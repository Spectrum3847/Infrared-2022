package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import frc.robot.Robot;
import frc.robot.constants.SwerveConstants;

public class SwerveFollowCommand extends PPSwerveControllerCommand {

    PathPlannerTrajectory m_trajectory;

    public SwerveFollowCommand(PathPlannerTrajectory trajectory) {
        super(trajectory, Robot.swerve::getPose, SwerveConstants.swerveKinematics, Robot.swerve.xController,
                Robot.swerve.yController, Robot.swerve.thetaController, Robot.swerve::setModuleStates, Robot.swerve);

        m_trajectory = trajectory;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        super.initialize();
        //Robot.swerve.resetOdometry(m_trajectory.getInitialPose()); //Not sure if this is needed
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        super.execute();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        Robot.swerve.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
