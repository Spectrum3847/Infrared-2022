//Created by Spectrum3847
package frc.robot.commands.auto;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;
import frc.robot.commands.swerve.SwerveDrive;
import frc.robot.commands.swerve.TeleopSwerve;
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
      BallPathCommands.tarmacShot().withTimeout(8).alongWith(
      new TurnToAngle(AutonConstants.thirdBallAngle).andThen(  //turn towards third ball
        AutonCommands.driveForTime(2, 0.25)                 //Drive towards third ball
            .deadlineWith(AutonCommands.intake()),      //Intake balls
          new TurnToAngle(AutonConstants.thirdBallTurnToGoal).withTimeout(1).alongWith(  //Turn to goal
            new WaitCommand(1).deadlineWith(AutonCommands.intake())), 
            new WaitCommand(2).deadlineWith(BallPathCommands.feed()))));         //and then launch balls
  }
}
