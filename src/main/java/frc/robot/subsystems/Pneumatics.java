// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Pneumatics extends SubsystemBase {
  private Compressor compressor;
  /** Creates a new Pneumatics. */
  public Pneumatics() {
    compressor = new Compressor(PneumaticsModuleType.REVPH);

    checkIfPractice();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void checkIfPractice(){
    if (Robot.isPractice){
      compressor.enableDigital();
    } else{
      compressor.enableAnalog(90, 115);
    }
  }

  public boolean isCompressorEnabled(){
    return compressor.enabled();
  }

  public double getPressure(){
    return compressor.getPressure();
  }

  public void dashboard(){
    //SmartDashboard.putBoolean("Compressor On?", this.isCompressorEnabled());
    //SmartDashboard.putNumber("Pressure", getPressure());
  }
}
