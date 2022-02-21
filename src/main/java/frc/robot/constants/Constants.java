//Created by Spectrum3847
package frc.robot.constants;

import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.util.Alert;
import frc.lib.util.Alert.AlertType;
import frc.robot.Robot;
import frc.robot.Robot.RobotState;

public final class Constants {
    private static final RobotType robot = RobotType.ROBOT_2022C;
    private static final boolean fakeFMS = false;
    public static final boolean tuningMode = true;
    public static final boolean ENABLE_DASHBOARD = true;
    
    public static final double loopPeriodSecs = 0.02;

    private static final Alert invalidRobotAlert =
    new Alert("Invalid robot selected, using competition robot as default.",
        AlertType.ERROR);
        
    public static final Map<RobotType, String> logFolders =
    Map.of(RobotType.ROBOT_2022C, "/media/sda2/", RobotType.ROBOT_2022P,
        "/media/sda1/");

    public static enum RobotType {
    ROBOT_2022C, ROBOT_2022P, ROBOT_SIMBOT, ROBOT_ROMI
    }

    public static enum Mode {
    REAL, REPLAY, SIM
    }

    //Check if we are FMSattached (or faking it) and we are not disabled
    public static boolean isFMSEnabled (){
      return ((DriverStation.isFMSAttached() || fakeFMS) && !(Robot.s_robot_state == RobotState.DISABLED));
    }

    public static RobotType getRobot() {
        if (RobotBase.isReal()) {
          if (robot == RobotType.ROBOT_SIMBOT || robot == RobotType.ROBOT_ROMI) { // Invalid robot
                                                                                  // selected
            invalidRobotAlert.set(true);
            return RobotType.ROBOT_2022C;
          } else {
            return robot;
          }
        } else {
          return robot;
        }
      }
    
      public static Mode getMode() {
        switch (getRobot()) {
          case ROBOT_2022C:
          case ROBOT_2022P:
            return RobotBase.isReal() ? Mode.REAL : Mode.REPLAY;
    
          case ROBOT_SIMBOT:
          case ROBOT_ROMI:
            return Mode.SIM;
    
          default:
            return Mode.REAL;
        }
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

        public static final int kClimberMotor1 = 60;
        public static final int kClimberMotor2 = 61;
    }

    public static final class SolenoidPorts{
        public static final int kIntakeDown = 4;
        public static final int kclimberUp = 0;
    }

    public static final class PWMPorts{   
        public static final int kHoodServoLeft = 0;
        public static final int kHoodServoRight = 1;
    }
}
