//Created by Spectrum3847
//Based on Code from FRC# 4141 
package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.AutonSetup;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import java.util.Map;

// The Shuffleboard Main tab.
public class MainTelemetry {

    // ----------------//
    // Default Values //
    // ----------------//

    // ---------------------//
    // NetworkTableEntries //
    // ---------------------//
    public static NetworkTableEntry flashEntry;

    // ----------------//
    // Tab & Layouts //
    // ----------------//
    private ShuffleboardTab m_tab;

    // ---------//
    // Widgets //
    // ---------//
    public static SimpleWidget m_flashWidget;
    public static SimpleWidget m_limelightLEDenable;
    public static SimpleWidget m_enableTabsWidget;
    public static ComplexWidget m_autonSelectorWidget;
    public static ComplexWidget m_autonPositionWidget;
    public static ComplexWidget m_autonColorWidget;

    // --------------//
    // Constructor //
    // --------------//
    public MainTelemetry() {
        m_tab = Shuffleboard.getTab("Main");
    }

    // ---------------------//
    // initialize //
    // ---------------------//
    // Create all View Widgets, ones you can't edit, created after subsystem
    // instances are made
    public void initialize() {
        matchTimeWidget().withPosition(0, 1);
        flashWidget().withPosition(0, 0);
        m_tab.addBoolean("Compressor on?", () -> Robot.pneumatics.isCompressorEnabled()).withPosition(1, 1);
        m_tab.addNumber("Pressure", () -> Robot.pneumatics.getPressure()).withPosition(1, 0);
        //m_tab.addNumber("FPGA timestamp", () -> Timer.getFPGATimestamp()).withPosition(0, 4);
        //m_limelightLEDenable = m_tab.add("Limelight LED Enable", true).withWidget(BuiltInWidgets.kToggleButton)
        //        .withPosition(2, 0);
        m_tab.addNumber("LL-Distance", () -> Robot.visionLL.getLLDistance()).withPosition(2, 1);
        m_tab.addNumber("Target Distance", () -> Robot.visionLL.getActualDistance()).withPosition(2, 2);
        m_enableTabsWidget = m_tab.add("Update Enable", false).withWidget(BuiltInWidgets.kToggleButton).withPosition(3,
                0).withSize(1, 1);
        AutonSetup.setupSelectors();
        m_autonSelectorWidget = m_tab.add(AutonSetup.chooser).withPosition(4, 0).withSize(3, 1);
        m_autonPositionWidget = m_tab.add(AutonSetup.posChooser).withPosition(4, 1).withSize(3, 1);
        m_autonColorWidget = m_tab.add(AutonSetup.colorChooser).withPosition(4, 2).withSize(3, 1);
    }

    // Match Time
    public SuppliedValueWidget<Double> matchTimeWidget() {
        SuppliedValueWidget<Double> m_matchTimeWidget = m_tab.addNumber("Match Time",
                () -> DriverStation.getMatchTime());
        m_matchTimeWidget.withWidget(BuiltInWidgets.kDial);
        m_matchTimeWidget.withProperties(Map.of("min", -1, "max", 135));
        return m_matchTimeWidget;
    }

    // Flash
    public SimpleWidget flashWidget() {
        m_flashWidget = m_tab.add("Flash", false);
        flashEntry = m_flashWidget.getEntry();
        m_flashWidget.withWidget(BuiltInWidgets.kBooleanBox);
        m_flashWidget.withProperties(Map.of("Color when true", "#1a0068", "Color when false", "#FFFFFF"));
        return m_flashWidget;
    }

    // --------//
    // Update //
    // --------//
    static boolean b = true;

    public void update() { // This will be called at a rate setup in ShufflbeboardTabs
        b = !b;
        flashEntry.setBoolean(b);
    }

    public static boolean getLimeLightToggle() {
        return m_limelightLEDenable.getEntry().getBoolean(true);
    }
}
