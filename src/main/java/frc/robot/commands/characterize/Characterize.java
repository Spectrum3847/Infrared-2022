package frc.robot.commands.characterize;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.sysid.SysIdDrivetrainLogger;
import frc.robot.subsystems.Swerve.Swerve;

public class Characterize extends CommandBase {
    private final Swerve m_swerve;
    private SysIdDrivetrainLogger m_logger;   
    private Double m_prevAngle = 0.0;
    private Double m_prevTime = 0.0;
    private boolean m_resetComplete;
    public Characterize(Swerve subsystem) {

        m_swerve = subsystem;
        addRequirements(m_swerve);   
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // reset gyro and encoders
        // set timeperiod to .005
        //m_swerve.m_drive.setDeadband(0.0);
        // The following is called for the side-effect of resetting the 
        // drivebase odometers.
        m_swerve.resetOdometry(m_swerve.getPose()); 
        m_logger = new SysIdDrivetrainLogger();
        m_logger.updateThreadPriority();
        m_logger.initLogging();
        m_resetComplete = false;
    }
   
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftPosition = m_swerve.getLeftPositionMeters();
        double leftRate = m_swerve.getLeftMetersPerSec();
        double rightPosition = m_swerve.getRightPositionMeters();
        double rightRate = m_swerve.getRightMetersPerSec();
        double angularPosition = m_swerve.getRadians();
        double deltaAngle = angularPosition - m_prevAngle;
        double now = Timer.getFPGATimestamp();
        double deltaTime = now - m_prevTime;
        double angularRate = m_prevTime==0 || deltaTime==0 ? 0.0 : deltaAngle/deltaTime;
        m_prevAngle = angularPosition;
        m_prevTime = now;

        // Resetting encoders takes non-zero time on CAN-based encoders
        // Wait for the reset to complete
        if (!m_resetComplete) {
            if (leftPosition > 0.01 || rightPosition > 0.01) return;
            m_resetComplete = true;
        }
        m_logger.log(leftPosition, rightPosition, leftRate, 
                   rightRate, angularPosition, angularRate);
        m_swerve.tankDriveVolts(m_logger.getLeftMotorVoltage(), 
                               m_logger.getRightMotorVoltage());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Characterization done; disabled");
        m_swerve.tankDriveVolts(0, 0);
        m_logger.sendData();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;

    }
}

