package org.usfirst.frc.team1512.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically it
 * contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;  // class that handles basic drive operations
    Joystick leftStick;  // set to ID 1 in DriverStation
    Joystick rightStick; // set to ID 2 in DriverStation
    //Jaguar jag1, jag2;
    Solenoid Solenoid;
    Compressor c;


    public Robot() {
    	  //c = new Compressor(0);
    	  //Solenoid = new Solenoid(0);
        myRobot = new RobotDrive(0, 1);
        myRobot.setExpiration(0.1);

        Jaguar jag1 = new Jaguar(0);
        Jaguar jag2 = new Jaguar(1);
        Talon shooter = new Talon(2);
        Talon harvester = new Talon(3);

        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
    }

    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto modes", chooser);
    }

    public void autonomousInIt()
    {
        switch(autoSelected)
        {
          case customAuto:

          myRobot.setSafetyEnabled(false);


          break;

          case defaultAuto:

          myRobot.setSafetyEnabled(false);



          break;
        }
    /**
     * Runs the motors with tank steering and gives user ability to
     * toggle shooter and harvester.
     */
    public void operatorControl() {
        //c.start();
        myRobot.setSafetyEnabled(true);

        while (isOperatorControl() && isEnabled()) {

        	if(leftStick.getRawButton(2) == true)
        	{
        		Solenoid.set(true);
        	}

        	else
        	{
        		Solenoid.set(false);
        	}

        	if (leftStick.getRawButton(1) == true)
          {
            shooter.set(1.0);
          }
          else
          {
            shooter.set(0);
          }

          if(rightStick.getRawButton(1) == true)
          {
            harvester.set(0.5);
          }
          else
          {
            harvester.set(0);
          }

          myRobot.tankDrive(leftStick, rightStick);
          //tank drive program
          //driveTrain(leftStick, rightStick, jag1, jag2);

          Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /*public void driveTrain (Joystick leftStick, Joystick rightstick, Jaguar jag1, Jaguar jag2)
    {
      if(leftstick.getY()>0)
      {
        jag1.set(-0.5 * leftStick.getY());
      }
      else if(leftstick.getY()<0)
      {
        jag1.set(-0.5 * leftStick.getY());
      }
      else
      {
        jag1.set(0);
      }

      if(rightstick.getY()>0)
      {
        jag2.set(-0.5 * rightstick.getY());
      }
      else if(rightstick.getY()<0)
      {
        jag2.set(-0.5 * rightstick.getY());
      }
      else
      {
        jag2.set(0);
      }
    }*/

}
