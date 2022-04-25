//Created by Spectrum3847
package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.LimeLight;
import frc.lib.drivers.LimeLightControlModes.LedMode;
import frc.lib.util.LLDistance;
import frc.robot.Robot;
import frc.robot.constants.LauncherConstants;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.Vision.Vision.TimestampedTranslation2d;
import frc.robot.telemetry.Log;

public class VisionLL extends SubsystemBase {
    public static final String name = Log._visionLL;

    public final LimeLight limelight;
    private boolean LEDState = true;
    LLDistance UpperHub; 

    private TimestampedTranslation2d translationToGoal;


    /**
     * Creates a new VisionLL.
     */
    public VisionLL() {
        setName(name);
        limelight = new LimeLight();
        limeLightLEDOn();   
        forwardLimeLightPorts();
        translationToGoal = new TimestampedTranslation2d(0, new Translation2d(0,0));
        UpperHub = new LLDistance(VisionConstants.targetHeight, VisionConstants.limelightHeight, VisionConstants.limelightAngle);
    }

    public void forwardLimeLightPorts() {
        // Forward the Limelight Ports
        PortForwarder.add(5800, "10.85.15.22", 5800);
        PortForwarder.add(5801, "10.85.15.22", 5801);
    }

    public void setTranslationToGoal(TimestampedTranslation2d translationToGoal) {
        this.translationToGoal = translationToGoal;
    }

    @Override
    public void periodic() {
        //SmartDashboard.putNumber("Limelight Position (M)", getActualDistance());
        //SmartDashboard.putNumber("Limelight Position (in)", getLLDistance());
        //SmartDashboard.putNumber("Limelight Angle", limelight.getdegVerticalToTarget());
        SmartDashboard.putNumber("Inches to Goal",
             Units.metersToInches(translationToGoal.translation.getNorm()));
        //SmartDashboard.putNumber("Meters to Goal",translationToGoal.translation.getNorm());
        SmartDashboard.putNumber("Limelight Shooter Vel", getVisionLauncherRPM());
        // This method will be called once per scheduler run
        // If disabled and LED-Toggle is false, than leave lights off, else they should
        // be on
        /*if (Robot.s_robot_state == RobotState.DISABLED && !MainTelemetry.getLimeLightToggle()
                && !DriverStation.isFMSAttached()) {
            if (LEDState == true) {
                limeLightLEDOff();
                LEDState = false;
            }
        } else {
            if (LEDState == false) {
                limeLightLEDOn();
                LEDState = true;
            }
        }*/

        
    }

    public double getLLDistance(){
        return UpperHub.distanceInches(limelight.getdegVerticalToTarget());
    }
    public double getActualDistance(){
        return UpperHub.distanceMeters(limelight.getdegRotationToTarget());
    }

    public double getDistanceToGoalMeters(){
        return translationToGoal.translation.getNorm();
    }

    public double getDistanceToGoalFeet(){
        if (Robot.visionLL.limelight.getTargetArea() <= 0){
            return 0;
        }
        return Units.metersToFeet(getDistanceToGoalMeters());
    }

    public double getVisionLauncherRPM(){
        double distance = getDistanceToGoalFeet();
        if (distance <= 0){
            return LauncherConstants.LowGoalShotSpeed;
        }
        return (LauncherConstants.visionPerFootSpeed * distance) + (LauncherConstants.visionBaseSpeed);
    }

    public double getHoodAngle(){
        double d = translationToGoal.translation.getNorm();
        double setpoint;
        if(d < 1.5) setpoint = ((-2)*d + 70);
        else if(d < 2) setpoint = ((-4) * d + 73);
        else if(d < 3) setpoint = ((-2) * d + 69);
        setpoint = -d + 66;
        if(setpoint < 57){
            return 57;
        }
        else if(setpoint >68){
            return 68;
        }
        return setpoint;
    }

    public void limeLightLEDOff() {
        limelight.setLEDMode(LedMode.kforceOff);
    }

    public void limeLightLEDOn() {
        limelight.setLEDMode(LedMode.kforceOn);
    }

    public void setLimeLightLED(boolean b) {
        if (b) {
            limeLightLEDOn();
        } else {
            limeLightLEDOff();
        }
    }

    public double getLLDegToTarget() {
        return limelight.getdegRotationToTarget() * -1;
    }

    public boolean getLLIsTargetFound() {
        return limelight.getIsTargetFound();
    }

    public double getLLTargetArea() {
        return limelight.getTargetArea();
    }

    public boolean getLimelightHasValidTarget() {
        return limelight.getIsTargetFound();
    }

    public void setLimeLightPipeline(int i) {
        setLimeLightPipeline(i);
    }

    public void dashboard() {
    }

    public static void printDebug(String msg) {
        Log.println(msg, name, Log.low1);
    }

    public static void printInfo(String msg) {
        Log.println(msg, name, Log.normal2);
    }

    public static void printWarning(String msg) {
        Log.println(msg, name, Log.high3);
    }

    public static void printError(String msg) {
        Log.println(msg, name, Log.critical4);
    }

}