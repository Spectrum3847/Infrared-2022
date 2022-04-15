//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.Robot;
import frc.lib.telemetry.FalconConfigLayout;
import frc.lib.telemetry.WidgetsAndLayouts;

// The Shuffleboard Main tab.
public class LauncherTelemetry {
    
    // ---------------------//
    // NetworkTableEntries //

    // ----------------//
    // Tab & Layouts //
    private static ShuffleboardTab m_tab;
    private FalconConfigLayout LauncherLeader;

    // ---------//
    // Widgets //

    // --------------//
    // Constructor //
    public LauncherTelemetry() {
        m_tab = Shuffleboard.getTab("Launcher");
    }

    // ---------------------//
    // initialize //
    // Create all View Widgets, ones you can't edit, created after subsystem
    // instances are made
    public void initialize() {
        // intakeLayout(m_tab).withPosition(0, 0);
        WidgetsAndLayouts.TalonFXLayout("Motor", m_tab, Robot.launcher.motorLeader).withPosition(0, 0);
        m_tab.addNumber("Velocity", ()-> Robot.launcher.motorLeader.getSelectedSensorVelocity()).withPosition(1, 0);
        m_tab.addNumber("WheelRPM", ()-> Robot.launcher.getWheelRPM()).withPosition(1, 1);
        //m_tab.addNumber("Hood Current Angle", ()-> Robot.launcher.hood.getCurrentAngle()).withPosition(1, 2);

        LauncherLeader = new FalconConfigLayout("Launcher Config", m_tab, Robot.launcher.motorLeader, 3, 0);
        //LauncherLeader.initialize();
    }

    // --------//
    // Update //
    public void update() { // This will be called in the robotPeriodic
        //LauncherLeader.update();
    }
}
