// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FourBallAuto extends SequentialCommandGroup {
  /** Creates a new FourBallAuto. */

  
  PathPlannerTrajectory GetFirstBalls = PathPlanner.loadPath("Four_FirstBalls", 3, 3);
  PathPlannerTrajectory GoToTerminal = PathPlanner.loadPath("Four_GoToTerminal", 3, 3);
  PathPlannerTrajectory GoTo2ndShots = PathPlanner.loadPath("Four_2ndShots", 3, 3);

  public FourBallAuto() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutonCommands.intializePathFollowing(GetFirstBalls),
      AutonCommands.llShotwithTimeout(15).alongWith(
        AutonCommands.followPathAndIntake(GetFirstBalls, 2).andThen(
          AutonCommands.intake(0.5),
          AutonCommands.feed(1), //Feed first two balls
          AutonCommands.followPathAndIntake(GoToTerminal, 4),
          AutonCommands.intake(2.5),
          new SwerveFollowCommand(GoTo2ndShots).withTimeout(4),
          AutonCommands.autonLLAim().withTimeout(1),
          AutonCommands.feed(1)
    )));
  }
}
