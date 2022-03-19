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
    

    //--------------//
    // Constructor  //
    public BallPathTelemtry() {
        m_tab = Shuffleboard.getTab("BallPath");
    }

    //---------------------//
    // initialize //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        WidgetsAndLayouts.TalonFXLayout("Indexer Motor", m_tab, Robot.indexer.motorLeader).withPosition(0, 0);
        WidgetsAndLayouts.TalonFXLayout("Feeder Motor", m_tab, Robot.feeder.motorLeader).withPosition(1, 0);
        m_tab.addBoolean("Lower Sensor?", () -> Robot.feeder.lowerHasBall()).withPosition(2, 1)
            .withProperties(Map.of("Color when true", "#1a0068", "Color when false", "#FFFFFF"));
        m_tab.addBoolean("Top Sensor?", () -> Robot.feeder.topHasBall()).withPosition(2, 2)
            .withProperties(Map.of("Color when true", "#1a0068", "Color when false", "#FFFFFF"));
        m_tab.addBoolean("Is Blue?", () -> Robot.indexer.isBlue()).withPosition(2, 3)
            .withProperties(Map.of("Color when true", "#0000FF", "Color when false", "#FFFFFF"));
        m_tab.addBoolean("Is Red?", () -> Robot.indexer.isRed()).withPosition(2, 4)
            .withProperties(Map.of("Color when true", "#FF0000", "Color when false", "#FFFFFF"));
        m_tab.addNumber("Red 1", () -> Robot.indexer.getRed()).withPosition(3, 0);
        m_tab.addNumber("Blue 1", () -> Robot.indexer.getBlue()).withPosition(3, 1);
        m_tab.addNumber("Green 1", () -> Robot.indexer.getGreen()).withPosition(3, 2);
    }

    //--------//
    // Update //
    public void update() {     // This will be called in the robotPeriodic

    }
}
