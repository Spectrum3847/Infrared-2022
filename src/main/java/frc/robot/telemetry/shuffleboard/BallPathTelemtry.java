//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.Robot;
import frc.lib.telemetry.WidgetsAndLayouts;

// The Shuffleboard Main tab.
public class BallPathTelemtry {
    //---------------------//
    // NetworkTableEntries //

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
    }

    //--------//
    // Update //
    public void update() {     // This will be called in the robotPeriodic
    }
}
