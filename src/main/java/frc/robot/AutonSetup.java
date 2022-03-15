package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.auto.RightDoubleBall;
import frc.robot.commands.auto.TestPathPlanner;
import frc.robot.constants.AutonConstants;

public class AutonSetup {

    //Positions
    public static final SendableChooser<Double> posChooser = new SendableChooser<>();

    //AutoRoutines
    private static final Command complexAuto = new PrintCommand("TEST COMPLEX AUTO");
    private static final Command DoubleBall = new RightDoubleBall();

    // A chooser for autonomous commands
    public static final SendableChooser<Command> chooser = new SendableChooser<>();

    public static void setupSelectors() {
        posChooser.setDefaultOption("1. Left", AutonConstants.pos1angle);
        posChooser.addOption("2. Middle", AutonConstants.pos2angle);
        posChooser.addOption("3. Right", AutonConstants.pos3angle);

        chooser.setDefaultOption("DoubleBall", DoubleBall);
        chooser.addOption("Complex Auto", complexAuto);
    }

    //Return the starting angle for each position
    public static double getAutonAngle(){
        return posChooser.getSelected();
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
