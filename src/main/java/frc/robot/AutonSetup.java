package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.PathPlannerFollowCommand;
import frc.robot.commands.auto.TestPathPlanner;
import frc.robot.commands.characterize.CharacterizeLauncher;

public class AutonSetup {

    /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public static Command getAutonomousCommand() {
      //return new CharacterizeLauncher(Robot.launcher);
      return new TestPathPlanner();
  }
}
