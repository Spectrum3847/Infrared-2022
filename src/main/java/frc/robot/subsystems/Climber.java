package frc.robot.subsystems;

import frc.robot.Gamepads;
import frc.robot.constants.Constants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.constants.Constants.SolenoidPorts;
import frc.robot.telemetry.Log;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.SpectrumSolenoid;
import frc.lib.util.TalonFXSetup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Climber extends SubsystemBase{
    public static final String name = Log._climber;
    
    public final WPI_TalonFX motorLead;
    public final WPI_TalonFX motorFollow;
    public final Solenoid solenoid;

    public Climber(){
        setName(name);
        motorLead = new WPI_TalonFX(CanIDs.kClimberMotor1, Constants.Canivorename);
        motorFollow = new WPI_TalonFX(CanIDs.kClimberMotor2, Constants.Canivorename);
        TalonFXSetup.defaultSetup(motorLead, false, 40);
        TalonFXSetup.defaultSetup(motorFollow, true, 40);
        
        motorFollow.follow(motorLead);

        solenoid = new Solenoid();
        
        setDefaultCommand(new RunCommand(() -> setManualOutput(Gamepads.getClimberJoystick()), this));
    }

    public void stopMotor(){
        motorLead.stopMotor();
    }

    public void setManualOutput(double speed){
        motorLead.set(ControlMode.PercentOutput,speed);
    }

    public double getCurrent(){
        return motorLead.getSupplyCurrent();
    }

    public double getVelocity(){
        return motorLead.getSelectedSensorVelocity();
    }

    public class Solenoid extends SubsystemBase{
        public final SpectrumSolenoid solUp;
    
        public Solenoid(){
          setName(name + " solenoid");
          solUp = new SpectrumSolenoid(PneumaticsModuleType.REVPH, SolenoidPorts.kclimberUp);
    
          this.setDefaultCommand(new RunCommand(() -> down(), this));
        }
    
        public void up(){
          solUp.set(true);
        }
      
        public void down(){
          solUp.set(false);
        }
      }
}
