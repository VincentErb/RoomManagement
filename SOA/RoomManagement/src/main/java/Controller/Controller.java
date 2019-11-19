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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.Float;



import TemperatureWS.Temperature;
import Utils.JsonUtils;

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
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static Map<String, Object> getAllInfo() {
		
		// Fetch all temperature values & format them for front-end
		Client client = ClientBuilder.newClient();
		String jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/temperature/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);

		JsonArray jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> TempeAll = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> i : TempeAll){
			i.set(0, i.get(0).substring(i.get(0).length() - 1));
		}
		
		// Fetch all window state values & format them for front-end
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/windows/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
		
		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> WindowAll = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> j : WindowAll){
			j.set(0, j.get(0).substring(j.get(0).length() - 1));
		}
		
		// Send an object : {temp : [[id,room,temp], ...], window : [[id,room,state],...]}
		Map<String,Object> map = new HashMap<>();
		map.put("temp", TempeAll);
		map.put("window", WindowAll);
		return map;
	}
	
}