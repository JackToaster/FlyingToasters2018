package commands;

import org.usfirst.frc.team3641.robot.Robot;

import commands.interfaces.Command;
import commands.interfaces.CommandCallback;
import path_generation.Path;
import path_generation.Waypoint;
import utilities.Logging;

/**
 * Command to follow a motion profile. Generates the path from the waypoints
 * when the object is created, so the object should be created before the robot
 * starts moving because it will freeze up the code a bit!
 * 
 * @author jack
 *
 */
public class MotionProfileCommand extends Command {
	/**
	 * The amount of extra time to add to the end of motion profiles to ensure that
	 * they are really done.
	 */
	public static double END_TIME_EXTRA = 0.1;
	/**
	 * The waypoints the path will be generated from
	 */
	private Waypoint[] wp;
	/**
	 * Whether the path will be followed backwards or forwards.
	 */
	private boolean backwards;
	/**
	 * The robot object that will be driven
	 */
	private Robot bot;
	/**
	 * The generated path from the waypoints
	 */
	private Path path;
	/**
	 * Time used to determine when the command is done
	 */
	private double time = 0;
	/**
	 * End time of the command determined from the lenght of the motion profile and
	 * the extra time
	 */
	private double endTime;

	// TODO make speed settings
	/**
	 * Create a motion profile command with the default settings for
	 * speed/acceleration
	 * 
	 * @param opMode
	 *            the opmode calling the command. used for callback.
	 * @param robot
	 *            the robot being driven
	 * @param name
	 *            the name of the command
	 * @param isBackwards
	 *            whether the robot should drive backwards
	 * @param waypoints
	 *            the waypoints to generate the path from
	 */
	public MotionProfileCommand(CommandCallback opMode, Robot robot, String name, boolean isBackwards,
			Waypoint... waypoints) {
		super(opMode, name);
		wp = waypoints;
		backwards = isBackwards;
		bot = robot;
		// generate path
		path = new Path(wp);
		Logging.l(path);
		endTime = path.endTime + END_TIME_EXTRA;
	}

	/**
	 * starts following the motion profile
	 */
	public void init() {
		time = 0;
		bot.driveBase.drivePath(path, backwards);
	}

	/**
	 * called continuously when the command is running. Checks if the command should
	 * end.
	 */
	public void periodic(double deltaTime) {
		super.periodic(deltaTime);
		time += deltaTime;
		if (time >= endTime) {
			endCommand();
		}
	}

	/**
	 * called to stop the command early. Stops feedback control on the drivebase.
	 */
	public void stop() {
		super.stop();
		bot.driveBase.setFeedbackActive(false);
	}
}
