//Created by Spectrum3847

//Based on Code from Team364 - BaseFalconSwerve
//https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.subsystems.Swerve;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.Pigeon2;

import frc.lib.util.Conversions;
import frc.robot.commands.swerve.TeleopSwerve;
import frc.robot.constants.AutonConstants;
import frc.robot.constants.SwerveConstants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.telemetry.Log;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public static final String name = Log._drive;
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public Pigeon2 gyro;
    public double pidTurn = 0;
    public double drive_x = 0;
    public double drive_y = 0;
    public double drive_rotation = 0;
    private final Field2d m_field = new Field2d();
    private final Translation2d prevVelocity = new Translation2d(0, 0);
    public ProfiledPIDController thetaController = new ProfiledPIDController(AutonConstants.kPThetaController, 0, AutonConstants.kDThetaController,
                                                            AutonConstants.kThetaControllerConstraints);;
    public PIDController xController = new PIDController(AutonConstants.kPXController, 0, AutonConstants.kDXController);
    public PIDController yController = new PIDController(AutonConstants.kPYController, 0, AutonConstants.kDYController);


    public Swerve() {
        setName(name);
        gyro = new Pigeon2(CanIDs.pigeonID);
        gyro.configFactoryDefault();
        zeroGyro();
        
        swerveOdometry = new SwerveDriveOdometry(SwerveConstants.swerveKinematics, getYaw());

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, SwerveConstants.Mod0.constants),
            new SwerveModule(1, SwerveConstants.Mod1.constants),
            new SwerveModule(2, SwerveConstants.Mod2.constants),
            new SwerveModule(3, SwerveConstants.Mod3.constants)
        };

        //Setup thetaController used for auton and automatic turns
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        xController.setPID(AutonConstants.kPXController/100, 0, AutonConstants.kDXController/100);
        yController.setPID(AutonConstants.kPYController/100, 0, AutonConstants.kDYController/100);
        thetaController.setPID(AutonConstants.kPThetaController/100, 0, AutonConstants.kDThetaController/100);
        resetSteeringToAbsolute();
        setDefaultCommand(new TeleopSwerve(this, true, false));
    }    

    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getStates());   
        m_field.setRobotPose(swerveOdometry.getPoseMeters()); //Field is used for simulation and testing
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        //If pidTurn is getting a value override the drivers steering control
        if (pidTurn != 0) {
            rotation = pidTurn;
        }
        
        if (Math.abs(rotation) < 0.03){
            rotation = 0;
        }
        SwerveModuleState[] swerveModuleStates =
            SwerveConstants.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConstants.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
        drive_y = translation.getY();
        drive_x = translation.getX();
        drive_rotation = rotation;
    }

    public void useOutput(double output) {
        pidTurn = output * SwerveConstants.maxAngularVelocity;
    }

    //Used for control loops that give a rotational velocity directly
    public void setRotationalVelocity(double rotationalVelocity){
        pidTurn = rotationalVelocity;
    }

    //Reset AngleMotors to Absolute
    public void resetSteeringToAbsolute() {
        for (SwerveModule mod : mSwerveMods) {
            mod.resetToAbsolute();
        }
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(pose, getYaw());
    }

    /* Used by SwerveFollowCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveConstants.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void brakeMode (boolean enabled){
        for(SwerveModule mod : mSwerveMods){
            if (enabled){
                mod.mDriveMotor.setNeutralMode(NeutralMode.Brake);
            } else{
                mod.mDriveMotor.setNeutralMode(NeutralMode.Coast);
            }
        }
    }

    public void zeroGyro(){
        gyro.setYaw(0);
    }

    //Set gyro to a specific value
    public void setGyro(double value){
        gyro.setYaw(value);
    }

    public Rotation2d getYaw() {
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        return (SwerveConstants.invertGyro) ? Rotation2d.fromDegrees(360 - ypr[0]) : Rotation2d.fromDegrees(ypr[0]);
    }

    public double getDegrees() {
        return getYaw().getDegrees();
    }

    public double getRadians() {
        return getYaw().getRadians();
    }

    public SwerveModuleState[] getStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public void dashboard(){
        SmartDashboard.putData("Field", m_field);
    }

    //Characterization methods
    public double getLeftPositionMeters(){
        return Conversions.FalconToMeters(mSwerveMods[0].mDriveMotor.getSelectedSensorPosition(),
                SwerveConstants.wheelCircumference, SwerveConstants.driveGearRatio);
    }

    public double getRightPositionMeters(){
        return Conversions.FalconToMeters(mSwerveMods[1].mDriveMotor.getSelectedSensorPosition(),
                SwerveConstants.wheelCircumference, SwerveConstants.driveGearRatio);
    }

    public double getLeftMetersPerSec(){
        return Conversions.falconToMPS(mSwerveMods[0].mDriveMotor.getSelectedSensorVelocity()
        , SwerveConstants.wheelCircumference, SwerveConstants.driveGearRatio);
    }

    public double getRightMetersPerSec(){
        return Conversions.falconToMPS(mSwerveMods[1].mDriveMotor.getSelectedSensorVelocity()
        , SwerveConstants.wheelCircumference, SwerveConstants.driveGearRatio);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts){
        mSwerveMods[0].mDriveMotor.setVoltage(leftVolts);
        mSwerveMods[2].mDriveMotor.setVoltage(leftVolts);
        mSwerveMods[1].mDriveMotor.setVoltage(rightVolts);
        mSwerveMods[3].mDriveMotor.setVoltage(rightVolts);
    }

    public void stop(){
        for(SwerveModule mod : mSwerveMods){
            mod.mDriveMotor.stopMotor();
            mod.mAngleMotor.stopMotor();
        }
    }

    //DOES NOT WORK!!
    public Translation2d accelerationLimit(Translation2d desiredVelocitity, double desiredRotation){
        Translation2d trans2d;
        //Convert chassisSpeeds to Translation2D
        ChassisSpeeds currentCS = SwerveConstants.swerveKinematics.toChassisSpeeds(mSwerveMods[0].getState(), 
            mSwerveMods[1].getState(), mSwerveMods[2].getState(), mSwerveMods[3].getState());

        trans2d = new Translation2d(currentCS.vxMetersPerSecond, currentCS.vyMetersPerSecond);

        ChassisSpeeds desiredCS = ChassisSpeeds.fromFieldRelativeSpeeds(
            desiredVelocitity.getX(), 
            desiredVelocitity.getY(), 
                desiredRotation, 
                getYaw());

        
        //Computes difference betwee desired and actual velocities
        double accelX = (desiredCS.vxMetersPerSecond - currentCS.vxMetersPerSecond);
        double accelY = (desiredCS.vyMetersPerSecond - currentCS.vyMetersPerSecond);

        //Converts from cartesian to polar
        double accelNorm = new Translation2d(accelX, accelY).getNorm();
        double angle = Math.atan2(accelY, accelX);

        
        //Checks if it exceeed max acceleration
        if (accelNorm - SwerveConstants.maxAccel > 0){
            accelNorm = SwerveConstants.maxAccel;
        }

        //get limited veclocity vector vecotr difference in cartesian coordinate system
        //computes limited velocity 
        //Sends back to Swerve Drive
        Translation2d addedSpeed = new Translation2d(accelNorm, new Rotation2d(angle));

        return trans2d.plus(addedSpeed);
    }
}