package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class ClimberCommands {

    public static Command pull(){
        return new RunCommand(()-> Robot.climber.setManualOutput(.75), Robot.climber);
    }
}
