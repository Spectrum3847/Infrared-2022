package frc4061.robot.commands;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc4061.robot.subsystems.Shooter;
import frc.SysIdGeneralMechanismLogger;

public class CharacterizeShooter extends CommandBase {
    private final Shooter m_shooter;
    private SysIdGeneralMechanismLogger m_logger;
    public enum Side { kLEFT, kRIGHT };
    private final Side m_side;
     
    public CharacterizeShooter(Shooter shooter, Side side) {

        m_shooter = shooter;
        m_side = side;
        addRequirements(m_shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_logger = new SysIdGeneralMechanismLogger();
        m_logger.updateThreadPriority();
        m_logger.initLogging();
    }
   
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Pair<Double,Double> speeds = m_shooter.getSpeeds();
        if (m_side==Side.kLEFT) {
            m_logger.log(0.0, speeds.getFirst());
            m_shooter.setMotorOutputs(m_logger.getMotorVoltage(), 0.0);
        } else {
            m_logger.log(0.0, speeds.getSecond());
            m_shooter.setMotorOutputs(0.0, m_logger.getMotorVoltage());
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Shooter characterization done; disabled");
        m_shooter.stopShooter();
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

