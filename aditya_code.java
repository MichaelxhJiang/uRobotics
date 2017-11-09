/**
 * 
 */
package uRobotics;

/**
 * @author 335550265
 *
 */
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class MoveForward extends IterativeRobot {

	//Defines the variables as members of our Robot class
	RobotDrive myRobot;
	Joystick stick;
	Timer timer;

	//Initializes the variables in the robotInit method, this method is called when the robot is initializing
	public void robotInit() {
		myRobot = new RobotDrive(0,1);
		stick = new Joystick(1);
		timer = new Timer();
	}
}

public void teleopInit() {//The teleopInit method is called once each time the robot enters teleop mode
}

public void teleopPeriodic() { //The teleopPeriodic method is entered each time the robot receives a packet instructing it to be in teleoperated enabled mode
	myRobot.arcadeDrive(stick); //This line drives the robot using the values of the joystick and the motor controllers selected above
}
