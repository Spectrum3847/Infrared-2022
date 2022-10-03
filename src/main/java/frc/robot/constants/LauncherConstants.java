//Created by Spectrum3847

package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import frc.lib.motorControllers.TalonFXSetup;

public final class LauncherConstants{
    public static final String name = "Launcher";

    
    public final static double lowGoalShotAngle = 68;
    public final static double tarmacShotAngle = 68;
    public final static double farShotAngle = 68;
    public final static double LowGoalShotSpeed = 1185; //1175 was a little short on 2nd ball
    public final static double tarmacShotSpeed = 2155; //2150 was previous
    public final static double farShotSpeed = 2460;
    public final static double visionBaseSpeedC = 1555;
    public final static double visionBaseSpeedP = 1700; //was 1750 adujted after it overshot, 1650 and it undershot
    public static double visionBaseSpeed = visionBaseSpeedC;//3847 = 1555, 8515 = 1750 (changed for Photon 8515 for TRI)
    public final static double visionPerFootSpeedC = 58;//60; //58
    public final static double visionPerFootSpeedP = 60;
    public static double visionPerFootSpeed = visionPerFootSpeedC;
    public final static double defaultSpeed = LowGoalShotSpeed;

    //Physical Constants
    public static final double diameterInches = 4;
    public static final double diameterMeters = diameterInches * 0.0254;

    public static final double hoodGearRatio = 27/17;

    public static final double wheelCircumferenceMeters = diameterMeters * Math.PI;
    public static final double wheelCircumferenceInches = diameterInches * Math.PI;

    public static final double maxRPM = 6000;

    /* Motor Characterization Values */
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;

    //Falcon Setup
    public static TalonFXConfiguration config = new TalonFXConfiguration();

    /* Inverted */
    public static final boolean kInverted = false;
    public static final boolean kFollowerInverted = true;

    /* Neutral Modes */
    public static final NeutralMode kNeutralMode = NeutralMode.Coast;

    /* Control Loop Constants */
    public static final double kP = 0.07;//0.065 is states, 0.0465;
    public static final double kI = 0.001; //.005
    public static final double kD = 0;
    public static final double kF = 0.053; //.519
    public static final double kIz = 150;
    public static final double motionCruiseVelocity = 0;
    public static final double motionAcceleration = 0;

    /* Current Limiting */
    public static final int currentLimit = 40;
    public static final int tirggerThresholdLimit = 45;
    public static final double PeakCurrentDuration = 0.5;
    public static final boolean EnableCurrentLimit = true;
    public static final SupplyCurrentLimitConfiguration supplyLimit = new SupplyCurrentLimitConfiguration(
        EnableCurrentLimit, currentLimit, tirggerThresholdLimit, PeakCurrentDuration);

    /* Voltage Compensation */
    public static final double voltageCompSaturation = 12;

    /* Ramp Rate */
    public static final double openLoopRamp = 0;
    public static final double closedLoopRamp = 0;

    /* Intialization Strategy */
    public static final SensorInitializationStrategy sensorStrat = SensorInitializationStrategy.BootToZero;

    /* getConfig */
    private static final LauncherConstants instance = new LauncherConstants();
    public static LauncherConstants getInstance(){
        return instance;
    }

    private LauncherConstants(){
        config.slot0.kP = kP;
        config.slot0.kI = kI;
        config.slot0.kD = kD;
        config.slot0.kF = kF;
        config.slot0.integralZone = kIz;
        config.motionCruiseVelocity = motionCruiseVelocity;
        config.motionAcceleration = motionAcceleration;
        
        config.supplyCurrLimit = supplyLimit;
        config.openloopRamp = openLoopRamp;
        config.closedloopRamp = closedLoopRamp;
        config.voltageCompSaturation = voltageCompSaturation;
        config.initializationStrategy = sensorStrat;
    }

    public static void setupFalconLeader(TalonFX motor){
        TalonFXSetup.configAllSetup(motor, config);
        motor.setInverted(kInverted);
        motor.setNeutralMode(kNeutralMode);
        motor.enableVoltageCompensation(true);
    }

    public static void setupFalconFollower(TalonFX motorFollower, TalonFX motorLeader){
        TalonFXSetup.configFollowerSetup(motorFollower, config);
        motorFollower.setInverted(kFollowerInverted);
        motorFollower.setNeutralMode(kNeutralMode);
        motorFollower.enableVoltageCompensation(true);
    }
}
