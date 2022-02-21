package frc.robot.commands.ballpath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class BallPath {
    
    public static Command feed(){
        return new FeedBalls();
    }

    public static Command intakeBalls(){
        return runIntake(Robot.intake.intakeSpeed).alongWith(intakeDown()
            .alongWith(runIndexer(Robot.indexer.feedSpeed)));
    }

    //Deploy Intake
    public static Command intakeDown(){
        return new RunCommand(() -> Robot.intake.sol.down(), Robot.intake.sol);
    }

    //Run intake motor
    public static Command runIntake(double speed){
        return new RunCommand(() -> Robot.intake.setManualOutput(speed), Robot.intake);
    }

    //Run indexer motor
    public static Command runIndexer(double speed){
        return new RunCommand(() -> Robot.indexer.setMotorOutput(speed), Robot.indexer);
    }

    //Run feeder
    public static Command runFeeder(double speed){
        return new RunCommand(() -> Robot.feeder.setMotorOutput(speed), Robot.feeder);
    }

    //Run launcher motor
    public static Command runLauncher(double speed){
        return new RunCommand(() -> Robot.launcher.setManualOutput(speed), Robot.launcher);
    }

    //Run launcher motor
    public static Command runLauncherVoltage(double voltage){
        return new RunCommand(() -> Robot.launcher.setVoltage(voltage), Robot.launcher);

    }
    //Set Launcher RPM
    public static Command setLauncherRPM(double speed){
        return new RunCommand(() -> Robot.launcher.setRPM(speed), Robot.launcher);
    }

    public static Command setHood(double position){
        return new RunCommand(() -> Robot.launcher.hood.setHood(position), Robot.launcher.hood);
    }

    public static Command eject(){
        return runIntake(1.0).alongWith(runIndexer(-1.0).alongWith(runFeeder(-1.0).alongWith(intakeDown())));
    }
    
    public static Command unJamAll(){
        return runIntake(-0.5).alongWith(runIndexer(-Robot.indexer.feedSpeed))
            .alongWith(runFeeder(-Robot.indexer.feedSpeed)).alongWith(runLauncher(-0.2));
    }
}
