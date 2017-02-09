package org.usfirst.frc.team1512.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

/**
 * Barebones program for initial testing of the robot on the tennis courts
 * Version 1.0: Includes drive system and shooter system Revision Date: 2/9/17
 */

public class Robot extends SampleRobot{
    RobotDrive myRobot;
    Joystick leftstick, rightstick;
    XboxController xcontrol;
//XBOX
    CANTalon talon1;
		CANTalon driveLeft;
		CANTalon driveRight;


    public Robot() {


        myRobot = new RobotDrive(0, 1);
        talon1 = new CANTalon(1);

        myRobot.setExpiration(0.1);
        leftstick = new Joystick(0);
        rightstick = new Joystick(1);

    }

    public void robotInit() {

    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {

    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {


    	double speedmultiplier=0.85;
    	double test;


    	myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
					double speedmultiplier=0.85;
					boolean reverse = false;
					int reverseInt = 1.0;
					SmartDashboard.putBoolean("Reverse", reverse);

						if (leftstick.getRawButton(2))
							{
									reverseInt = -1;
									reverse = true;
								  speedmultiplier = -1 * 0.85;
							}
						else if (rightstick.getRawButton(2))
							{
									reverseInt = 1.0;
									reverse = false;
								  speedmultiplier = 1 * 0.85;
							}

						//drive
            if (leftstick.getY() > 0.1)
							{
									driveLeft.set(reverseInt * leftstick.getY());
							}
						else
							{
									driveLeft.set(0);
							}

						if(rightstick.getY() > 0.1)
							{
									driveRight.set(reverseInt * rightstick.getY());
							}
						else
							{
									driveRight.set(0);
							}

						//shooter
						if (leftstick.getRawButton(1))
							{
								 talon1.set(1.0);
							}
						else
							{
								 talon1.set(0.0);
							}


            Timer.delay(0.005);		// wait for a motor update time
        }
    }




    public void test() {

    }
}
