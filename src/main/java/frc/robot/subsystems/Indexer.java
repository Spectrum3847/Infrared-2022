//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.constants.Constants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.Constants.CanIDs;

public class Indexer extends frc.lib.subsystems.RollerSubsystem {

  /** Creates a new Indexer. */
  public Indexer() {  
    setName(IndexerConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kIndexerMotor, Constants.Canivorename);
    IndexerConstants.setupRollerFalconLeader(motorLeader);

    this.setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void dashboard() {
  }

}
