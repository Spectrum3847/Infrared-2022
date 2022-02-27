package frc.robot.commands.ballpath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.constants.FeederConstants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.LauncherConstants;

public class BallPathCommands {
    
    //fender shot
    public static Command fenderShot(){
        return setHood(LauncherConstants.closeShotAngle).alongWith(runLauncherRPM(LauncherConstants.closeShotSpeed));
    }

    //tarmac shot
    public static Command tarmacShot(){
        return setHood(LauncherConstants.tarmacShotAngle).alongWith(runLauncherRPM(LauncherConstants.tarmacShotSpeed));
    }

    //far shot
    public static Command farShot(){
        return setHood(LauncherConstants.farShotAngle).alongWith(runLauncherRPM(LauncherConstants.farShotSpeed));
    }

    //Feed
    public static Command feed(){
        return runFeeder(FeederConstants.feedSpeed).alongWith(runIndexer(IndexerConstants.feedSpeed));
    }


    //Intake
    public static Command intakeBalls(){
        return runIntake(IntakeConstants.intakeSpeed).alongWith(intakeDown()
            .alongWith(runIndexer(IndexerConstants.feedSpeed)));
    }

    //Deploy Intake
    public static Command intakeDown(){
        return new RunCommand(() -> Robot.intake.down(), Robot.intake.pneumatic);
    }

    //Run intake motor
    public static Command runIntake(double speed){
        return new RunCommand(() -> Robot.intake.setManualOutput(speed), Robot.intake);
    }

    //Run indexer motor
    public static Command runIndexer(double speed){
        return new RunCommand(() -> Robot.indexer.setManualOutput(speed), Robot.indexer);
    }

    //Run feeder
    public static Command runFeeder(double speed){
        return new RunCommand(() -> Robot.feeder.setManualOutput(speed), Robot.feeder);
    }

    //Run launcher motor
    public static Command runLauncher(double speed){
        return new RunCommand(() -> Robot.launcher.setManualOutput(speed), Robot.launcher);
    }

    //Run launcher motor
    public static Command runLauncherVoltage(double voltage){
        return new RunCommand(() -> Robot.launcher.setVoltageOutput(voltage), Robot.launcher);

    }
    //Set Launcher RPM
    public static Command runLauncherRPM(double speed){
        return new RunCommand(() -> Robot.launcher.setRPM(speed), Robot.launcher);
    }

    //Set Hood angle
    public static Command setHood(double angle){
        return new RunCommand(() -> Robot.launcher.hood.setHoodAngle(angle), Robot.launcher.hood);
    }

    //Eject Balls
    public static Command eject(){
        return runIntake(1.0).alongWith(runIndexer(-1.0).alongWith(runFeeder(-1.0).alongWith(intakeDown())));
    }
    
    //UnJam balls
    public static Command unJamAll(){
        return runIntake(-0.5).alongWith(runIndexer(-IndexerConstants.feedSpeed))
            .alongWith(runFeeder(-FeederConstants.feedSpeed)).alongWith(runLauncher(-0.2));
    }
}
