//Created by Spectrum3847
package frc.robot.commands.auto;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;

//Need to work on setting an intial position for the field2D map to work properly.
public class DoubleBallSimple extends ParallelCommandGroup {
  /** Creates a new TestPathFollowing. */
  public DoubleBallSimple() {

    addCommands(
      BallPathCommands.tarmacShot().withTimeout(5),
      new SwerveDrive(false, 0.2, 0).withTimeout(1.0)
          .deadlineWith(BallPathCommands.intakeBalls()).andThen(
        new WaitCommand(1.5).deadlineWith(new LLAim(), BallPathCommands.intakeBalls()).andThen(
        new WaitCommand(2.5).deadlineWith(BallPathCommands.feed())))
    );
  }

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}
