//Created by Spectrum3847
package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lib.subsystems.RollerSubsystem;
import frc.robot.constants.Constants;
import frc.robot.constants.FeederConstants;
import frc.robot.constants.Constants.CanIDs;

public class Feeder extends RollerSubsystem {
  public DigitalInput topSensor;
  public DigitalInput bottomSensor;

  /** Creates a new Feeder. */
  public Feeder() {  
    setName(FeederConstants.name);

    topSensor = new DigitalInput(FeederConstants.topSensor);
    bottomSensor = new DigitalInput(FeederConstants.bottomSensor);

    motorLeader = new WPI_TalonFX(CanIDs.kFeederMotor, Constants.Canivorename);
    FeederConstants.setupRollerFalconLeader(motorLeader);

    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  @Override
  public void periodic() {

    System.out.println(bottomHasBall());
    System.out.println(topHasBall());
    // This method will be called once per scheduler run
  }


  //returns whether indexer sensor is broken (true = broken)
  public boolean topHasBall(){
    return !bottomSensor.get();
  }

  //returns whether feeder sensor is broken (true = broken)
  public Boolean bottomHasBall(){
    return !topSensor.get();
  }



  public void dashboard() {

  }
}
