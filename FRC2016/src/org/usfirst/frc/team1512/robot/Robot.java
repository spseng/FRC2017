package org.usfirst.frc.team1512.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton; 	
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.tables.ITable;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
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

public class Robot extends SampleRobot{
	ITable visionTable;
    RobotDrive myRobot;
    Joystick leftstick, rightstick;
    XboxController xcontrol;
//XBOX 
    CANTalon talon1;
    DoubleSolenoid hook;
    Victor vic;
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    SendableChooser chooser;


    public Robot() {

    	vic = new Victor(2);
        myRobot = new RobotDrive(0, 1);
        talon1 = new CANTalon(1);

        myRobot.setExpiration(0.1);
        leftstick = new Joystick(0);
        rightstick = new Joystick(1);
        xcontrol = new XboxController(2);
       //XBOX
        hook = new DoubleSolenoid(0,1);


    }

    public void robotInit() {
    	
    	//NetworkTable.setClientMode();    	
    	NetworkTable.setTeam(1512);
    	NetworkTable.setIPAddress("169.254.153.20");
    	
    	visionTable = NetworkTable.getTable("vision");
    	
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto modes", chooser);

        
        talon1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        talon1.reverseSensor(false);
        
        talon1.configNominalOutputVoltage(+0.0f, -0.0f);
        talon1.configPeakOutputVoltage(+12.0f, -12.0f);
        
        talon1.setProfile(0);
        talon1.setF(0);
        talon1.setP(0);
        talon1.setI(0);
        talon1.setD(0);
        

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

    	String autoSelected = (String) chooser.getSelected();
//		String autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

    	switch(autoSelected) {
    	case customAuto:
            myRobot.setSafetyEnabled(false);
            myRobot.drive(-0.5, 1.0);	// spin at half speed
            Timer.delay(2.0);		//    for 2 seconds
            myRobot.drive(0.0, 0.0);	// stop robot
            break;
    	case defaultAuto:
    	default:
            myRobot.setSafetyEnabled(false);
            myRobot.drive(-0.5, 0.0);	// drive forwards half speed
            Timer.delay(2.0);		//    for 2 seconds
            myRobot.drive(0.0, 0.0);	// stop robot
            break;
    	}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {

    	
    	double speedmultiplier=0.85;
    	boolean haveTarget;
    	double test;
    	
    	
    	
    	
    	talon1.changeControlMode(TalonControlMode.Speed);
        
    	
    	
    	myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	haveTarget = visionTable.getBoolean("Have target", false);
        	test = visionTable.getNumber("Test", 0);
        	SmartDashboard.putNumber("Test", test);
        	SmartDashboard.putBoolean("Have target", haveTarget);
        	
        	
        	double targetSpeed = xcontrol.getY(Hand.kLeft) * 1000;
        	
        	if (xcontrol.getAButton() == true)
            {
            	talon1.set(targetSpeed);
            }
        	
        	SmartDashboard.putNumber("Target Speed", targetSpeed);
        	SmartDashboard.putNumber("Motor speed", talon1.getSpeed());
        	
        	
            //myRobot.tankDrive(leftstick.getY()*speedmultiplier, rightstick.getY()*speedmultiplier);
            talon1.set(xcontrol.getY()*12);
            if(leftstick.getRawButton(2))
            {
            	vic.set(leftstick.getY());
            }

            else if(!leftstick.getRawButton(2)&&!leftstick.getRawButton(2))
            {
            	vic.set(0);
            }
            //if(xcontrol.getAButton())
            //{
            	//talon1.set(1);
           // }
            
            
           //XBOX
            

   
        myRobot.setSafetyEnabled(true);


            //Prints shooter speed to smart dashboard
            SmartDashboard.putNumber("Talon Speed", talon1.getSpeed());

            //drive system
            myRobot.tankDrive(leftstick.getY()*speedmultiplier, rightstick.getY()*speedmultiplier);

            //if this button is pushed, lowers the reactivity of the motors
            //so that driver can be more precise for shooting/harvesting/climbing
            if(leftstick.getRawButton(4))
              {
                speedmultiplier = 0.1;
              }
            else
              {
                speedmultiplier = 0.85;
              }

            //controls victor speed controller
            if(leftstick.getRawButton(2))
              {
              	vic.set(1);
              }
            else if(leftstick.getRawButton(5))
              {
              	vic.set(0.25);
              }
            else if(!leftstick.getRawButton(2)&&!leftstick.getRawButton(2))
              {
              	vic.set(0);
              }

            //controls shooter
            if (rightstick.getRawButton(1))
              {
              	talon1.set(0.85);
              }
            else if(!rightstick.getRawButton(1))
              {
              	talon1.set(0);
              }


            /*if(leftstick.getRawButton(2)==true)
            {
            	hook.set(DoubleSolenoid.Value.kForward);
            }
            else if(rightstick.getRawButton(2)==true)
            {	
            	hook.set(DoubleSolenoid.Value.kReverse);
            }
            else
            {
            	hook.set(DoubleSolenoid.Value.kOff);
            }

*/
            Timer.delay(0.005);		// wait for a motor update time
        }
    }
    
    

