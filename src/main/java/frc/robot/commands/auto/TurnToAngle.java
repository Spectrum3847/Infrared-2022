//Created by Spectrum3847
package frc.robot.commands.auto;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Robot;

public class TurnToAngle extends ProfiledPIDCommand {

  public static double kP = 0.01;
  public static double kI = 0; // 0.00015
  public static double kD = 0.000; // 0.0005

  boolean hasTarget = false;

  public TurnToAngle(double angle) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gainss
            kP, kI, kD,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(360, 1080)),
        // This should return the measurement
        Robot.swerve::getDegrees,
        // This should return the goal (can also be a constant)
        angle,
        // This uses the output
        (output, setpoint) -> Robot.swerve.useOutput(output));
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(1);
    getController().enableContinuousInput(0, 360);
    //double differance = angle - Robot.swerve.getDegrees();
    //if (differance > 180) {
      //differance = (360 - differance) * -1;
    //}
    //getController().setGoal(differance);
    addRequirements(Robot.swerve);
  }

  @Override
  public void initialize() {
    super.initialize();
  }

  @Override
  public void execute() {
    super.execute();
    Robot.swerve.drive(new Translation2d(0, 0), 0, true, false);
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.swerve.useOutput(0);
    Robot.swerve.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
