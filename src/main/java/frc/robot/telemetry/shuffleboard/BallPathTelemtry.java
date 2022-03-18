//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.Robot;
import frc.lib.telemetry.WidgetsAndLayouts;

// The Shuffleboard Main tab.
public class BallPathTelemtry {
    //---------------------//
    // NetworkTableEntries //
    public static NetworkTableEntry feederSensorEntry;
    public static NetworkTableEntry intakeSensorEntry;

    //----------------//
    // Tab & Layouts  //
    private static ShuffleboardTab m_tab;

    //---------//
    // Widgets //
    public static SimpleWidget feederSensor;
    public static SimpleWidget intakeSensor;
    

    //--------------//
    // Constructor  //
    public BallPathTelemtry() {
        m_tab = Shuffleboard.getTab("BallPath");
    }

    //---------------------//
    // initialize //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        //flashWidget(feederSensor, feederSensorEntry);
        //flashWidget(intakeSensor, intakeSensorEntry);
        WidgetsAndLayouts.TalonFXLayout("Indexer Motor", m_tab, Robot.indexer.motorLeader).withPosition(0, 0);
        WidgetsAndLayouts.TalonFXLayout("Feeder Motor", m_tab, Robot.feeder.motorLeader).withPosition(1, 0);
        //m_tab.addBoolean("Feeder Sensor", () -> Robot.feeder.feederHasBall()).withPosition(1, 1);
        
    }
    public SimpleWidget flashWidget(SimpleWidget flash, NetworkTableEntry flashEntry) {
        flash = m_tab.add("Flash", false);
        flashEntry = flash.getEntry();
        flash.withWidget(BuiltInWidgets.kBooleanBox);
        flash.withProperties(Map.of("Color when true", "#1a0068", "Color when false", "#FFFFFF"));
        return flash;
    }
    //--------//
    // Update //
    public void update() {     // This will be called in the robotPeriodic

    }
}
