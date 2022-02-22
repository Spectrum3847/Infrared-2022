package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class ClimberCommands {
    /* Climber Sequence
    * 1. fullUp
    * 2. climb
    * 3. nextRungExtend
    * 4. nextRungUp
    * Repeat 2-4.
    */

    //Put the climber fully up, used to grab the mid bar
    public static Command fullUp(){
        return tiltUp().alongWith(extendFull());
    }

    //Put the climber partially out and tilted up, used to grab the next rung
    public static Command nextRungUp(){
        return tiltUp().alongWith(extendNextRung());
    }

    //pull the climber down, used to climb to mid bar, and from rung to rung
    public static Command climb(){
        return tiltDown().alongWith(pull());
    }

    public static Command extendFull(){
        return toPosition(ClimberConstants.fullExtend);
    }

    //Used to extend before tilting up to grab the next rung
    public static Command extendNextRung(){
        return toPosition(ClimberConstants.nextRungExtend);
    }

    public static Command pull(){
        return toPosition(ClimberConstants.fullRetract);
    }

    public static Command toPosition(double position){
        return new RunCommand(()-> Robot.climber.setMMPosition(position), Robot.climber);
    }

    public static Command tiltUp(){
        return new RunCommand(()-> Robot.climber.tiltUp(), Robot.climber.pneumatic);
    }

    public static Command tiltDown(){
        return new RunCommand(()-> Robot.climber.tiltDown(), Robot.climber.pneumatic);
    }
}
