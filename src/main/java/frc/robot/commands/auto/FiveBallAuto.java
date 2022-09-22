// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.AutonConstants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FiveBallAuto extends SequentialCommandGroup {
  /** Creates a new FourBallAuto. */

  
  PathPlannerTrajectory GetFirstBalls = PathPlanner.loadPath("Five_FirstBalls", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
  PathPlannerTrajectory Prep2ndBall = PathPlanner.loadPath("Five_2ndBall", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
  PathPlannerTrajectory Get2ndBall = PathPlanner.loadPath("Five_Intake2ndBall", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
  PathPlannerTrajectory GoToTerminal = PathPlanner.loadPath("Five_GoToTerminal", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
  PathPlannerTrajectory HumanBall = PathPlanner.loadPath("Five_HumanBall", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);
  PathPlannerTrajectory GoTo2ndShots = PathPlanner.loadPath("Five_2ndShots", AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel);

  public FiveBallAuto() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      AutonCommands.intializePathFollowing(GetFirstBalls),
      AutonCommands.llShotwithTimeout(15).alongWith(
        AutonCommands.followPathAndIntake(GetFirstBalls, 2).andThen(
          AutonCommands.intake(0.05),
          AutonCommands.followPathAndIntake(Prep2ndBall, 2),
          new WaitCommand(0.1),
          AutonCommands.followPathAndIntake(Get2ndBall, 2),
          AutonCommands.intake(0.05),
          AutonCommands.feed(1), //Feed first three balls
          AutonCommands.followPathAndIntake(GoToTerminal, 5),
          new WaitCommand(0.1),
          AutonCommands.followPathAndIntake(HumanBall,2),
          AutonCommands.intake(0.75),
          new SwerveFollowCommand(GoTo2ndShots).withTimeout(4),
          AutonCommands.autonLLAim().withTimeout(0.4),
          AutonCommands.feed(6)
    )));
  }
}


//AutonCommands.intake(2).alongWith(AutonCommands.autonLLAim().withTimeout(2)),
