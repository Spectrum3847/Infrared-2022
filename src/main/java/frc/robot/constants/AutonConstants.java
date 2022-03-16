package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.lib.util.GeomUtil;

public final class AutonConstants {
    public static final double posAangle = 160; //Infront of Left Ball (Cargo B)
    public static final double posA90angle = 91.5; //Sideways A setup, intake toward field boarder
    public static final double posBangle = 120;
    public static final double posCangle = 220; //Pointing to Cargo D
    public static final double posDangle = 270; //Infront of Right Ball (Cargo E) perpendicular to field
    public static final double posD90angle = 181.5; //Sideways D Setup intake facing the driver station

    public static final double kMaxSpeedMetersPerSecond = SwerveConstants.maxSpeed * 0.75;
    public static final double kMaxAccelerationMetersPerSecondSquared = kMaxSpeedMetersPerSecond; // 1 sec to full speed
    public static final double kMaxAngularSpeedRadiansPerSecond = SwerveConstants.maxAngularVelocity;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = SwerveConstants.maxAngularVelocity;

    public static final double kPXController = 500;//2300;
    public static final double kDXController = 0;//20;
    public static final double kPYController = 500;//2300;
    public static final double kDYController = 0;//20;
    public static final double kPThetaController = 2500;//1000;
    public static final double kDThetaController = 0;

    // Constraint for the motion profilied robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
        new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

    public static enum AutoPosition {
        ORIGIN, TARMAC_A, TARMAC_B, TARMAC_C, TARMAC_D;
    
        public Pose2d getPose() {
            switch (this) {
            case ORIGIN:
                return new Pose2d();
            case TARMAC_A: //Left Auto Setup
                return FieldConstants.referenceA
                    .transformBy(GeomUtil.transformFromTranslation(-0.5, 0.7));
            case TARMAC_B: //LeftCenter Setup NOT USED
                return FieldConstants.referenceB
                    .transformBy(GeomUtil.transformFromTranslation(-0.5, -0.2));
            case TARMAC_C: //RightCenter Setup NOT USED
                return FieldConstants.referenceC
                    .transformBy(GeomUtil.transformFromTranslation(-0.5, -0.1));
            case TARMAC_D: //Right Auto Setup
                return FieldConstants.referenceD
                    .transformBy(GeomUtil.transformFromTranslation(-0.5, -0.7));
            default:
                return new Pose2d();
            }
        }
    }
}
