package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;
import frc.robot.commands.swerve.TeleopSwerve;

public class AutonCommands {

    public static Command intake(){
        return BallPathCommands.intakeBalls();
        //return new WaitCommand(1);
    }

    public static Command intake(double time){
        return intake().withTimeout(time);
    }

    public static Command followPathAndIntake(PathPlannerTrajectory path, double time){
        return new SwerveFollowCommand(path).deadlineWith(intake()).withTimeout(time);
    }

    public static Command feed(double time){
        return BallPathCommands.feed().withTimeout(time);
    }

    public static Command llShotwithTimeout(double time){
        return BallPathCommands.llShotRPM().withTimeout(time);
        //return BallPathCommands.lowGoalShot().withTimeout(time);
    }

    public static Command driveForTime(double time, double speed){
        return new SwerveDrive(false, speed, 0).withTimeout(time);
    }

    public static Command autonLLAim(){
        return new LLAim().alongWith(
         new TeleopSwerve(Robot.swerve, true, true)   
        );
    }

    public static Command intializePathFollowing(PathPlannerTrajectory path){
        return new SequentialCommandGroup(
            AutonCommands.setBrakeMode().withTimeout(0.25), //set brake mode and pause for 1/4 second
            AutonCommands.intializeGyroAngle(path), //set gyro to initial heading
            AutonCommands.resetOdometry(path) //reset odometry to the initial position
        );
    }

    public static Command setBrakeMode(){
        return new RunCommand(() -> Robot.swerve.brakeMode(true));
    }

    public static Command setCoastMode(){
        return new RunCommand(() -> Robot.swerve.brakeMode(false));
    }

    public static Command setGryoDegrees(double deg){
        return new InstantCommand(() -> Robot.swerve.setGyroDegrees(deg)).andThen(
            new PrintCommand("Gyro Degrees: " + Robot.swerve.getDegrees())
        );
    }

    public static Command intializeGyroAngle(PathPlannerTrajectory path){
        PathPlannerState s = (PathPlannerState) path.getStates().get(0);
        return setGryoDegrees(s.holonomicRotation.getDegrees());
    }

    public static Command resetOdometry(PathPlannerTrajectory path){
        Pose2d tempPose = path.getInitialPose();
        PathPlannerState s = (PathPlannerState) path.getStates().get(0) ;
        Pose2d tempPose2 = new Pose2d(tempPose.getTranslation(), s.holonomicRotation) ;
        return new InstantCommand(() -> Robot.swerve.resetOdometry(tempPose2));
    }
}
