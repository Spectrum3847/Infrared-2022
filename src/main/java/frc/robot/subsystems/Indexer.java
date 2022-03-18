//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.constants.Constants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.Constants.CanIDs;

public class Indexer extends frc.lib.subsystems.RollerSubsystem {
  public ColorSensorV3 colorSensor;

  
  /** Creates a new Indexer. */
  public Indexer() {  
    setName(IndexerConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kIndexerMotor, Constants.Canivorename);
    IndexerConstants.setupRollerFalconLeader(motorLeader);

    //sensor setup
    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  public void indexerColorSort(){
    if (isRed()){
      setManualOutput(IndexerConstants.intakeSpeed);
    } else if (isBlue()){
        setManualOutput(-1);
    } else {
        stop();
    }
  }


  public boolean isBall(){
    if (colorSensor.getProximity() > 110 || isRed() || isBlue()){
      return false;
    } else {
      return true;
    }
  }

  public boolean isRed(){
    return colorSensor.getRed() > 225;
  }

  public boolean isBlue(){
    return colorSensor.getBlue() > 170;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void dashboard() {
    SmartDashboard.putNumber("Color Proximity", colorSensor.getProximity());
    SmartDashboard.putNumber("Color Red", colorSensor.getRed());
    SmartDashboard.putNumber("Color Blue", colorSensor.getBlue());
    SmartDashboard.putNumber("Color Green", colorSensor.getGreen());
    SmartDashboard.putNumber("Color IR", colorSensor.getIR());
  }

}
