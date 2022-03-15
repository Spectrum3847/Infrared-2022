package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.auto.RightDoubleBall;
import frc.robot.commands.auto.TestPathPlanner;

public class AutonSetup {

    // A simple auto routine that drives forward a specified distance, and then
    // stops.
    private static final Command simpleAuto = new PrintCommand("TEST SIMPLE AUTO");

    // A complex auto routine that drives forward, drops a hatch, and then drives
    // backward.
    private static final Command complexAuto = new PrintCommand("TEST COMPLEX AUTO");

    private static final Command testPathPlanner = new RightDoubleBall();

    // A chooser for autonomous commands
    public static final SendableChooser<Command> chooser = new SendableChooser<>();

    public static void setupSelector() {
        chooser.setDefaultOption("Simple Auto", simpleAuto);
        chooser.addOption("Complex Auto", complexAuto);
        chooser.addOption("Test Path Planner", testPathPlanner);
    }
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public static Command getAutonomousCommand() {
        // return new CharacterizeLauncher(Robot.launcher);
        return chooser.getSelected();
    }
}
