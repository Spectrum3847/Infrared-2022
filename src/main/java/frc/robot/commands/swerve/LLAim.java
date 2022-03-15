//Created by Spectrum3847
package frc.robot.commands.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.lib.util.Logger;
import frc.lib.util.SpectrumPreferences;
import frc.robot.Robot;
import frc.robot.telemetry.Log;

public class LLAim extends PIDCommand {

  public static double kP = 0.8;
  public static double kI = 0;
  public static double kD = 0;

  boolean hasTarget = false;
  
  public LLAim() {    
    super(
        // The PPIDController used by the command
        new PIDController(
            // The PID gainss
            kP, kI, kD),
        // This should return the measurement
        Robot.visionLL::getLLDegToTarget,
        // This should return the goal (can also be a constant)
        0,
        // This uses the output
        (output) -> Robot.swerve.useOutput(output * -1)); // -1 to turn correct direction
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  @Override
  public void initialize() {
    Robot.visionLL.limeLightLEDOn();
    kP = SpectrumPreferences.getNumber("LL-AIM kP", kP)/100;
    kI = SpectrumPreferences.getNumber("LL-AIM kI", kI)/100;
    kD = SpectrumPreferences.getNumber("LL-AIM kD", kD)/100;
    double tolerance = SpectrumPreferences.getNumber("LL-AIM Tolerance", 1.0);
    this.getController().setPID(kP, kI, kD);
    getController().setTolerance(tolerance);

    if(Robot.visionLL.getLimelightHasValidTarget()) {
      hasTarget = true;
    } else {
      hasTarget = false;
    }

    /*if(Robot.visionLL.getLLTargetArea() < 0.5) {
      Robot.visionLL.setLimeLightPipeline(1);
    } else {
      Robot.visionLL.setLimeLightPipeline(0);
    } */
    
    super.initialize();
  }

  @Override
  public void execute() {
    super.execute();
    printDebug("Angle: " + this.m_measurement.getAsDouble());
    Logger.setLevel(Logger.low1);
    dashboard();
  }

  public void dashboard() {
    SmartDashboard.putNumber("LL-AIM/ERROR", this.getController().getPositionError());
  }

  
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.swerve.useOutput(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint(); //getController().atGoal() || !hasTarget;
  }

  public static void printDebug(String msg){
    Logger.println(msg, Log._visionLL, Logger.low1);
  }
  
  public static void printInfo(String msg){
    Logger.println(msg, Log._visionLL, Logger.normal2);
  }
  
  public static void printWarning(String msg) {
    Logger.println(msg, Log._visionLL, Logger.high3);
  }
}
