//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.Logger;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.telemetry.Log;

public class Feeder extends SubsystemBase {
  public static final String name = Log._feeder;
  public final double feedSpeed = 0.3;
  public final WPI_TalonFX motor;

  /** Creates a new Feeder. */
  public Feeder() {  
    setName(name);
    motor = new WPI_TalonFX(CanIDs.kFeederMotor, Constants.Canivorename);
    motor.configFactoryDefault();
    motor.setInverted(true);
    SupplyCurrentLimitConfiguration supplyCurrentLimit = new SupplyCurrentLimitConfiguration(true, 40, 45, 0.5);
    motor.configSupplyCurrentLimit(supplyCurrentLimit);
    motor.setNeutralMode(NeutralMode.Coast);

    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setMotorOutput(double speed){
    motor.set(ControlMode.PercentOutput,speed);
  }

  public void runFwd(){
    setMotorOutput(feedSpeed);
  }
  public void runRev(){
    setMotorOutput(-1 *feedSpeed);
  }

  public void stop(){
    motor.set(ControlMode.PercentOutput, 0);
  }

  public void dashboard() {
    SmartDashboard.putNumber("Feeder/Output", motor.getMotorOutputPercent());
    SmartDashboard.putNumber("Feeder/Current", motor.getSupplyCurrent());
  }

  public static void printDebug(String msg){
    Logger.println(msg, name, Logger.low1);
  }
  
  public static void printInfo(String msg){
    Logger.println(msg, name, Logger.normal2);
  }
  
  public static void printWarning(String msg) {
    Logger.println(msg, name, Logger.high3);
  }

  public static void printError(String msg) {
    Logger.println(msg, name, Logger.critical4);
  }
}
