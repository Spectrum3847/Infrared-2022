package frc.robot.subsystems;

import frc.lib.subsystems.PositionSubsystem;
import frc.lib.subsystems.SolenoidSubsystem;
import frc.robot.Gamepads;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.constants.Constants.SolenoidPorts;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Climber extends PositionSubsystem {
    public final WPI_TalonFX motorFollower;
    public final WPI_TalonFX motorFollower2;
    public final WPI_TalonFX motorFollower3;
    public final SolenoidSubsystem pneumatic;

    public Climber() {
        setName(ClimberConstants.name);
        motorLeader = new WPI_TalonFX(CanIDs.kClimberMotorLeft, Constants.Canivorename);
        ClimberConstants.setupFalconLeader(motorLeader);
        motorFollower = new WPI_TalonFX(CanIDs.kClimberMotorRight, Constants.Canivorename);
        ClimberConstants.setupFalconFollower(motorFollower, motorLeader);

        motorFollower2 = new WPI_TalonFX(CanIDs.kClimberMotorLeft2, Constants.Canivorename);
        ClimberConstants.setupFalconFollower(motorFollower2, motorLeader);
        motorFollower2.setInverted(ClimberConstants.kInverted);
        
        motorFollower3 = new WPI_TalonFX(CanIDs.kClimberMotorRight2, Constants.Canivorename);
        ClimberConstants.setupFalconFollower(motorFollower3, motorLeader);
        follow();


        resetEncoder();
        motorLeader.configForwardSoftLimitThreshold(ClimberConstants.fullExtend);
        motorLeader.configForwardSoftLimitEnable(false);

        motorLeader.configReverseSoftLimitThreshold(ClimberConstants.fullRetract);
        motorLeader.configReverseSoftLimitEnable(false);

        motorLeader.configNominalOutputReverse(0.05);
        

        pneumatic = new SolenoidSubsystem("Climber Solenoid", SolenoidPorts.kclimberUp);

        setDefaultCommand(defaultCommand());
    }

    public Command defaultCommand(){
        return new RunCommand(() -> setManualOutput(Gamepads.getClimberJoystick()), this);
    }

    public void setPIDslot(int slot){
        motorLeader.selectProfileSlot(slot, 0);
    }

    public void follow() {
        motorFollower.setNeutralMode(NeutralMode.Brake);
        motorFollower.follow(motorLeader);
        motorFollower2.setNeutralMode(NeutralMode.Brake);
        motorFollower2.follow(motorLeader);
        motorFollower3.setNeutralMode(NeutralMode.Brake);
        motorFollower3.follow(motorLeader);
    }

    public void tiltUp() {
        motorLeader.setNeutralMode(NeutralMode.Brake);
        motorFollower.setNeutralMode(NeutralMode.Brake);
        pneumatic.on();
    }

    public void tiltDown() {
        motorLeader.setNeutralMode(NeutralMode.Brake);
        motorFollower.setNeutralMode(NeutralMode.Brake);
        pneumatic.off();
    }
}
