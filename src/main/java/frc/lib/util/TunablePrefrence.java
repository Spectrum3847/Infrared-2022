package frc.lib.util;

import frc.robot.constants.Constants;

//Based on FRC#6328 software
/**
 * Class for a tunable number. Gets value from dashboard in tuning mode, returns default if key is 
 * not in dashboard or last value if not in tuning mode.
 */
public class TunablePrefrence {
  private String key;
  private double value;
  private double lastHasChangedValue = value;

  /**
   * Create a new TunableNumber
   * 
   * @param dashboardKey Key on dashboard
   */
  public TunablePrefrence(String dashboardKey) {
    this(dashboardKey, 0.0);
  }

  /**
   * Create a new TunableNumber
   * 
   * @param dashboardKey Key on dashboard
   */
  public TunablePrefrence(String dashboardKey, double v) {
    value = v;
    key = dashboardKey;
    SpectrumPreferences.getNumber(dashboardKey, value);
  }

  /**
   * Get the default value for the number that has been set
   * 
   * @return The default value
   */
  public double getStoredValue() {
    return value;
  }

  /**
  * Sets the networktalbes value even if it already exists.
  */
  public void setNetworkValue(double v) {
    SpectrumPreferences.setNumber(key, v);
  }

  /**
   * Get the current value, from dashboard if available and in tuning mode
   * 
   * @return The current value
   */
  public double get() {
    if (Constants.TUNING_MODE) {
      value = SpectrumPreferences.getNumber(key, value);
      return value;
    } else {
      return value;
    }
  }

  /**
   * Checks whether the number has changed since our last check
   * 
   * @return True if the number has changed since the last time this method was called, false
   *         otherwise
   */
  public boolean hasChanged() {
    double currentValue = get();
    if (currentValue != lastHasChangedValue) {
      lastHasChangedValue = currentValue;
      return true;
    }
    return false;
  }
}
