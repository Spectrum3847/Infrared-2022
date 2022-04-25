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
public class LeftFancyDouble extends SequentialCommandGroup {
  /** Creates a new FourBallAuto. */

  
  PathPlannerTrajectory GetFirstBalls = PathPlanner.loadPath("LFD_FirstBall", 3, 3);
  PathPlannerTrajectory PrepOpponentBall = PathPlanner.loadPath("LFD_PrepOpponentBall", 3, 3);
  PathPlannerTrajectory GetOpponentBall = PathPlanner.loadPath("LFD_GetOpponentBall", 3, 3);

  public LeftFancyDouble() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutonCommands.intializePathFollowing(GetFirstBalls),
      AutonCommands.llShotwithTimeout(15).alongWith(
        AutonCommands.followPathAndIntake(GetFirstBalls, 2).andThen(
          AutonCommands.intake(0.5),
          AutonCommands.feed(1.5), //Feed two balls
          AutonCommands.followPathAndIntake(PrepOpponentBall, 2),
          AutonCommands.followPathAndIntake(GetOpponentBall, 2),
          AutonCommands.intake(2),
          AutonCommands.feed(1)
    )));
  }
}
