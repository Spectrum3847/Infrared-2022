//Created by Spectrum3847
package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;

//Need to work on setting an intial position for the field2D map to work properly.
public class RightDoubleBall extends ParallelCommandGroup {
  /** Creates a new TestPathFollowing. */
  public RightDoubleBall() {

    // An example trajectory to follow. All units in meters.
    PathPlannerTrajectory RightDriveBackToBall = PathPlanner.loadPath("RightDriveBackToBall", 3, 3);
    addCommands(
      BallPathCommands.tarmacShot().withTimeout(6),
      new SwerveDrive(false, 0.2, 0).withTimeout(1.0)
          .deadlineWith(BallPathCommands.intakeBalls()).andThen(
        new WaitCommand(1.5).deadlineWith(new LLAim(), BallPathCommands.intakeBalls()).andThen(
        new WaitCommand(2).deadlineWith(BallPathCommands.feed())))
    );
  }

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}
