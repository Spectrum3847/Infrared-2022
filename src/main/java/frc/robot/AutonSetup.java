package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto.DoubleBallSimple;
import frc.robot.commands.auto.TaxiSimple;
import frc.robot.commands.auto.TestPathPlanner;
import frc.robot.commands.auto.TripleBallSimple;
import frc.robot.commands.auto.TurnToAngle;
import frc.robot.constants.AutonConstants;

public class AutonSetup {

    //Positions
    public static final SendableChooser<Double> posChooser = new SendableChooser<>();

    //AutoRoutines
    private static final Command taxiSimple = new TaxiSimple();
    private static final Command doubleBall = new DoubleBallSimple();
    private static final Command tripleBall = new TripleBallSimple();
    private static final Command testPathFollow = new TestPathPlanner();
    private static final Command turnTest = new WaitCommand(0.25).andThen(new TurnToAngle(AutonConstants.thirdBallTurnToGoal));
    private static final Command doNothing = new WaitCommand(10);

    // A chooser for autonomous commands
    public static final SendableChooser<Command> chooser = new SendableChooser<>();

    public static void setupSelectors() {
        posChooser.setDefaultOption("A. LEFT", AutonConstants.posAangle);
        posChooser.addOption("B. LEFT-CENTER", AutonConstants.posBangle);
        posChooser.addOption("C. RIGHT-CENTER", AutonConstants.posCangle);
        posChooser.addOption("D. RIGHT", AutonConstants.posCangle);
        posChooser.addOption("0 Degrees", 0.0);

        chooser.setDefaultOption("DoubleBall", doubleBall);
        chooser.addOption("TripleBall", tripleBall);
        chooser.addOption("Taxi Simple", taxiSimple);
        chooser.addOption("Test Path Planner", testPathFollow);
        chooser.addOption("TurnTest", turnTest);
        chooser.addOption("doNothing", doNothing);

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
