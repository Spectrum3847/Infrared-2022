//Created by Spectrum3847
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.LinearServo;
import frc.lib.subsystems.RollerSubsystem;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.constants.LauncherConstants;
import frc.robot.constants.Constants.CanIDs;
import frc.robot.constants.Constants.PWMPorts;

public class Launcher extends RollerSubsystem {
  public final WPI_TalonFX motorFollower;
  public final ServoHood hood;
  private boolean hasRun = false;

  /**
   * Creates a new Intake.
   */
  public Launcher() {
    setName(LauncherConstants.name);

    motorLeader = new WPI_TalonFX(CanIDs.kLauncherMotorLeft, Constants.Canivorename);
    LauncherConstants.setupFalconLeader(motorLeader);

    motorFollower = new WPI_TalonFX(CanIDs.kFollowerMotorRight, Constants.Canivorename);
    LauncherConstants.setupFalconFollower(motorFollower, motorLeader);

    hood = new ServoHood();

    this.setDefaultCommand(new RunCommand(() -> reset(), this));
  }

  public void follow(){
    motorFollower.set(TalonFXControlMode.Follower, motorLeader.getDeviceID());
  }

  public void stop(){
    super.stop();
    follow();
  }
  
  public void reset(){
    if (Math.abs(getWheelRPM()) > 500){
      hasRun = true;
      setRPM(LauncherConstants.defaultSpeed);
      follow();
      return;
    }
    stop();
  }
  
  public void setRPM(double wheelRPM) {
    // Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) *
    // 1 gear ratio to shooter
    // Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 1
    // to 1
    double motorVelocity = ((wheelRPM / 600) * 2048);
      setVelocity(motorVelocity);
      follow();
  }

  public void LLsetRPM(){
    setRPM(Robot.visionLL.getVisionLauncherRPM());
  }

  public double getWheelRPM() {
    return (motorLeader.getSelectedSensorVelocity()) / 2048 * 600;
  }


  public void dashboard() {
  }

  public class ServoHood extends SubsystemBase {
    public final LinearServo leftHood;
    public final LinearServo rightHood;
    public final int hoodMaxSpeed = 20;
    public final int maxlength = 50;
    public final int minlength = 0;
    public final double minAngle = 50;
    public final double maxAngle = 80;

    public ServoHood() {
      leftHood = new LinearServo(PWMPorts.kHoodServoLeft, maxlength, hoodMaxSpeed);
      rightHood = new LinearServo(PWMPorts.kHoodServoRight, maxlength, hoodMaxSpeed);
      this.setHoodAngle(LauncherConstants.tarmacShotAngle);
      this.setDefaultCommand(new RunCommand(() -> this.setHoodAngle(LauncherConstants.tarmacShotAngle), this));
    }

    public void periodic() {
      // This method will be called once per scheduler run
      leftHood.updateCurPos();
      rightHood.updateCurPos();
    }

    public void setHoodLength(double position) {
      leftHood.setPosition(position);
      rightHood.setPosition(position);
    }

    public void setHoodAngle(double angle) {
      double length = angleToLength(angle);
      setHoodLength(length);
    }

    public double getCurrentAngle() {
      return lengthToAngle(leftHood.getPosition());
    }

    public double getSetAngle() {
      return lengthToAngle(leftHood.get() * maxlength);
    }

    public void hoodFullFwd() {
      setHoodLength(maxlength);
    }

    public void hoodFullRear() {
      setHoodLength(minlength);
    }

    public double angleToLength(double angle) {
      angle = (angle - minAngle) / (maxAngle - minAngle);
      return (1 - angle) * maxlength;
    }

    public double lengthToAngle(double length) {
      length = (length - minlength) / (maxlength - minlength);
      return minAngle + ((1 - length) * (maxAngle - minAngle));
    }
  }
}
