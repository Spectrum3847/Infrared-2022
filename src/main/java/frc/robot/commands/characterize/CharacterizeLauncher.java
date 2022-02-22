package frc.robot.commands.characterize;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.sysid.SysIdGeneralMechanismLogger;
import frc.robot.subsystems.Launcher;

public class CharacterizeLauncher extends CommandBase {
    private final Launcher m_launcher;
    private SysIdGeneralMechanismLogger m_logger;
     
    public CharacterizeLauncher(Launcher launcher) {
        m_launcher = launcher;
        addRequirements(m_launcher);
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
        double speeds = m_launcher.getRotationPerSec();
            m_logger.log(0.0, speeds);
            m_launcher.setVoltageOutput(m_logger.getMotorVoltage());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Launcher characterization done; disabled");
        m_launcher.stop();
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

