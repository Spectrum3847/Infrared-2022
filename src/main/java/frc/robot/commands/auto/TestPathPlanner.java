//Created by Spectrum3847
package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;

//Need to work on setting an intial position for the field2D map to work properly.
public class TestPathPlanner extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TestPathPlanner() {

    // An example trajectory to follow. All units in meters.
    PathPlannerTrajectory RightDriveBackToBall = PathPlanner.loadPath("RightDriveBackToBall", 3, 3);
    addCommands(
        new SwerveFollowCommand(RightDriveBackToBall)
          .raceWith(BallPathCommands.intakeBalls(), BallPathCommands.tarmacShot()),
        new LLAim().withTimeout(2).raceWith(new SwerveDrive(true, 0, 0)),
        new WaitCommand(2).alongWith(BallPathCommands.tarmacShot(), BallPathCommands. feed())
    );
  }

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}
