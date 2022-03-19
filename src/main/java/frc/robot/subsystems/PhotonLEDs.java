package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivers.Photon;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class PhotonLEDs extends SubsystemBase {
	public static Photon photon;
  ArrayList<Animation> ar = new ArrayList<Animation>();
  Animation top;

  public PhotonLEDs(){
		photon = new Photon(192,255);
		photon.SetNumberOfLEDs(1, 55); //55 on comp bot
		photon.SetNumberOfLEDs(2, 55);
    defaultAnimations();
    
    System.out.println("Starting Photon Controller Thread");
    PhotonControllerThread.start();
  }

  public void defaultAnimations(){
		photon.setAnimation(1,Photon.Animation.CYLON_MIDDLE_DUAL);
    photon.setAnimation(2,Photon.Animation.CYLON_MIDDLE_DUAL);
    //Add a default pattern to the arrayList
    top = new Animation("Default", Photon.Animation.CYLON_MIDDLE_DUAL, Photon.Color.PURPLE,Photon.Color.WHITE,0,-100);
    ar.add(top); 
  }

  public void addAnimation(String name, Photon.Animation photonanimation, Photon.Color color1, Photon.Color color2, int priority, double timeout){
    Animation a = new Animation(name, photonanimation, color1, color2, priority, timeout);
    //Check if this animation is already added (same name), if it is delete the old one and add the new one
    if (ar.indexOf(a) >= 0){
      ar.remove(ar.indexOf(a));
    }
    ar.add(a);
  }

  public void photonController(){
    //Increment through all the Animations
    for(int j = 0; j < ar.size(); j++){
      Animation obj = ar.get(j);

      //Delete itemss with a timeout less than 0 and greater than -100
      //Start things at -100 if you want them to never timeout
      if(obj.getTimeout() < 0 && obj.getTimeout() > -100){
        //found, delete.
          ar.remove(j);
      }
      obj.decrementTimeOut();
    }
        //Sort the list by priority
    Collections.sort(ar, new SortbyPriority()); 

    //If we have a new top
    if (top.getName() != ar.get(0).getName()){
      top = ar.get(0);
      photon.setAnimation(1, top.getAnimation(),top.getColor1(), top.getColor2());
      photon.setAnimation(2, top.getAnimation(),top.getColor1(), top.getColor2());
    } 

    if (top.getPriority() > 1){
      top.decrementPriority();
    }
  }

  public static void print(String msg){
    System.out.println(msg);
  }

  public void dashboard() {
  }

  //Modify to run a system check for the robot
  public boolean checkSystem() {
    return true;
  }


    /**
     * This thread runs a periodic task in the background to listen for vision camera packets.
     */
    Thread PhotonControllerThread = new Thread(new Runnable(){
    	public void run(){
    		while(true){
          photonController();		
          Timer.delay(0.2);//Loop runs at 20hz
    		}
    	}
    });

    class Animation {
      String name;
      Photon.Color color1;
      Photon.Color color2;
      Photon.Animation photonAnimation;
      int priority;
      double timeout;
      public Animation(String name, Photon.Animation photonanimation, Photon.Color color1, Photon.Color color2, int priority, double timeout){
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
        this.photonAnimation = photonanimation;
        this.priority = priority;
        this.timeout = timeout;
      }
      
      public boolean equals(Object object)
      {
          boolean isEqual= false;

          if (object != null && object instanceof Animation)
          {
              isEqual = (this.name == ((Animation) object).getName());
          }

          return isEqual;
      }

      public String getName(){
        return name;
      }

      public int getPriority(){
        return this.priority;
      }

      public void setPriority(int p){
        priority = p;
      }

      public double getTimeout(){
        return this.timeout;
      }

      public void setTimeout(double t){
        timeout = t;
      }

      public Photon.Color getColor1(){
        return color1;
      }

      public Photon.Color getColor2(){
        return color2;
      }

      public Photon.Animation getAnimation(){
        return photonAnimation;
      }

      public void decrementTimeOut(){
        timeout -= 1;
      }

      public void decrementPriority(){
        priority -= 1;
      }
    }

    class SortbyPriority implements Comparator<Animation> 
    { 
      // Used for sorting in ascending order of 
      // roll number 
      public int compare(Animation a, Animation b) 
      { 
          return b.priority - a.priority; 
      } 
    } 
  
  
  }
