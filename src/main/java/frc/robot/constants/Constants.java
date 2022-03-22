//Created by Spectrum3847
package frc.robot.constants;

import edu.wpi.first.wpilibj.DriverStation;

public final class Constants {
    public static RobotType ROBOT = RobotType.ROBOT_2022C;
    private static final boolean FAKE_FMS = true;
    public static final boolean TUNING_MODE = false;
    public static final boolean ENABLE_DASHBOARD = true;
    public static final String MAC_ADDRESS = "00:80:2F:24:6D:7C";
    
    public static final double loopPeriodSecs = 0.02;

    public static enum RobotType {
    ROBOT_2022C, ROBOT_2022P, ROBOT_SIMBOT, ROBOT_ROMI
    }

    //Check if we are FMSattached or Faking it
    public static boolean isFMSEnabled (){
      return ((DriverStation.isFMSAttached() || FAKE_FMS));
    }

    public static int CANconfigTimeOut = 0;
    public static final int minBatteryVoltage = 12;
    public static String Canivorename = "3847";

    public static final class CanIDs{

        public static final int pigeonID = 0;

        public static final int driveMotor0 = 1;
        public static final int angleMotor0 = 2;
        public static final int driveMotor1 = 11;
        public static final int angleMotor1 = 12;
        public static final int driveMotor2 = 21;
        public static final int angleMotor2 = 22;
        public static final int driveMotor3 = 31;
        public static final int angleMotor3 = 32;

        public static final int kIntakeMotor = 40;
        public static final int kIndexerMotor = 41;
        public static final int kFeederMotor = 42;
        
        public static final int kLauncherMotorLeft = 50;
        public static final int kFollowerMotorRight = 51;

        public static final int kClimberMotorLeft = 60;
        public static final int kClimberMotorRight = 61;
        public static final int kClimberMotorLeft2 = 62;
        public static final int kClimberMotorRight2 = 59;
    }

    public static final class SolenoidPorts{
        public static final int kIntakeDown = 4;
        public static final int kclimberUp = 3;
    }

    public static final class PWMPorts{   
        public static final int kHoodServoLeft = 8;
        public static final int kHoodServoRight = 9;
    }
}
