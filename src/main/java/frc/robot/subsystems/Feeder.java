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

  public void dashboard() {

  }
}
