//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lib.subsystems.RollerSubsystem;
import frc.robot.constants.Constants;
import frc.robot.constants.FeederConstants;
import frc.robot.constants.Constants.CanIDs;

public class Feeder extends RollerSubsystem {
  public DigitalInput topSensor;
  public DigitalInput lowerSensor;
  public int numberOfBalls = 0;

  /** Creates a new Feeder. */
  public Feeder() {  
    setName(FeederConstants.name);

    topSensor = new DigitalInput(FeederConstants.topSensor);
    lowerSensor = new DigitalInput(FeederConstants.bottomSensor);

    motorLeader = new WPI_TalonFX(CanIDs.kFeederMotor, Constants.Canivorename);
    FeederConstants.setupRollerFalconLeader(motorLeader);

    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  /**
   * Feeder Command Flow Chart
   * if Indexer is running and lower sensor broken, run until lower sensor not broken or Top Sensor broken
   */
  public void intakeBalls(){
    if (lowerHasBall() && !topHasBall()){
      setManualOutput(FeederConstants.intakeSpeed);
    } else if (topHasBall()){
      setManualOutput(-0.2);
    } else {
      stop();
    }
  }

  public void periodic() {
  }

  //returns whether lower sensor is broken (true = broken)
  public boolean lowerHasBall(){
    return !lowerSensor.get();
  }

  //returns whether top sensor is broken (true = broken)
  public Boolean topHasBall(){
    return !topSensor.get();
  }

  public void setRPM(double wheelRPM) {
    // Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) *
    // 1 gear ratio to shooter
    // Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 1
    // to 1
    double motorVelocity = ((wheelRPM / 600) * 2048) * FeederConstants.gearRatio;
      setVelocity(motorVelocity);
  }

  public double getRPM(){
    return super.getRPM() / FeederConstants.gearRatio;
  }

  public void dashboard() {

  }
}
