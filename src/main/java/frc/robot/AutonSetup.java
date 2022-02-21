package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.characterize.FeedForwardCharacterization;
import frc.robot.commands.characterize.FeedForwardCharacterization.FeedForwardCharacterizationData;

public class AutonSetup {

    /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public static Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    FeedForwardCharacterizationData launcherData =
        new FeedForwardCharacterizationData("Launcher");


      return new FeedForwardCharacterization(Robot.launcher, true, launcherData,
                volts -> Robot.launcher.setVoltage(volts),
                Robot.launcher::getCharacterizationVelocity);
  }
}
