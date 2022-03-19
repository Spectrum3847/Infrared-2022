//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lib.drivers.PhotonColorSensors;
import frc.lib.drivers.PhotonColorSensors.RawColor;
import frc.robot.constants.Constants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.Constants.CanIDs;

public class Indexer extends frc.lib.subsystems.RollerSubsystem {
  public PhotonColorSensors colorSensor;
  private RawColor sensor1;
  private RawColor sensor2;
  private double redThreshold1 = 200;
  private double blueThreshold1 = 170;
  private double redThreshold2 = 200;
  private double blueThreshold2 = 170;

  
  /** Creates a new Indexer. */
  public Indexer() {  
    setName(IndexerConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kIndexerMotor, Constants.Canivorename);
    IndexerConstants.setupRollerFalconLeader(motorLeader);

    //sensor setup
    colorSensor = new PhotonColorSensors();
    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  public void indexerColorSort(){
    if (isRed()){
      setManualOutput(1);
    } else if (isBlue()){
        setManualOutput(-1);
    } else {
        stop();
    }
  }

  public boolean isRed(){
    return sensor1.red > redThreshold1 || sensor2.red > redThreshold2;
  }

  public boolean isBlue(){
    return sensor1.blue > blueThreshold1 || sensor2.blue > blueThreshold2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    colorSensor.getRawColor0(sensor1);
    colorSensor.getRawColor1(sensor2);
  }

  public void dashboard() {
    SmartDashboard.putNumber("Color 1 Red", sensor1.red);
    SmartDashboard.putNumber("Color 1 Blue", sensor1.blue);
    SmartDashboard.putNumber("Color 2 Red", sensor2.red);
    SmartDashboard.putNumber("Color 2 Blue", sensor2.blue);
  }

}
