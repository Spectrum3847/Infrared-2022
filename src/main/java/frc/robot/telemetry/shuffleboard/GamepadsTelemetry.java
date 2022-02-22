//Created by Spectrum3847

package frc.robot.telemetry.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import frc.lib.gamepads.XboxGamepad;
import frc.lib.telemetry.CustomLayout;
import frc.robot.Gamepads;

public class GamepadsTelemetry {

    //---------------------//
    // NetworkTableEntries //
    //---------------------//

    //----------------//
    // Tab & Layouts  //
    private static ShuffleboardTab m_tab;
    private gamepadLayout driver;
    private gamepadLayout operator;

    
    public static SimpleWidget m_EnableWidget;

    //--------------//
    // Constructor  //
    public GamepadsTelemetry() {
        m_tab = Shuffleboard.getTab("Gamepads");
    }

    //---------------------//
    // initializeViewable  //
    // Create all View Widgets, ones you can't edit, created after subsystem instances are made
    public void initialize() {
        driver = new gamepadLayout("Driver 0", m_tab, Gamepads.driver);
        operator = new gamepadLayout("Operator 1", m_tab, Gamepads.operator);
        driver.initialize();
        operator.initialize();
        m_EnableWidget = m_tab.add("Update Enable", false).withWidget(BuiltInWidgets.kToggleButton).withPosition(5, 0);
    }


    //--------//
    // Update //
    public void update() {
        if (m_EnableWidget.getEntry().getBoolean(false)) {
            driver.update();
            operator.update();
        }
    }

    private class gamepadLayout extends CustomLayout{
        public NetworkTableEntry yLeftDeadbandEntry;
        public NetworkTableEntry xLeftDeadbandEntry;
        public NetworkTableEntry yRightDeadbandEntry;
        public NetworkTableEntry xRightDeadbandEntry;
        public NetworkTableEntry yLeftExpValEntry;
        public NetworkTableEntry xLeftExpValEntry;
        public NetworkTableEntry yRightExpValEntry;
        public NetworkTableEntry xRightExpValEntry;
        public NetworkTableEntry yLeftScalarValEntry;
        public NetworkTableEntry xLeftScalarValEntry;
        public NetworkTableEntry yRightScalarValEntry;
        public NetworkTableEntry xRightScalarValEntry;
        private XboxGamepad gamepad;

        public gamepadLayout(String name, ShuffleboardTab tab, XboxGamepad gamepad){
            super(name, tab);
            this.gamepad = gamepad;
            setColumnsAndRows(2, 6);
            setSize(2, 4);
        }

        public void initialize() {
            yLeftDeadbandEntry = quickAddPersistentWidget("yLeftDeadband", gamepad.leftStick.expYCurve.getDeadzone(), 0, 0);
            xLeftDeadbandEntry = quickAddPersistentWidget("xLeftDeadband", gamepad.leftStick.expXCurve.getDeadzone(), 0, 1);
            yRightDeadbandEntry = quickAddPersistentWidget("yRightDeadband", gamepad.rightStick.expYCurve.getDeadzone(), 1, 0);
            xRightDeadbandEntry = quickAddPersistentWidget("xRightDeadband", gamepad.rightStick.expXCurve.getDeadzone(), 1, 1);
            yLeftExpValEntry = quickAddPersistentWidget("yLeftExpVal", gamepad.leftStick.expYCurve.getExpVal(), 0, 2);
            xLeftExpValEntry = quickAddPersistentWidget("xLeftExpVal", gamepad.leftStick.expXCurve.getExpVal(), 0, 3);
            yRightExpValEntry = quickAddPersistentWidget("yRightExpVal", gamepad.rightStick.expYCurve.getExpVal(), 1, 2);
            xRightExpValEntry = quickAddPersistentWidget("xRightExpVal", gamepad.rightStick.expXCurve.getExpVal(), 1, 3);
            yLeftScalarValEntry = quickAddPersistentWidget("yLeftScalar", gamepad.leftStick.expYCurve.getScalar(), 0, 4);
            xLeftScalarValEntry = quickAddPersistentWidget("xLeftScalar", gamepad.leftStick.expXCurve.getScalar(), 0, 5);
            yRightScalarValEntry = quickAddPersistentWidget("yRightScalar", gamepad.rightStick.expYCurve.getScalar(), 1, 4);
            xRightScalarValEntry = quickAddPersistentWidget("xRightScalar", gamepad.rightStick.expXCurve.getScalar(), 1, 5);
        }

        public void update(){
            gamepad.leftStick.expYCurve.setDeadzone(yLeftDeadbandEntry.getDouble(0));
            gamepad.leftStick.expXCurve.setDeadzone(xLeftDeadbandEntry.getDouble(0));
            gamepad.rightStick.expYCurve.setDeadzone(yRightDeadbandEntry.getDouble(0));
            gamepad.rightStick.expXCurve.setDeadzone(xRightDeadbandEntry.getDouble(0));
            
            gamepad.leftStick.expYCurve.setExpVal(yLeftExpValEntry.getDouble(0));
            gamepad.leftStick.expXCurve.setExpVal(xLeftExpValEntry.getDouble(0));
            gamepad.rightStick.expYCurve.setExpVal(yRightExpValEntry.getDouble(0));
            gamepad.rightStick.expXCurve.setExpVal(xRightExpValEntry.getDouble(0));
            
            gamepad.leftStick.expYCurve.setScalar(yLeftScalarValEntry.getDouble(0));
            gamepad.leftStick.expXCurve.setScalar(xLeftScalarValEntry.getDouble(0));
            gamepad.rightStick.expYCurve.setScalar(yRightScalarValEntry.getDouble(0));
            gamepad.rightStick.expXCurve.setScalar(xRightScalarValEntry.getDouble(0));
        }

    }
}

