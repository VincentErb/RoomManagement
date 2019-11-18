package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.Float;



import TemperatureWS.Temperature;

/**
 * Root resource (exposed at "resource" path)
 */
@Path("")
public class Controller {
	public void manageRooms(){
		// Get the temperature of all room in a list of triplets [url, room label, value]
		ArrayList<ArrayList<String>> allTemp = new ArrayList<ArrayList<String>>();
		allTemp = TemperatureWS.Temperature.getTempe();
		
		// Get external temperature
		float externalTemp = TemperatureWS.Temperature.getOutsideTemperature();
		
		// Get windows status
		ArrayList<ArrayList<String>> allWindowsStatus = new ArrayList<ArrayList<String>>();
		allWindowsStatus = WindowsWS.Windows.getState();
		
		// Difference of temperature at which we open/close the window
		float diffTemp = (float) 5.0;
		
		for (int i = 0; i < allTemp.size(); i++){
			float insideTemp = Float.parseFloat(allTemp.get(i).get(2));
			//if the room is much hotter than the outside close the windows
			if ( insideTemp - externalTemp > diffTemp){
				for (int j = 0; j < allWindowsStatus.size() ; j ++){
					if (allWindowsStatus.get(j).get(1) == allTemp.get(i).get(1)){
						//TODO : request setStatus 0
					}
				}
			}
			// if the room is much cooler than the outside open the windows
			else if ( insideTemp - externalTemp < -diffTemp){
				for (int j = 0; j < allWindowsStatus.size() ; j ++){
					if (allWindowsStatus.get(j).get(1) == allTemp.get(i).get(1)){
						//TODO : request setStatus 1
					}
				}
			}
		}
		
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static Map<String, Object> getAllInfo() {
		ArrayList<ArrayList<String>> TempeAll = new ArrayList<ArrayList<String>>();
		TempeAll = TemperatureWS.Temperature.getTempe();
		
		for (ArrayList<String> i : TempeAll){
			i.set(0, i.get(0).substring(i.get(0).length() - 1));
		}
		
		ArrayList<ArrayList<String>> WindowAll = new ArrayList<ArrayList<String>>();
		WindowAll = WindowsWS.Windows.getState();
		
		for (ArrayList<String> j : WindowAll){
			j.set(0, j.get(0).substring(j.get(0).length() - 1));
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("temp", TempeAll);
		map.put("window", WindowAll);
		return map;
	}
	
}