    /**
     * Runs during test mode
     */
    
   /* public class XboxController {
        private final Joystick joy;
        private Button[] button=new Button[10];
        
        public Axis LeftStick=new Axis(0,0), RightStick=new Axis(0,0);
        public triggers Triggers=new triggers(0,0);
        public buttons Buttons;
        public POV DPad = new POV();
        
        
        public class triggers{
        	public double Right;
        	public double Left;
        	public double Combined;
        	public triggers(double r, double l){
        		Right=r;
        		Left=l;
        		combine();
        	}
        	private void combine(){
        		Combined=Right-Left;
        	}
        }
        public class POV{
        	public boolean Up=false, Down=false, Left = false, Right=false;
        	public int degrees=-1;
        	private void set(int degree){
        		Up=(degree==315 || degree==0 || degree==45);
        		Down=(degree<=225 && degree>=135);
        		Left=(degree<=315 && degree>=225);
        		Right=(degree<=135 && degree>=45);
        		degrees=degree;
        	}
        }
        public class Axis{
            public double X,Y;
            public Axis(double x,double y){
                X=x;
                Y=y;
            }
        }
        public class buttons{
            public Button A =button[0];
            public Button B =button[1];
            public Button X =button[2];
            public Button Y =button[3];
            public Button LB =button[4];
            public Button RB =button[5];
            public Button Back =button[6];
            public Button Start =button[7];
            public Button LeftStick =button[8];
            public Button RightStick =button[9];
        	
        }
        public class Button {
            public boolean current=false , last=false,changedDown=false,changedUp=false;
            private void set(boolean c){
            	last=current;
            	current=c;
            	changedDown=!last && current;
            	changedUp=last && !current;
            }
        }
        private void getDpad(){
        	DPad.set(joy.getPOV(0));
        }
        private void leftStick(){
        	LeftStick.X=joy.getRawAxis(0);
        	LeftStick.Y=joy.getRawAxis(1);
        }
        private void rightStick(){
        	RightStick.X=joy.getRawAxis(4);
        	RightStick.Y=joy.getRawAxis(5);
        }
        public void trigger(){
            Triggers.Left = joy.getRawAxis(2);
            Triggers.Right = joy.getRawAxis(3);
            Triggers.combine();
        }
        public void refresh(){
        	for(int i=1;i<11;i++){
        		button[i-1].set(joy.getRawButton(i));
        	}
            leftStick();
            rightStick();
            trigger();
            getDpad();
        }
        public XboxController(int i) {
            joy=new Joystick(i);
            for(int ii=0;ii<10;ii++){
            	button[ii]=new Button();
            }
            refresh();
            Buttons=new buttons();
        }*/
       /* public void vibrate(RumbleType type,float value){
        	joy.setRumble(type, value);
        }*/
    
    public void test() {
   
    }
}
