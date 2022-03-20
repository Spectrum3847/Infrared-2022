// Copied from 6328

package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

/** Constants for the vision camera. */
public final class VisionConstants {
  public static final int widthPixels = 960;
  public static final int heightPixels = 720;
  public static final Rotation2d fovHorizontal = Rotation2d.fromDegrees(59.6);
  public static final Rotation2d fovVertical = Rotation2d.fromDegrees(49.7);

  private static final double cameraHeight = Units.inchesToMeters(31.5);
  private static final double verticalRotationDegrees = 30;
  private static final double offsetX = Units.inchesToMeters(0);

  //custom visionLL stuff
  public static final double limelightHeight = Units.inchesToMeters(31.5);
  public static final double limelightAngle = Units.degreesToRadians(30);
  public static final double targetHeight = Units.inchesToMeters(105.5);

  public static CameraPosition getCameraPosition() {
        return new CameraPosition(cameraHeight,
            Rotation2d.fromDegrees(verticalRotationDegrees),
            new Transform2d(new Translation2d(offsetX, 0.0),
                Rotation2d.fromDegrees(180.0)));
  }

  public static final class CameraPosition {
    public final double cameraHeight;
    public final Rotation2d verticalRotation;
    public final Transform2d vehicleToCamera;

    public CameraPosition(double cameraHeight, Rotation2d verticalRotation,
        Transform2d vehicleToCamera) {
      this.cameraHeight = cameraHeight;
      this.verticalRotation = verticalRotation;
      this.vehicleToCamera = vehicleToCamera;
    }
  }
}
