package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.GregorianCalendar; 

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.om2m.commons.resource.ContentInstance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.Float;
import Utils.JsonUtils;
import fr.insa.om2m.mapper.Mapper;

/**
 * Root resource (exposed at "resource" path)
 */
@Path("")
public class Controller {
	
	static Boolean closedForTheNight = false;
	
	@Path("manage")
	@GET
	public static String manageRooms(){
		Controller.manageScenario1();
		Controller.manageScenario2();
		return "OK";
	}
	
	public static void manageScenario1(){
		
		// Difference of temperature at which we open/close the window
		float diffTemp = 5;
		
		// Minimum air quality after which we default open the window
		float thresholdAir = 70;
				
		// Get the temperature of all room in a list of triplets [url, room label, value]
		Client client = ClientBuilder.newClient();
		String jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/temperature/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);

		JsonArray jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> allTemp = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		// Get the air quality of all rooms
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/gas/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
		
		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> allGas = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		
		// Get external temperature
		float externalTemp = Float.parseFloat(client.target("http://127.0.0.1:8484/RoomManagement/temperature/out")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class));
		
		
		
		
		// Get windows status
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/windows/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
		
		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> allWindowsStatus = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> l : allWindowsStatus) {
			l.set(0, l.get(0).substring(l.get(0).length() - 1));
		}
		
		
		for (int i = 0; i < allTemp.size(); i++){
			float insideTemp = Float.parseFloat(allTemp.get(i).get(2));
			//if the room is much hotter than the outside close the windows
			if (insideTemp - externalTemp > diffTemp){
				for (int j = 0; j < allWindowsStatus.size() ; j ++){
					if (allWindowsStatus.get(j).get(1).equals(allTemp.get(i).get(1))){
						Response resp = client.target("http://127.0.0.1:8484/RoomManagement/windows/setState/")
								.path(String.valueOf(i+1)) // Room
								.path("/" + allWindowsStatus.get(j).get(0)) // ID
								.path("/0")
								.request(MediaType.APPLICATION_JSON).post(null); 
					}
				}
			}
			// if the room is much cooler than the outside open the windows
			else if ( insideTemp - externalTemp < -diffTemp){
				for (int j = 0; j < allWindowsStatus.size() ; j ++){
					if (allWindowsStatus.get(j).get(1).equals(allTemp.get(i).get(1))){
						Response resp = client.target("http://127.0.0.1:8484/RoomManagement/windows/setState/")
								.path(String.valueOf(i+1)) // Room
								.path("/" + allWindowsStatus.get(j).get(0)) // ID
								.path("/1")
								.request(MediaType.APPLICATION_JSON).post(null); 
					}
				}
			}
			
			// If air quality below a certain threshold in a room, open the windows anyway
			for (int k = 0; k < allGas.size(); k++){
				if(allGas.get(k).get(1).equals(allTemp.get(i).get(1))){
					if(Float.parseFloat(allGas.get(k).get(2)) < thresholdAir){

						for (int j = 0; j < allWindowsStatus.size() ; j ++){
							if (allWindowsStatus.get(j).get(1).equals(allTemp.get(i).get(1))){
								Response resp = client.target("http://127.0.0.1:8484/RoomManagement/windows/setState/")
										.path(String.valueOf(i+1)) // Room
										.path("/" + allWindowsStatus.get(j).get(0)) // ID
										.path("/1")
										.request(MediaType.APPLICATION_JSON).post(null); 
							}
						}
					} 
				}
			}
		}
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
		
		// Fetch all gas sensor values & format them for front-end
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/gas/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);

		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> GasAll = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> i : GasAll){
			i.set(0, i.get(0).substring(i.get(0).length() - 1));
		}
		
		// Fetch all light sensor values & format them for front-end
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/light/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);

		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> LightAll = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> i : LightAll){
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
		
		// Fetch all lamp state values & format them for front-end
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/lamp/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
		
		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> LampAll = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> j : LampAll){
			j.set(0, j.get(0).substring(j.get(0).length() - 1));
		}
		
		// Send an object : {temp : [[id,room,temp], ...], window : [[id,room,state],...]}
		Map<String,Object> map = new HashMap<>();
		map.put("temp", TempeAll);
		map.put("gas", GasAll);
		map.put("light", LightAll);
		map.put("window", WindowAll);
		map.put("lamp", LampAll);
		return map;
	}
	
	@GET
	@Path("setTempe/{roomId}/{tempe}")
	public Response setRoomTempe (@PathParam("roomId") String roomId,  @PathParam("tempe") String tempe){
		
		Client client = ClientBuilder.newClient();
		Response resp = client.target("http://127.0.0.1:8484/RoomManagement/temperature/setTempe/")
				.path(roomId + "/1/")
				.path(tempe)
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return resp;
	}
	
	@GET
	@Path("setGas/{roomId}/{gas}")
	public Response setRoomGas (@PathParam("roomId") String roomId,  @PathParam("gas") String gas){
		Client client = ClientBuilder.newClient();
		Response resp = client.target("http://127.0.0.1:8484/RoomManagement/gas/setGas/")
				.path(roomId + "/1/")
				.path(gas)
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return resp;
	}
	
	@GET
	@Path("setLight/{roomId}/{light}")
	public Response setRoomLight (@PathParam("roomId") String roomId,  @PathParam("light") String light){
		
		Client client = ClientBuilder.newClient();
		Response resp = client.target("http://127.0.0.1:8484/RoomManagement/light/setLight/")
				.path(roomId + "/1/")
				.path(light)
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return resp;
	}
	
	public static void manageScenario2(){
		double lightThreshold = 0.4;
		
		
		//Idée : récupérer l'heure et ouvrir/fermer fenêtres, lumières (+ portes si on le fait) 
		java.util.GregorianCalendar calendar = new GregorianCalendar();
		int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
		hour = 10; 
		//int minute = calendar.get(java.util.Calendar.MINUTE);
		
		Client client = ClientBuilder.newClient();
		
				
		// Get windows status
		String jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/windows/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
		
		JsonArray jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> allWindowsStatus = JsonUtils.getArrayFromJsonArray(jsonArray);
		
		for (ArrayList<String> l : allWindowsStatus){
			l.set(0, l.get(0).substring(l.get(0).length() - 1));
		}
		
		// Get light sensor status
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/light/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
				
		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> allLights = JsonUtils.getArrayFromJsonArray(jsonArray);
				
		for (ArrayList<String> l : allLights){
			l.set(0, l.get(0).substring(l.get(0).length() - 1));
		}
		
		// Get lamp status
		jsonStr = client.target("http://127.0.0.1:8484/RoomManagement/lamp/all")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.get(String.class);
				
		jsonArray = JsonParser.parseString(jsonStr).getAsJsonArray();
		ArrayList<ArrayList<String>> allLampsStatus = JsonUtils.getArrayFromJsonArray(jsonArray);
				
		for (ArrayList<String> l : allLampsStatus){
			l.set(0, l.get(0).substring(l.get(0).length() - 1));
		}
		
		for (int i = 0; i < allLights.size(); i++){
			float lightValue = Float.parseFloat(allLights.get(i).get(2));
			//if the lighting is high enough, turn off lamps
			if (lightValue >= lightThreshold){
				
				for (int j = 0; j < allLampsStatus.size() ; j ++){
					if (allLampsStatus.get(j).get(1).equals(allLights.get(i).get(1))){
						Response resp = client.target("http://127.0.0.1:8484/RoomManagement/lamp/setState/")
								.path(String.valueOf(i+1)) // Room
								.path("/" + allLampsStatus.get(j).get(0)) // ID
								.path("/0")
								.request(MediaType.APPLICATION_JSON).post(null); 
					}
				}
			}
			//if the lighting is not high enough, turn on lamps
			else if (lightValue < lightThreshold){
				for (int j = 0; j < allLampsStatus.size() ; j ++){
					if (allLampsStatus.get(j).get(1).equals(allLights.get(i).get(1))){
						Response resp = client.target("http://127.0.0.1:8484/RoomManagement/lamp/setState/")
								.path(String.valueOf(i+1)) // Room
								.path("/" + allLampsStatus.get(j).get(0)) // ID
								.path("/1")
								.request(MediaType.APPLICATION_JSON).post(null); 
					}
				}
			}
		}
		
		//si c'est après 20h et avant 8h on ferme si c'est ouvert. 
		//Pb :il va checker toute la nuit toutes les fenetres alors qu'une fois qu'il les as fermées ca devrait être ok
		//Solution : faire un closed = 1 une fois que c'est fermé et le passer à 0 à 8h ?
		//Ajouter la notion d'heure dans le scénario 1 sinon ca va réouvrir
		/*boolean closed = false; 
		if (hour > 8 && closed) closed = false; */
		
		
		
		if (hour > 20 || hour < 8){
			for (int j = 0; j < allWindowsStatus.size() ; j ++){
				ArrayList<String> wds = allWindowsStatus.get(j); 
				if (wds.get(2).equals("1")){
					Response resp = client.target("http://127.0.0.1:8484/RoomManagement/windows/setState/")
							.path(String.valueOf(wds.get(1).substring(wds.get(1).length()-1))) // Room
							.path("/" + allWindowsStatus.get(j).get(0)) // ID
							.path("/0")
							.request(MediaType.APPLICATION_JSON).post(null); 
				}
			}
			for (int j = 0; j < allLampsStatus.size() ; j ++){
				ArrayList<String> lamp = allLampsStatus.get(j);
				if (lamp.get(2).equals("1")){
					Response resp = client.target("http://127.0.0.1:8484/RoomManagement/lamp/setState/")
							.path(String.valueOf(lamp.get(1).substring(lamp.get(1).length()-1))) // Room
							.path("/" + allLampsStatus.get(j).get(0)) // ID
							.path("/0")
							.request(MediaType.APPLICATION_JSON).post(null); 
				}
			}
			closedForTheNight=true;
		}
	}
}