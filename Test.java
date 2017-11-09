// import libraries
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/*
 * public class Test extends IterativeRobot
 * 
 * Description:
 * This class will take joystick input and translate that into movements in a robot.
 * 
 * Vars(class type):
 * myRobot(RobotDrive) - Object used to access the ports on the micro-controller in order to control motors
 * stick(Joystick) - Object used to access the inputs form the joystick
 */
public class Test extends IterativeRobot {
	RobotDrive myRobot; // declare myRobot object to control the robot
	Joystick stick; // declare stick object to take input form the joytstick

	public void robotinit() { // initialize robot
		myRobot = new RobotDrive(0, 1); // initialize and link RobotDrive object to ports 0, and 1, for the two motors
		stick = new Joystick(1); // initialize and setup stick object at USB port 1
	}
	
	public void teleopPeriodic() { // activates with any joystick input
		myRobot.arcadeDrive(stick); // send joystick input to micro-controller through the myRobot object
	}
}
