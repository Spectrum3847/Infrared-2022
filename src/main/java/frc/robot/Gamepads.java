//Created by Spectrum3847
//Based on code by FRC4141
package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.gamepads.AndButton;
import frc.lib.gamepads.AndNotButton;
import frc.lib.gamepads.AxisButton;
import frc.lib.gamepads.XboxGamepad;
import frc.lib.gamepads.AxisButton.ThresholdType;
import frc.lib.gamepads.XboxGamepad.XboxAxis;
import frc.lib.gamepads.mapping.ExpCurve;
import frc.lib.util.Alert;
import frc.robot.Robot.RobotState;
import frc.robot.commands.ClimberCommands;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.ballpath.BallPathCommands;
import frc.robot.commands.swerve.LLAim;

public class Gamepads {
	// Create Joysticks first so they can be used in defaultCommands
	public static XboxGamepad driver = new XboxGamepad(0, 0, 0);
	public static ExpCurve throttleCurve = new ExpCurve(1.2, 0, -1.0, 0.15);
	public static ExpCurve steeringCurve = new ExpCurve(1.2, 0, -0.8, 0.07);
	public static XboxGamepad operator = new XboxGamepad(1, .1, .1);
	public static boolean driverConfigured = false;
	public static boolean operatorConfigured = false;
	public static Alert noDriverAlert = new Alert("Alerts", "No Driver Controller Found", Alert.AlertType.WARNING);
	public static Alert noOperatorAlert= new Alert("Alerts", "No Operator Controller Found", Alert.AlertType.WARNING);;

	// Configure all the controllers
	public static void configure() {
		configureDriver();
		configureOperator();
	}

	public static void resetConfig() {
		CommandScheduler.getInstance().clearButtons();
		driverConfigured = false;
		operatorConfigured = false;
		configure();
	}

	// Configure the driver controller
	public static void configureDriver() {
		// Detect whether the xbox controller has been plugged in after start-up
		if (!driverConfigured) {
			boolean isConnected = driver.isConnected();
			if (!isConnected) {
				noDriverAlert.set(true);
				return;
			}

			// Configure button bindings once the driver controller is connected
			if (Robot.getState() == RobotState.TEST) {
				driverTestBindings();
			} else {
				driverBindings();
			}
			driverConfigured = true;
			
			noDriverAlert.set(false);
		}
	}

	// Configure the operator controller
	public static void configureOperator() {
		// Detect whether the xbox controller has been plugged in after start-up
		if (!operatorConfigured) {
			boolean isConnected = operator.isConnected();
			if (!isConnected){
				noOperatorAlert.set(true);
				return;
			}

			// Configure button bindings once operatorIsConnected is true
			if (Robot.getState() == RobotState.TEST) {
				operatorTestBindings();
			} else {
				operatorBindings();
			}
			operatorConfigured = true;

			
			noOperatorAlert.set(false);
		}
	}

	public static void driverBindings() {
		// Driver Controls
		// Reset Gyro based on left bumper and the abxy buttons
		new AndButton(driver.leftBumper, driver.yButton).whileHeld(new ResetGyro(0));
		new AndButton(driver.leftBumper, driver.xButton).whileHeld(new ResetGyro(90));
		new AndButton(driver.leftBumper, driver.aButton).whileHeld(new ResetGyro(180));
		new AndButton(driver.leftBumper, driver.bButton).whileHeld(new ResetGyro(270));

		new AndNotButton(driver.aButton, driver.leftBumper).whileHeld(BallPathCommands.intakeBalls());
		new AndNotButton(driver.bButton, driver.leftBumper).whileHeld(BallPathCommands.sortBalls());
		new AndNotButton(driver.yButton, driver.leftBumper).whileHeld(BallPathCommands.eject());
		new AndNotButton(driver.xButton, driver.leftBumper).whileHeld(BallPathCommands.feed());
		driver.startButton.whenPressed(BallPathCommands.llShotRPM());
		driver.selectButton.whenPressed(BallPathCommands.stopLauncher());
		
		
		// Aim with limelight
		driver.rightBumper.whileHeld(new LLAim());
	}

	public static void operatorBindings() {
		//Intake
		operator.aButton.whileHeld(BallPathCommands.intakeBalls());

		//Eject
		operator.yButton.whileHeld(BallPathCommands.eject());

		//Feeder
		operator.xButton.whileHeld(BallPathCommands.feed());

		//Unjam
		operator.bButton.whileHeld(BallPathCommands.unJamAll());

		// Fender
		operator.leftTriggerButton.whileHeld(BallPathCommands.fenderShot());

		// Tarmac
		operator.rightTriggerButton.whileHeld(BallPathCommands.tarmacShot());

		// Far Shot
		operator.rightBumper.whileHeld(BallPathCommands.farShot());

		// Climber Controls
		operator.Dpad.Up.whenPressed(ClimberCommands.fullUp());
		operator.Dpad.Down.whenPressed(ClimberCommands.climb());
		operator.Dpad.Left.whenPressed(ClimberCommands.nextRungDown());
		operator.Dpad.Right.whenPressed(ClimberCommands.nextRungUp());
		operator.selectButton.whenPressed(ClimberCommands.hang());
		operator.startButton.whenPressed(ClimberCommands.pull());

		//Manual climb tilt control
		operator.leftBumper.whileHeld(ClimberCommands.tiltUp());

		//Return to default command when pressed
		operator.leftStickButton.whileHeld(Robot.climber.defaultCommand());
		new AxisButton(operator, XboxAxis.LEFT_Y, 0.15, ThresholdType.DEADBAND)
			.whenPressed(Robot.climber.defaultCommand());
	}

	// Configure the button bindings for the driver control in Test Mode
	public static void driverTestBindings() {

	}

	// Configure the button bindings for the driver control in Test Mode
	public static void operatorTestBindings() {

	}

	public static double getClimberJoystick(){
		double value = operator.leftStick.getY() * -1;
		if (value > 0){
			return value * 0.4;
		}
		return value;
	}

	public static double getDriveY(){
		return throttleCurve.calculateMappedVal(driver.leftStick.getY());
	}

	public static double getDriveX(){
		return throttleCurve.calculateMappedVal(driver.leftStick.getX());
	}

	public static double getDriveR(){
		double value = driver.triggers.getTwist();
		value = steeringCurve.calculateMappedVal(value);
		return value;
	}
}
