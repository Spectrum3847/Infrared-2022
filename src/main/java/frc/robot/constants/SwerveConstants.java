//Created by Spectrum3847
package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.lib.swerve.SwerveModuleConstants;
import frc.robot.constants.Constants.CanIDs;

public final class SwerveConstants {
    public static final double DRIVE_PERIOD = 20;
    public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

    /* Drivetrain Constants */
    public static final double trackWidth = Units.inchesToMeters(18.75);
    public static final double wheelBase = Units.inchesToMeters(21.75);
    public static final double wheelDiameter = Units.inchesToMeters(3.8195); //3.94 //3.791);
    public static final double wheelCircumference = wheelDiameter * Math.PI;

    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    public static final double driveGearRatio = (6.75 / 1.0); //6.75:1
    public static final double angleGearRatio = (50.0/14.0) * (60.0/10.0);//(12.8 / 1.0); //12.8:1

    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

    /* Swerve Current Limiting */
    public static final int angleContinuousCurrentLimit = 20;
    public static final int anglePeakCurrentLimit = 30;
    public static final double anglePeakCurrentDuration = 0.1;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveContinuousCurrentLimit = 40;
    public static final int drivePeakCurrentLimit = 40;
    public static final double drivePeakCurrentDuration = 0.0;
    public static final boolean driveEnableCurrentLimit = true;

    /* Angle Motor PID Values */
    public static final double angleKP = 0.6;//364 = 0.6; SDS = 0.2;
    public static final double angleKI = 0.0;
    public static final double angleKD = 12;//364 = 12.0; SDS = 0.1;
    public static final double angleKF = 0.0;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.1; //0.10 was previous, switched to 1678 value 0.05
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKF = 0.0;

    /* Drive Motor Characterization Values */
    public static final double driveKS = (0.605 / 12); // (0.667 / 12); //divide by 12 to convert from volts to percent output for CTRE
    public static final double driveKV = (1.72 / 12); //(2.44 / 12);
    public static final double driveKA = (0.193/12); //(0.27 / 12);

    /* Swerve Profiling Values */
    public static final double motorFreeSpeed = 6380.0;
    public static final double maxSpeed = 4.8;//4.5; //meters per second
    public static final double maxAccel = maxSpeed * 1.5; //take 1/2 sec to get to max speed.
    public static final double autoMaxSpeed = maxSpeed * 0.7; //70% of full speed in auto
    public static final double autoMaxAccel = autoMaxSpeed * 2; //Take 1/2 sec to get to full speed
    public static final double maxAngularVelocity = 3 * Math.PI; //11.5; //4 revolutions a second
    public static final double maxAngularAcceleration = maxAngularVelocity * 2; //take 1/2 sec to get to max angular velocity

    /* Neutral Modes */
    public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
    public static final NeutralMode driveNeutralMode = NeutralMode.Coast;

    /* Motor Inverts */
    public static final boolean driveMotorInvert = true;
    public static final boolean angleMotorInvert = true;

    /* Angle Encoder Invert */
    public static final boolean canCoderInvert = false;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */
    public static final class Mod0 {
        public static final int driveMotorID = CanIDs.driveMotor0;
        public static final int angleMotorID = CanIDs.angleMotor0;
        public static final int canCoderID = 3;
        public static final double angleOffsetP = 12.15+180;
        public static final double angleOffsetC = 322.822;
        public static double angleOffset = angleOffsetC; 
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 {
        public static final int driveMotorID = CanIDs.driveMotor1;
        public static final int angleMotorID = CanIDs.angleMotor1;
        public static final int canCoderID = 13;
        public static final double angleOffsetP = 531.103;
        public static final double angleOffsetC = 126.826;
        public static double angleOffset = angleOffsetC;  
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }
    
    /* Back Left Module - Module 2 */
    public static final class Mod2 {
        public static final int driveMotorID = CanIDs.driveMotor2;
        public static final int angleMotorID = CanIDs.angleMotor2;
        public static final int canCoderID = 23;
        public static final double angleOffsetP = 74.2+180;
        public static final double angleOffsetC = 142.2;
        public static double angleOffset = angleOffsetC;  
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 {
        public static final int driveMotorID = CanIDs.driveMotor3;
        public static final int angleMotorID = CanIDs.angleMotor3;
        public static final int canCoderID = 33;
        public static final double angleOffsetP = 309.11;
        public static final double angleOffsetC = 74.8828;
        public static double angleOffset = angleOffsetC; 
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }
}
