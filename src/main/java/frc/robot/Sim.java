//Created by Spectrum3847
package frc.robot;

import frc.lib.sim.PhysicsSim;
import frc.robot.subsystems.Swerve.SwerveModule;

public class Sim {
    public static void intialization (){
        PhysicsSim.getInstance().addTalonFX(Robot.intake.motorLeader, 0.75, 5100.0, false);
        PhysicsSim.getInstance().addTalonFX(Robot.indexer.motorLeader, 0.75, 5100.0, false);
        PhysicsSim.getInstance().addTalonFX(Robot.feeder.motorLeader, 0.75, 5100.0, false);
        PhysicsSim.getInstance().addTalonFX(Robot.launcher.motorLeader, 0.75, 5100.0, false);
        PhysicsSim.getInstance().addTalonFX(Robot.launcher.motorFollower, 0.75, 5100.0, false);
        PhysicsSim.getInstance().addTalonFX(Robot.climber.motorLeader, 0.75, 5100.0, false);
        PhysicsSim.getInstance().addTalonFX(Robot.climber.motorFollower, 0.75, 5100.0, false);
        swerveIntialize();
    }

    private static void swerveIntialize(){
        for(SwerveModule mod : Robot.swerve.mSwerveMods){
            PhysicsSim.getInstance().addTalonFX(mod.mAngleMotor, 0.75, 5100.0, false);
            PhysicsSim.getInstance().addTalonFX(mod.mDriveMotor, 0.75, 5100.0, false);
        }
    }
}
