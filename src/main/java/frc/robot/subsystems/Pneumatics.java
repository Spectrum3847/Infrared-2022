// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;

public class Pneumatics extends SubsystemBase {
  private Compressor compressor;
  /** Creates a new Pneumatics. */
  public Pneumatics() {
    switch (Constants.getRobot()){
      case ROBOT_2022C:
        compressor.enableAnalog(90, 120);
      break;
      case ROBOT_2022P:
        compressor.enableDigital();
      break;
      default:
      break;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public boolean isCompressorEnabled(){
    return compressor.enabled();
  }
}
