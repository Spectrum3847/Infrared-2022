//Created by Spectrum3847
package frc.robot;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.util.Alert;
import frc.lib.util.SpectrumPreferences;
import frc.lib.util.Alert.AlertType;
import frc.lib.sim.PhysicsSim;
import frc.robot.constants.Constants;
import frc.robot.constants.PracticeConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.VisionLL;
import frc.robot.subsystems.Swerve.Swerve;
import frc.robot.subsystems.Vision.Vision;
import frc.robot.subsystems.Vision.VisionIOLimelight;
import frc.robot.telemetry.Dashboard;
import frc.robot.telemetry.Log;
import frc.robot.telemetry.ShuffleboardTabs;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // subsystems and hardware are defined here
    public static Swerve swerve;
    public static Intake intake;
    public static Indexer indexer;
    public static Feeder feeder;
    public static Launcher launcher;
    public static Climber climber;
    public static VisionLL visionLL;
    public static Vision vision;
    public static Pneumatics pneumatics;
    public static PowerDistribution pdh;
    public static SpectrumPreferences prefs;
    public static ShuffleboardTabs shuffleboardTabs;

    private void intializeSubsystems() {
        swerve = new Swerve();
        intake = new Intake();
        indexer = new Indexer();
        feeder = new Feeder();
        launcher = new Launcher();
        climber = new Climber();
        visionLL = new VisionLL();
        vision = new Vision(new VisionIOLimelight());
        vision.setTranslationConsumer(visionLL::setTranslationToGoal);
        pneumatics = new Pneumatics();
        pdh = new PowerDistribution(1, ModuleType.kRev);
        prefs = SpectrumPreferences.getInstance();
        shuffleboardTabs = new ShuffleboardTabs();
    }

    // AutonCommand
    private Command m_autonomousCommand;

    /**
     * Robot State Tracking Setup
     */
    public enum RobotState {
        DISABLED, AUTONOMOUS, TELEOP, TEST
    }

    public static RobotState s_robot_state = RobotState.DISABLED;

    public static RobotState getState() {
        return s_robot_state;
    }

    public static void setState(final RobotState state) {
        s_robot_state = state;
    }

    public static String MAC = "";
    public static boolean isPractice = false;

    // Alerts
    public static Alert batteryAlert = new Alert("Low Battery", AlertType.WARNING);
    public static Alert practiceRobotAlert = new Alert("Practice robot selected", Alert.AlertType.WARNING);
    public static Alert FMSConnectedAlert = new Alert("FMS Connected", Alert.AlertType.WARNING);

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        printNormal("Start robotInit()");
        DataLogManager.start();
        checkIfPracticeRobot();
        intializeSubsystems();
        Dashboard.intializeDashboard();
        shuffleboardTabs.initialize();
        visionLL.limeLightLEDOn();
        Gamepads.resetConfig(); // Reset Gamepad Configs
        Log.initLog(); // Config the Debugger based on FMS state
        printNormal("End robotInit()");
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();

        // Ensure the controllers are always configured
        Gamepads.configure();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
        setState(RobotState.DISABLED);
        printNormal("Start disabledInit(), MAC Address:" + MAC);
        checkIfPracticeRobot();
        pneumatics.checkIfPractice();
        checkFMS();
        Log.initLog(); // Config the Debugger based on FMS state
        Gamepads.resetConfig();
        ; // Reset Gamepad Configs
        CommandScheduler.getInstance().cancelAll(); // Disable any currently running commands
        LiveWindow.setEnabled(false); // Disable Live Window we don't need that data being sent
        LiveWindow.disableAllTelemetry();
        swerve.resetSteeringToAbsolute();
        swerve.brakeMode(false); // Set the swerve to coast mode for disabled
        printNormal("End disabledInit()");
    }

    @Override
    public void disabledPeriodic() {
        batteryAlert.set(checkBattery());
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        setState(RobotState.AUTONOMOUS);
        printNormal("Start autonomousInit()");
        Log.initLog(); // Config the Debugger based on FMS state
        CommandScheduler.getInstance().cancelAll(); // Disable any currently running commands
        LiveWindow.setEnabled(false); // Disable Live Window we don't need that data being sent
        LiveWindow.disableAllTelemetry();

        // schedule the autonomous command (example)
        visionLL.limeLightLEDOn();
        m_autonomousCommand = AutonSetup.getAutonomousCommand();
        double angle = AutonSetup.getAutonAngle();
        Robot.swerve.setGyroDegrees(angle);
        swerve.brakeMode(true); // Set the swerve to brake mode for Auton

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }

        printNormal("End autonomousInit()");
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
    }

    public void autonomousExit(){
        Robot.swerve.stop();
    }

    @Override
    public void teleopInit() {
        setState(RobotState.TELEOP);
        printNormal("Start teleopInit()");
        CommandScheduler.getInstance().cancelAll(); // Disable any currently running commands
        Log.initLog(); // Config the Debugger based on FMS state
        Gamepads.resetConfig();
        ; // Reset Gamepad Configs
        LiveWindow.setEnabled(false); // Disable Live Window we don't need that data being sent
        LiveWindow.disableAllTelemetry();
        swerve.brakeMode(false); //Force the robot to coast for telop
        printNormal("End teleopInit()");
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
    }

    public void teleopExit(){
        Robot.swerve.stop();
    }

    @Override
    public void testInit() {
        setState(RobotState.TEST);
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
        Gamepads.resetConfig();
        ; // Reset Gamepad Configs
        Log.initLog(); // Config the Debugger based on FMS state
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }

    public void simulationInit() {
        Sim.intialization();
    }

    public void simulationPeriodic() {
        PhysicsSim.getInstance().run();
    }

    public boolean checkFMS() {
        if (Constants.isFMSEnabled()) {
            FMSConnectedAlert.set(true);
            return true;
        } else {
            FMSConnectedAlert.set(false);
            return false;
        }
    }

    public boolean checkBattery() {
        if (RobotController.getBatteryVoltage() < 12.0) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkIfPracticeRobot() {
        if (MAC.equals("")) {
            getMACaddress();
        }
        if (MAC.equals(PracticeConstants.MAC_ADDRESS)) {
            isPractice = true;
            practiceRobotAlert.set(true);
            PracticeConstants.practiceBotConstantsOverride();
        }
        return isPractice;
    }

    private static String getMACaddress() {
        InetAddress localHost;
        NetworkInterface ni;
        byte[] hardwareAddress;
        try {
            localHost = InetAddress.getLocalHost();
            ni = NetworkInterface.getByInetAddress(localHost);
            hardwareAddress = ni.getHardwareAddress();
            String[] hexadecimal = new String[hardwareAddress.length];
            for (int i = 0; i < hardwareAddress.length; i++) {
                hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
            }
            MAC = String.join(":", hexadecimal);
            return MAC;
        } catch (UnknownHostException | SocketException | NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    // ---------------//
    // Print Methods //
    // ---------------//
    public static void printLow(String msg) {
        Log.println(msg, Log._general, Log.low1);
    }

    public static void printNormal(String msg) {
        Log.println(msg, Log._general, Log.normal2);
    }

    public static void printHigh(String msg) {
        Log.println(msg, Log._general, Log.high3);
    }
}
