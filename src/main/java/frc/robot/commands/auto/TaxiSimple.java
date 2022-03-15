//Created by Spectrum3847
package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.swerve.SwerveDrive;

//Wait 5 seconds before driving out of tarmac
public class TaxiSimple extends ParallelCommandGroup {
  /** Creates a new TestPathFollowing. */
  public TaxiSimple() {
    addCommands(
      new WaitCommand(5),
      new SwerveDrive(false, 0.2, 0).withTimeout(1.5)
    );
  }

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}
