package frc.robot.subsystems;

import frc.lib.subsystems.PositionSubsystem;
import frc.lib.subsystems.SolenoidSubsystem;
import frc.robot.Gamepads;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.constants.Constants.SolenoidPorts;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.RunCommand;

public class Climber extends PositionSubsystem{
    public final WPI_TalonFX motorFollower;
    public final SolenoidSubsystem pneumatic;

    public Climber(){
        setName(ClimberConstants.name);
        motorLeader = new WPI_TalonFX(CanIDs.kClimberMotorLeft, Constants.Canivorename);
        ClimberConstants.setupFalconLeader(motorLeader);

        motorFollower = new WPI_TalonFX(CanIDs.kClimberMotorRight, Constants.Canivorename);
        ClimberConstants.setupFalconFollower(motorFollower, motorLeader);
        
        resetEncoder();

        pneumatic = new SolenoidSubsystem("Climber Solenoid", SolenoidPorts.kclimberUp);
        
        setDefaultCommand(new RunCommand(() -> setManualOutput(Gamepads.getClimberJoystick()), this));
    }

    public void follow(){
        motorFollower.follow(motorLeader);
    }
    public void tiltUp(){
        pneumatic.on();
    }

    public void tiltDown(){
      pneumatic.off();
  }
}
