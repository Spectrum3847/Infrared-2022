//Created by Spectrum3847
package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

//Need to work on setting an intial position for the field2D map to work properly.
public class TestPathPlanner extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TestPathPlanner() {

    // An example trajectory to follow. All units in meters.
    PathPlannerTrajectory DriveOneMeterFwd = PathPlanner.loadPath("Test_1meter", 3, 3);
    PathPlannerTrajectory Test_SlideRight = PathPlanner.loadPath("Test_SlideRight", 3, 3);
    
    addCommands(
        AutonCommands.intializePathFollowing(DriveOneMeterFwd),
        new SwerveFollowCommand(DriveOneMeterFwd)
        //new WaitCommand(2),
        //new SwerveFollowCommand(Test_SlideRight)

    );
  }
}
