package Controller;

import java.util.TimerTask;

public class ControllerTask extends TimerTask{
		public void run(){
			String result = Controller.manageRooms();
			System.out.println(result);
		}
	}