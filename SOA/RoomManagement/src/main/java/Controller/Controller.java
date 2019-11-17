package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Float;



import TemperatureWS.Temperature;

public class Controller {
	public void manageRooms(){
		// Get the temperature of all room in a list of triplets [url, room label, value]
		ArrayList<ArrayList<String>> allTemp = new ArrayList<ArrayList<String>>();
		allTemp = TemperatureWS.Temperature.getTempe();
		
		// Get external temperature
		float externalTemp = TemperatureWS.Temperature.getOutsideTemperature();
		
		// TODO : get windows status
		
		// Difference of temperature at which we open/close the window
		float diffTemp = (float) 5.0;
		
		for (int i = 0; i < allTemp.size(); i++){
			float insideTemp = Float.parseFloat(allTemp.get(i).get(2));
			//if the room is much hotter than the outside close the windows
			if ( insideTemp - externalTemp > diffTemp){
				closeWindow(allTemp.get(i).get(1));
			}
			// if the room is much cooler than the outside open the windows
			else if ( insideTemp - externalTemp < -diffTemp){
				openWindow(allTemp.get(i).get(1));
			}
		}
		
	}

	private void openWindow(String string) {
		// TODO Auto-generated method stub
		
	}

	private void closeWindow(String string) {
		// TODO Auto-generated method stub
		
	}
	
}