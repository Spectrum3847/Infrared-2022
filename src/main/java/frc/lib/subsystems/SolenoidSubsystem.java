package frc.lib.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.SpectrumSolenoid;

public class SolenoidSubsystem extends SubsystemBase{
    public final SpectrumSolenoid solenoid;

    public SolenoidSubsystem(String name, int port){
      setName(name);
      solenoid = new SpectrumSolenoid(PneumaticsModuleType.REVPH, port);

      //Default to down
      this.setDefaultCommand(new RunCommand(() -> off(), this));
    }

    public void on(){
      solenoid.set(true);
    }
  
    public void off(){
      solenoid.set(false);
    }
  }