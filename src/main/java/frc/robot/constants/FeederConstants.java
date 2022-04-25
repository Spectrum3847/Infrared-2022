//Created by Spectrum3847

package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import frc.lib.motorControllers.TalonFXSetup;


public final class FeederConstants {
    public static final String name = "Feeder";

    public final static double feedRPM = 375; //350; = State champs speed
    public final static double feedSpeed = 0.30;
    public final static double intakeSpeed = 0.3;

    //sensors
    public final static int topSensor = 8;
    public final static int bottomSensor = 9;
    
    // Physical Constants
    public static final double diameterInches = 4;
    public static final double diameterMeters = diameterInches * 0.0254;

    public static final double gearRatio = 72/16;

    public static final double wheelCircumferenceMeters = diameterMeters * Math.PI;
    public static final double wheelCircumferenceInches = diameterInches * Math.PI;

    public static final double maxRPM = 6000;

    /* Motor Characterization Values */
    public static final double kS = 0;
    public static final double kV = 0;
    public static final double kA = 0;

    // Falcon Setup
    public static TalonFXConfiguration config = new TalonFXConfiguration();

    /* Inverted */
    public static final boolean kInverted = true;
    public static final boolean kFollowerInverted = true;

    /* Neutral Modes */
    public static final NeutralMode kNeutralMode = NeutralMode.Brake;

    /* Control Loop Constants */
    public static final double kP = 0.05;
    public static final double kI = 0.0005;
    public static final double kD = 2;
    public static final double kF = 0.05;
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
    private static final FeederConstants instance = new FeederConstants();

    public static FeederConstants getInstance() {
        return instance;
    }

    private FeederConstants() {
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

    public static void setupRollerFalconLeader(TalonFX motor) {
        TalonFXSetup.configAllSetup(motor, config);
        motor.setInverted(kInverted);
        motor.setNeutralMode(kNeutralMode);
        motor.enableVoltageCompensation(true);
    }

    public static void setupRollerFalconFollower(TalonFX motorFollower, TalonFX motorLeader) {
        TalonFXSetup.configFollowerSetup(motorFollower, config);
        motorFollower.setInverted(kFollowerInverted);
        motorFollower.setNeutralMode(kNeutralMode);
        motorFollower.enableVoltageCompensation(true);
        motorFollower.follow(motorLeader);
    }
}
