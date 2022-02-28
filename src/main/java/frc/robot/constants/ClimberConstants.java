//Created by Spectrum3847

package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import frc.lib.util.TalonFXSetup;

public final class ClimberConstants{
    public static final String name = "Climber";

    public static final int fullExtend = 100000;
    public static final int nextRungExtend = 6000;
    public static final int fullRetract = -2000;

    //Physical Constants
    public static final double pulleyDiameterInches = 2;
    public static final double pulleyDiameterMeters = pulleyDiameterInches * 0.0254;

    public static final double gearRatio = 72/10;

    public static final double pulleyCircumferenceMeters = pulleyDiameterMeters * Math.PI;
    public static final double pulleyCircumferenceInches = pulleyDiameterInches * Math.PI;

    /* Motor Characterization Values */
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;

    //Falcon Setup
    public static TalonFXConfiguration config = new TalonFXConfiguration();

    /* Inverted */
    public static final boolean kInverted = true;
    public static final boolean kFollowerInverted = !kInverted;

    /* Neutral Modes */
    public static final NeutralMode kNeutralMode = NeutralMode.Brake;

    /* Control Loop Constants */
    public static final double kP = 0.0;
    public static final double kI = 0;
    public static final double kD = 0;
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
    private static final ClimberConstants instance = new ClimberConstants();
    public static ClimberConstants getInstance(){
        return instance;
    }

    private ClimberConstants(){
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
        motorFollower.follow(motorLeader);
    }
}
