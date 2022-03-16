package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.auto.DoubleBallSimple;
import frc.robot.commands.auto.TaxiSimple;
import frc.robot.commands.auto.TestPathPlanner;
import frc.robot.constants.AutonConstants;

public class AutonSetup {

    //Positions
    public static final SendableChooser<Double> posChooser = new SendableChooser<>();

    //AutoRoutines
    private static final Command taxiSimple = new TaxiSimple();
    private static final Command DoubleBall = new DoubleBallSimple();
    private static final Command testPathFollow = new TestPathPlanner();

    // A chooser for autonomous commands
    public static final SendableChooser<Command> chooser = new SendableChooser<>();

    public static void setupSelectors() {
        posChooser.setDefaultOption("A. LEFT", AutonConstants.posAangle);
        posChooser.addOption("B. LEFT-CENTER", AutonConstants.posBangle);
        posChooser.addOption("C. RIGHT-CENTER", AutonConstants.posCangle);
        posChooser.addOption("D. RIGHT", AutonConstants.posCangle);
        posChooser.addOption("0 Degrees", 0.0);

        chooser.setDefaultOption("DoubleBall", DoubleBall);
        chooser.addOption("Taxi Simple", taxiSimple);
        chooser.addOption("Test Path Planner", testPathFollow);
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
