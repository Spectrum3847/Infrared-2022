package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.constants.ClimberConstants;

public class ClimberCommands {
    /*
     * Climber Sequence
     * 1. fullUp
     * 2. climb
     * 3. nextRungExtend
     * 4. nextRungUp
     * Repeat 2-4.
     */

    // Put the climber fully up, used to grab the mid bar
    public static Command fullUp() {
        return extendFull().alongWith(new WaitCommand(0.5).andThen(tiltUp()));
    }

    // Put the climber partially out and tilted up, used to grab the next rung
    public static Command nextRungUp() {
        return tiltUp().alongWith(extendNextRung());
    }

    public static Command nextRungDown(){
        return extendNextRung();//.alongWith(releaseRung());
    }

    // pull the climber down, used to climb to mid bar, and from rung to rung
    public static Command climb() {
        return pull().alongWith(new WaitCommand(0.05).andThen(tiltDown()));
    }

    // release rung
    public static Command releaseRung() {
        return tiltUp().withTimeout(0.2).andThen(tiltDown());
    }

    public static Command extendFull() {
        return extendPIDsetting().andThen(toPosition(ClimberConstants.fullExtend));
    }

    // Used to extend before tilting up to grab the next rung
    public static Command extendNextRung() {
        return extendPIDsetting().andThen(toPosition(ClimberConstants.nextRungExtend));
    }

    public static Command pull() {
        return climbPIDsetting().andThen(toPosition(ClimberConstants.fullRetract));
    }

    public static Command climbPIDsetting(){
        return new InstantCommand(() -> Robot.climber.setPIDslot(1));
    }

    public static Command extendPIDsetting(){
        return new InstantCommand(() -> Robot.climber.setPIDslot(0));
    }

    public static Command toPosition(double position) {
        return new RunCommand(() -> Robot.climber.setMMPosition(position), Robot.climber);
    }

    public static Command toPositionAndEnd(double position){
        return new FunctionalCommand(
            () -> nothing(),
            () -> Robot.climber.setMMPosition(position),
            interrupted -> nothing(),
            () -> Robot.climber.getAtTarget(200),
            Robot.climber
        );
    }

    public static void nothing(){      
    }

    public static Command zeroClimberPosition(){
        return new RunCommand(() -> Robot.climber.zeroClimberEncoder(), Robot.climber);
    }

    public static Command tiltUp() {
        return new RunCommand(() -> Robot.climber.tiltUp(), Robot.climber.pneumatic);
    }

    public static Command tiltDown() {
        return new RunCommand(() -> Robot.climber.tiltDown(), Robot.climber.pneumatic);
    }
}
