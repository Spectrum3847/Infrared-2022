//Created by Spectrum3847
package frc.robot.commands.auto;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;
import frc.robot.commands.swerve.TeleopSwerve;
import frc.robot.constants.AutonConstants;

//Need to work on setting an intial position for the field2D map to work properly.
public class DoubleBallSimple extends ParallelCommandGroup {
  /** Creates a new TestPathFollowing. */
  public DoubleBallSimple() {

    addCommands(
      AutonCommands.llShotwithTimeout(4),
      new WaitCommand(0.25).andThen(
      AutonCommands.driveForTime(1, 0.2)
          .deadlineWith(AutonCommands.intake()).andThen(
        new WaitCommand(1).deadlineWith(AutonCommands.intake()).andThen(
        new WaitCommand(1.75).deadlineWith(BallPathCommands.feed(),
        new PrintCommand("Double Ball Done"))))).withTimeout(4)
    );
  }
}
