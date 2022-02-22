//Created by Spectrum3847
package frc.robot.telemetry;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.constants.Constants;

//Smart Dashboard Values should mostly be used for quick debugging, we will disable if FMS is connected
public class Dashboard {

    private static final Notifier dashFastThread = new Notifier(new dashboardFastThread());
    private static final Notifier dashSlowThread = new Notifier(new dashboardSlowThread());
	
	static final double FAST_DELAY = 0.04;
    static final double SLOW_DELAY = 0.5;

    //Put values that you want to use as use inputs here and set their default state
    public static void intializeDashboard() {
    	if(Constants.ENABLE_DASHBOARD){
            dashFastThread.startPeriodic(FAST_DELAY);
            dashSlowThread.startPeriodic(SLOW_DELAY);
        }
    }

    //Check each subsystems dashboard values and update them
    private static void updatePutFast() {
        //If we are not FMS enabled run these
        if (!Constants.isFMSEnabled()){    
            Robot.visionLL.dashboard();
            Robot.intake.dashboard();
            Robot.indexer.dashboard();
            Robot.swerve.dashboard();
            Robot.launcher.dashboard();
        }
    }

    //Things that don't need to be sent out each cycle
    static boolean b = true;
    private static void updatePutSlow(){
        Robot.pneumatics.dashboard();
        b = !b;
        SmartDashboard.putBoolean("Disabled Toggle", b);
    }

    private static class dashboardFastThread implements Runnable {    
		@Override
		public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updatePutFast();
		}
	}

    private static class dashboardSlowThread implements Runnable {    
		@Override
		public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			updatePutSlow();
		}
	}
}
