//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.Robot;
import frc.lib.telemetry.FalconConfigLayout;
import frc.lib.telemetry.WidgetsAndLayouts;

// The Shuffleboard Main tab.
public class ClimberTelemetry {
    // ---------------------//
    // NetworkTableEntries //

    // ----------------//
    // Tab & Layouts //
    private static ShuffleboardTab m_tab;
    private FalconConfigLayout ClimberLeader;

    // ---------//
    // Widgets //

    // --------------//
    // Constructor //
    public ClimberTelemetry() {
        m_tab = Shuffleboard.getTab("Climber");
    }

    // ---------------------//
    // initialize //
    // Create all View Widgets, ones you can't edit, created after subsystem
    // instances are made
    public void initialize() {
        // intakeLayout(m_tab).withPosition(0, 0);
        WidgetsAndLayouts.TalonFXLayout("Motor", m_tab, Robot.climber.motorLeader).withPosition(0, 0);
        ClimberLeader = new FalconConfigLayout("Climber Config", m_tab, Robot.climber.motorLeader, 3, 0);
        ClimberLeader.initialize();
    }

    // --------//
    // Update //
    public void update() { // This will be called in the robotPeriodic
        ClimberLeader.update();
    }
}
