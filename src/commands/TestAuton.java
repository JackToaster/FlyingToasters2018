package commands;

import org.usfirst.frc.team3641.robot.Robot;

import utilities.Logging;

/**
 * Simplest auton. Does nothing, prints strings.s
 * @author jack
 *
 */
public class TestAuton extends OpMode {
	public TestAuton(Robot callback, String name) {
		super(callback, name);
	}
	
	public void init() {
		Logging.h("Init called!");
	}
	
	public void periodic(double deltaTime) {
		Logging.h("Periodic called!");
	}
	
	public void stop() {
		Logging.h("Stop called!");
	}
}
