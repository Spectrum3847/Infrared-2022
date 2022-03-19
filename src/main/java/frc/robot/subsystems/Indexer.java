//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import com.revrobotics.ColorSensorV3.RawColor;
import frc.robot.constants.Constants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.Constants.CanIDs;

public class Indexer extends frc.lib.subsystems.RollerSubsystem {
  public ColorSensorV3 revColorSensor;
  //public PhotonColorSensors colorSensor;
  private RawColor sensor1 = new RawColor(0, 0, 0, 0);
  private RawColor sensor2 = new RawColor(0, 0, 0, 0);
  private double redThreshold1 = 175;
  private double blueThreshold1 = 220;
  private double redThreshold2 = 600;
  private double blueThreshold2 = 500;

  private int redLED = 24000;
  private int greenLED = 47000;
  private int blueLED = 22000;

  private int BAL_RED1 = greenLED/redLED;
  private int BAL_GREEN1 = 1;
  private int BAL_BLUE1 = greenLED/blueLED;

  /** Creates a new Indexer. */
  public Indexer() {  
    setName(IndexerConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kIndexerMotor, Constants.Canivorename);
    IndexerConstants.setupRollerFalconLeader(motorLeader);

    //sensor setup
    //colorSensor = new PhotonColorSensors();
    //colorSensor.setDebugPrints(true);
    revColorSensor = new ColorSensorV3(I2C.Port.kMXP);
    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  public void indexerColorSort(){
    if (isRed()){
      setManualOutput(IndexerConstants.intakeSpeed);
    } else if (isBlue()){
        setManualOutput(-.5);
    } else {
        stop();
    }
  }

  public boolean isRed(){
    //check if Red is greater than the low threshold and blue is less than threshold or 
    //red is greater than the large threshold.
    return !isNothing() && (double) getRed() / (double) getBlue() > 0.72;
    //return getRed() > redThreshold1 && getRed() > getBlue() ||
    //  (getGreen() < 175 && (getRed() - getBlue()) > -30);
    //return sensor1.red > redThreshold1 || sensor2.red > redThreshold2;
  }

  public boolean isBlue(){
    return !isNothing() && !isRed();
    //return getBlue() > blueThreshold1 && getBlue() > getRed() && getBlue() > getGreen() && getGreen() < 3000||
     // (getGreen() < 175 && (getBlue() - getRed()) > 30);
    //return sensor1.blue > blueThreshold1 || sensor2.blue > blueThreshold2;
  }

  public boolean isNothing(){
    return getGreen() > 280 && getRed() < 300 && getBlue() - getGreen() < 10;
  }

  public int getRed(){
    return sensor1.red * BAL_RED1;
  }

  public int getBlue(){
    return sensor1.blue * BAL_BLUE1;
  }

  public int getGreen(){
    return sensor1.green * BAL_GREEN1;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    sensor1 = revColorSensor.getRawColor();
    //colorSensor.getRawColor0(sensor1);
    //colorSensor.getRawColor1(sensor2);
  }

  public void dashboard() {
    //SmartDashboard.putNumber("Color 1 Red", sensor1.red);
    SmartDashboard.putNumber("Color 1 Blue", sensor1.blue);
    SmartDashboard.putNumber("Color 2 Red", sensor2.red);
    SmartDashboard.putNumber("Color 2 Blue", sensor2.blue);
  }

}
