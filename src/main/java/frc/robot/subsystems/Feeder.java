//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lib.subsystems.RollerSubsystem;
import frc.lib.util.TalonFXSetup;
import frc.robot.constants.Constants;
import frc.robot.constants.FeederConstants;
import frc.robot.constants.Constants.CanIDs;

public class Feeder extends RollerSubsystem {

  /** Creates a new Feeder. */
  public Feeder() {  
    setName(FeederConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kFeederMotor, Constants.Canivorename);
    TalonFXSetup.configAllSetup(motorLeader, FeederConstants.config);

    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void dashboard() {
    SmartDashboard.putNumber("Feeder/Output", motorLeader.getMotorOutputPercent());
    SmartDashboard.putNumber("Feeder/Current", motorLeader.getSupplyCurrent());
  }
}
