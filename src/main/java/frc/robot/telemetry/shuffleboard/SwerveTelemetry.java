//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;

import java.util.Map;

import frc.robot.Robot;
import frc.robot.telemetry.Log;

// The Shuffleboard Main tab.
public class SwerveTelemetry {

     //----------------//
    // Default Values //

    //---------------------//
    // NetworkTableEntries //
    
    //----------------//
    // Tab & Layouts  //
    private ShuffleboardTab m_tab;

    //---------//
    // Widgets //

    //--------------//
    // Constructor  //
    public SwerveTelemetry() {
        printLow("Constructing SwerveTab...");

        m_tab = Shuffleboard.getTab("Swerve");
    }

    //---------------------//
    // initializeViewable  //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        driveLayout().withPosition(0, 0);
        moduleLayout("Mod 0", 0, m_tab).withPosition(1, 0);
        moduleLayout("Mod 1", 1, m_tab).withPosition(2, 0);
        moduleLayout("Mod 2", 2, m_tab).withPosition(3, 0);
        moduleLayout("Mod 4", 3, m_tab).withPosition(4, 0);
    }

    public ShuffleboardLayout driveLayout() {
        ShuffleboardLayout driveLayout = m_tab.getLayout("Drive", BuiltInLayouts.kGrid);
        driveLayout.withProperties(Map.of("Label position", "TOP"));

        SuppliedValueWidget<Double> m_gyroYawWidget = driveLayout.addNumber("Gyro Yaw",
                () -> Robot.swerve.getYaw().getDegrees());
        // m_gyroYawWidget.withWidget(BuiltInWidgets.kNumberBar);
        m_gyroYawWidget.withPosition(0, 0);

        SuppliedValueWidget<Double> m_driveYWidget = driveLayout.addNumber("Drive Y", () -> Robot.swerve.drive_y);
        m_driveYWidget.withPosition(0, 1);

        SuppliedValueWidget<Double> m_driveXWidget = driveLayout.addNumber("Drive X", () -> Robot.swerve.drive_x);
        m_driveXWidget.withPosition(0, 2);

        SuppliedValueWidget<Double> m_driveRWidget = driveLayout.addNumber("Drive R",
                () -> Robot.swerve.drive_rotation);
        m_driveRWidget.withPosition(0, 3);

        SuppliedValueWidget<Double> m_driveDistanceWidget = driveLayout.addNumber("Distance",
                () -> Robot.swerve.getDistance());
        m_driveDistanceWidget.withPosition(0, 4);
        
        SuppliedValueWidget<Double> m_driveyLocatioWidget = driveLayout.addNumber("Y-Location",
                () -> Robot.swerve.getPose().getY());
        m_driveyLocatioWidget.withPosition(0, 5);
        SuppliedValueWidget<Double> m_drivexLocatioWidget = driveLayout.addNumber("X-Location",
                () -> Robot.swerve.getPose().getX());
        m_drivexLocatioWidget.withPosition(0, 6);
        SuppliedValueWidget<Double> m_driveThetaWidget = driveLayout.addNumber("Theta",
                () -> Robot.swerve.getPose().getRotation().getDegrees());
        m_driveThetaWidget.withPosition(0, 7);
        return driveLayout;
    }

    public ShuffleboardLayout moduleLayout(String name, int moduleNum, ShuffleboardTab tab){
        ShuffleboardLayout modLayout = tab.getLayout(name, BuiltInLayouts.kGrid);
        //m_mod0Layout.withSize(1, 2);
        modLayout.withProperties(Map.of("Label position", "TOP"));

        // mod0 Cancoder Angle
        SuppliedValueWidget<Double> mod0CancoderAngleWidget = modLayout.addNumber("Cancoder Angle", () -> Robot.swerve.mSwerveMods[moduleNum].getCanCoder().getDegrees());
        mod0CancoderAngleWidget.withPosition(0, 0);

        //mod0 Integrated Angle
        SuppliedValueWidget<Double> mod0IntegratedAngleWidget = modLayout.addNumber("Integrated Angle", () -> Robot.swerve.mSwerveMods[moduleNum].getState().angle.getDegrees());
        mod0IntegratedAngleWidget.withPosition(0, 1);

        //mod0 Velocity
        SuppliedValueWidget<Double> mod0VelocityWidget = modLayout.addNumber("Wheel Velocity", () -> Robot.swerve.mSwerveMods[moduleNum].getState().speedMetersPerSecond);
        mod0VelocityWidget.withPosition(0, 2);
        return modLayout;
    }

    //--------//
    // Update //
    public void update() {     // This will be called in the robotPeriodic
    }

    public static void printLow(String msg) {
        Log.println(msg, Log._telemetry, Log.low1);
    }

    public static void printNormal(String msg) {
        Log.println(msg, Log._telemetry, Log.normal2);
    }

    public static void printHigh(String msg) {
        Log.println(msg, Log._telemetry, Log.high3);
    }

}
