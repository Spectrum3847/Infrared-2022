//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;

import frc.lib.drivers.PicoColorSensor;
import frc.lib.drivers.PicoColorSensor.RawColor;
import frc.robot.AutonSetup;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.Constants.CanIDs;

public class Indexer extends frc.lib.subsystems.RollerSubsystem {
  public ColorSensorV3 revColorSensor;
  public PicoColorSensor picoCS;
  private RawColor sensor1 = new RawColor(0, 0, 0, 0);
  private RawColor sensor2 = new RawColor(0, 0, 0, 0);
  private double prox1 = 0;
  private double prox2 = 0;

  private double redLED1 = 5786;//30108;//24000;
  private double greenLED1 = 5152;//55341;//47000;
  private double blueLED1 = 11993;//25171;//22000;

  private double redLED2 = 1358;//42702;
  private double greenLED2 = 1155;//83718;
  private double blueLED2 = 2502;//38932;

  private double BAL_RED1 =  greenLED1/redLED1;
  private double BAL_GREEN1 = 1;
  private double BAL_BLUE1 = greenLED1/blueLED1;

  private double BAL_RED2 = greenLED2/redLED2;
  private double BAL_GREEN2 = 1;
  private double BAL_BLUE2 = greenLED2/blueLED2;

  private double currentSpeed = IndexerConstants.intakeSpeed;

  /** Creates a new Indexer. */
  public Indexer() {  
    setName(IndexerConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kIndexerMotor, Constants.Canivorename);
    IndexerConstants.setupRollerFalconLeader(motorLeader);

    //sensor setup
    //picoCS = new PicoColorSensor();
    //revColorSensor = new ColorSensorV3(I2C.Port.kMXP);
    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  public void restartColorSensor(){
    try {
      picoCS.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    picoCS = new PicoColorSensor();
  }

  public void indexerColorSort(){
    boolean redAlliance = AutonSetup.getIsRed();
    //Reject the ball if it's not our color, default to intaking the ball
    if (isDisconnected()){
      currentSpeed = IndexerConstants.intakeSpeed;
    } else if ((isBlue() && redAlliance) || (isRed() && !redAlliance)){
      currentSpeed = -.33;
    } else if ((isBlue() && !redAlliance) || (isRed() && redAlliance)) {
      currentSpeed = IndexerConstants.intakeSpeed;
    }
    setManualOutput(currentSpeed);
  }

  public boolean isRed(){
    //check if Red is greater than the low threshold and blue is less than threshold or 
    //red is greater than the large threshold.
    return !isNothing() && 
    ((double) getRed1() / (double) getBlue1() > 1.35 ||
    (double) getRed2() / (double) getBlue2() > 1.35);
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
    return getGreen1() > 80 && getGreen2() > 80;
    //return getProx1() < 150 && getProx2() < 113;
    //return getGreen1() > 220 && getRed1() < 200 && getBlue1() - getGreen1() < 10;
  }

  public boolean isDisconnected(){
    return getGreen1() == 0 || getGreen2() == 0;
  }

  public double getRed1(){
    return sensor1.red * BAL_RED1;
  }

  public double getBlue1(){
    return sensor1.blue * BAL_BLUE1;
  }

  public double getGreen1(){
    return sensor1.green * BAL_GREEN1;
  }

  public double getProx1(){
    return prox1;
  }

  public double getRed2(){
    return sensor2.red * BAL_RED2;
  }

  public double getBlue2(){
    return sensor2.blue * BAL_BLUE2;
  }

  public double getGreen2(){
    return sensor2.green * BAL_GREEN2;
  }
  public double getProx2(){
    return prox2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //sensor1 = revColorSensor.getRawColor();
    /*picoCS.getRawColor0(sensor1);
    picoCS.getRawColor1(sensor2);
    prox1 = picoCS.getProximity0();
    prox2 = picoCS.getProximity1();*/
  }

  public void dashboard() {
    //SmartDashboard.putNumber("Color 1 Red", sensor1.red);
    /*SmartDashboard.putNumber("Color 1 Blue", sensor1.blue);
    SmartDashboard.putNumber("Color 2 Red", sensor2.red);
    SmartDashboard.putNumber("Color 2 Blue", sensor2.blue);*/
  }

}

