//Created by Spectrum3847
package frc.robot.subsystems;

import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.LimeLight;
import frc.lib.drivers.LimeLightControlModes.LedMode;
import frc.lib.util.LLDistance;
import frc.robot.constants.VisionConstants;
import frc.robot.telemetry.Log;

public class VisionLL extends SubsystemBase {
    public static final String name = Log._visionLL;

    public final LimeLight limelight;
    private boolean LEDState = true;
    LLDistance UpperHub; 


    /**
     * Creates a new VisionLL.
     */
    public VisionLL() {
        setName(name);
        limelight = new LimeLight();
        limeLightLEDOn();
        forwardLimeLightPorts();
        UpperHub = new LLDistance(VisionConstants.targetHeight, VisionConstants.limelightHeight, VisionConstants.limelightAngle);
    }

    public void forwardLimeLightPorts() {
        // Forward the Limelight Ports
        PortForwarder.add(5800, "10.85.15.22", 5800);
        PortForwarder.add(5801, "10.85.15.22", 5801);
    }

    @Override
    public void periodic() {
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

    public double getRPM(){
        return UpperHub.distanceMeters(limelight.getdegRotationToTarget());
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