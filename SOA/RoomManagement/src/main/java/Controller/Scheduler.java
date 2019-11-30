package Controller;

import java.util.Timer;

public class Scheduler {
	public static void main(String args[]) throws InterruptedException {

		Timer time = new Timer(); // Instantiate Timer Object
		ControllerTask ct = new ControllerTask(); // Instantiate SheduledTask class
		time.schedule(ct, 0, 5000); // Create Repetitively task for every 1 secs
	}
}
