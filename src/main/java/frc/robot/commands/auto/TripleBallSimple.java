//Created by Spectrum3847
package frc.robot.commands.auto;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.constants.AutonConstants;

//Need to work on setting an intial position for the field2D map to work properly.
public class TripleBallSimple extends SequentialCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TripleBallSimple() {

    addCommands(
      new DoubleBallSimple(),
      thirdBall()
    );
  }

  private Command thirdBall(){
    return  new WaitCommand(0.25).andThen(
      BallPathCommands.llShotRPM().withTimeout(8).alongWith(
      new TurnToAngle(AutonConstants.thirdBallAngle).withTimeout(1.5).andThen(  //turn towards third ball
        AutonCommands.driveForTime(2.6, 0.25)                 //Drive towards third ball
            .deadlineWith(AutonCommands.intake()),      //Intake balls
          new TurnToAngle(AutonConstants.thirdBallTurnToGoal).withTimeout(1).andThen(
            AutonCommands.autonLLAim().alongWith(  //Turn to goal
            new WaitCommand(4).deadlineWith(AutonCommands.intake())), 
            new WaitCommand(4).deadlineWith(BallPathCommands.feed())))).andThen(
              new PrintCommand("Done with 3 ball")
            ));         //and then launch balls
  }
}
