//Created by Spectrum3847
package frc.robot.commands.auto;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Robot;
import frc.robot.constants.AutonConstants;

public class DriveToMeters extends ProfiledPIDCommand {

  public static double kP = 1;
  public static double kI = 0; // 0.00015
  public static double kD = 0.000; // 0.0005

  private static Pose2d intialPose = new Pose2d();

  public DriveToMeters(double distanceMeters) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gainss
            kP, kI, kD,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(AutonConstants.kMaxSpeed, AutonConstants.kMaxAccel)),
        // This should return the measurement
        DriveToMeters::getDistance,
        // This should return the goal (can also be a constant)
        distanceMeters,
        // This uses the output
        (output, setpoint) -> Robot.swerve.drive(new Translation2d(output, 0), 0, false, false));

    getController().setTolerance(Units.inchesToMeters(1));
    addRequirements(Robot.swerve);
  }

  @Override
  public void initialize() {
    super.initialize();
    intialPose = Robot.swerve.getPose();
  }

  private static double getDistance(){
    return Robot.swerve.getPose().minus(intialPose).getTranslation().getNorm();
  }

  @Override
  public void execute() {
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Robot.swerve.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
