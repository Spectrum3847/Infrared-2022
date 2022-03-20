//Created by Spectrum3847
package frc.robot.commands.auto;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;
import frc.robot.commands.swerve.TurnToAngle;
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
    return BallPathCommands.tarmacShot().withTimeout(6).alongWith(    //spin up launcher the entire time
      new TurnToAngle(AutonConstants.thirdBallAngle).withTimeout(1).andThen(  //turn towards third ball
        new SwerveDrive(false, 0.2, 0).withTimeout(1.0)                 //Drive towards third ball
            .deadlineWith(BallPathCommands.intakeBalls()),      //Intake balls
          new TurnToAngle(AutonConstants.thirdBallTurnToGoal).withTimeout(1),  //Turn to goal
            new WaitCommand(1.5).deadlineWith(new LLAim(), BallPathCommands.intakeBalls()), //Aim the 
            new WaitCommand(2).deadlineWith(BallPathCommands.feed())));         //launch balls
  }
  

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}
