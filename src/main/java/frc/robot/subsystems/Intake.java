//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lib.motorControllers.TalonFXSetup;
import frc.lib.subsystems.SolenoidSubsystem;
import frc.robot.constants.Constants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.constants.Constants.SolenoidPorts;

public class Intake extends frc.lib.subsystems.RollerSubsystem{
  public final SolenoidSubsystem pneumatic;
  

  /** Creates a new Intake. */
  public Intake() {  
    setName(IntakeConstants.name);
    motorLeader = new WPI_TalonFX(CanIDs.kIntakeMotor , Constants.Canivorename);
    TalonFXSetup.configAllSetup(motorLeader, IntakeConstants.config);
    
    pneumatic = new SolenoidSubsystem(IntakeConstants.name + "Solenoid", SolenoidPorts.kIntakeDown);


    setDefaultCommand(new RunCommand(() -> stop(), this));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public void intakeOn(){
    setManualOutput(IntakeConstants.intakeSpeed);
  }

  public void dashboard() {
  }

  public void down(){
    pneumatic.on();
  }

  public void up(){
    pneumatic.off();
  }
}
