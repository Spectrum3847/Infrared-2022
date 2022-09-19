//Created by Spectrum3847
package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.AutonConstants;

//Need to work on setting an intial position for the field2D map to work properly.
public class TestPathPlanner extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TestPathPlanner() {

    // An example trajectory to follow. All units in meters.
    PathPlannerTrajectory DriveOneMeterFwd = PathPlanner.loadPath("Test_1meter", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
    PathPlannerTrajectory DriveTerminal = PathPlanner.loadPath("Test_DriveTerminal", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
    PathPlannerTrajectory Test_SlideRight = PathPlanner.loadPath("Test_SlideRight", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
    
    addCommands(
        AutonCommands.intializePathFollowing(DriveOneMeterFwd),
        new SwerveFollowCommand(DriveOneMeterFwd)
        //new WaitCommand(0.5),
        //new SwerveFollowCommand(DriveTerminal)

    );
  }
}
