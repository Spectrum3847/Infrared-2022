package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.SwerveDrive;

public class AutonCommands {

    public static Command intake(){
        //return BallPathCommands.intakeBalls();
        return new WaitCommand(1);
    }

    public static Command intake(double time){
        return intake().withTimeout(time);
    }

    public static Command tarmacShotwithTimeout(double time){
        return BallPathCommands.tarmacShot().withTimeout(time);
    }

    public static Command driveForTime(double time, double speed){
        return new SwerveDrive(false, speed, 0).withTimeout(time);
    }
}
