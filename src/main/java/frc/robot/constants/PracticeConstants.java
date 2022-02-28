package frc.robot.constants;

import frc.robot.constants.Constants.RobotType;

public class PracticeConstants {
    public static final String MAC_ADDRESS = "18:66:DA:19:D4:41";
    
    public static void practiceBotConstantsOverride(){
        Constants.ROBOT = RobotType.ROBOT_2022P;
        SwerveConstants.Mod0.angleOffset = SwerveConstants.Mod0.angleOffsetP;
        SwerveConstants.Mod1.angleOffset = SwerveConstants.Mod1.angleOffsetP;
        SwerveConstants.Mod2.angleOffset = SwerveConstants.Mod2.angleOffsetP;
        SwerveConstants.Mod3.angleOffset = SwerveConstants.Mod3.angleOffsetP;
    }
}
