//Created by Spectrum3847
package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//Need to work on setting an intial position for the field2D map to work properly.
public class TestPathPlanner extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TestPathPlanner() {

    // An example trajectory to follow. All units in meters.
    PathPlannerTrajectory exampleTrajectory = PathPlanner.loadPath("New New New Path", 3, 3);

    addCommands(
        new SwerveFollowCommand(exampleTrajectory));
  }

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}
