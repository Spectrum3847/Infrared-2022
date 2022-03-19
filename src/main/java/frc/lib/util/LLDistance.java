package frc.lib.util;

import edu.wpi.first.math.util.Units;

public class LLDistance{
    /*
    Sets up Limelight Distance Calculations.
    all Concstructors should be in inches, all calculations are done in Meters.
    */
    private double targetHeight, limelightHeight, limelightAngle, distanceToTarget;

    public LLDistance(double targetHeight, double limelightHeight, double limelightAngle){
        this.targetHeight = targetHeight;
        this.limelightHeight = limelightHeight;
        this.limelightAngle = limelightAngle;
    }
    public double distanceInches(double targetAngle){
        distanceToTarget = ((targetHeight - limelightHeight) / Math.tan(limelightAngle + Math.toRadians(targetAngle)));
        return Units.metersToInches(distanceToTarget);
    }
    public double distanceFeet(double targetAngle){
        distanceToTarget = ((targetHeight - limelightHeight) / Math.tan(limelightAngle + Math.toRadians(targetAngle)));
        return Units.metersToFeet(distanceToTarget);
    }
    public double distanceMeters(double targetAngle){
        distanceToTarget = ((targetHeight - limelightHeight) / Math.tan(limelightAngle + Math.toRadians(targetAngle)));
        return distanceToTarget;
    }
}