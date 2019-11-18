package TemperatureWS;

import java.util.ArrayList;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
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


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;




/**
 * Root resource (exposed at "resource" path)
 */
@Path("")

public class Temperature {
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static ArrayList<ArrayList<String>> getTempe() {
		Client client = ClientBuilder.newClient();

		ArrayList<String> UrlList = getlistURL();
		ArrayList<ArrayList<String>> TempeAll = new ArrayList<ArrayList<String>>();

		for (int n = 0; n < UrlList.size(); n++) {
			ArrayList<String> Triplet = new ArrayList<String>();
			Triplet.add(UrlList.get(n));
			
			String infoStr = client.target("http://127.0.0.1:8080/~")
					.path(UrlList.get(n))
					.request(MediaType.APPLICATION_JSON)
					.header("X-M2M-Origin", "admin:admin")
					.get(String.class);
			JsonObject jsonObject1 = JsonParser.parseString(infoStr).getAsJsonObject();
			String lbl = jsonObject1.get("m2m:ae").getAsJsonObject().get("lbl").getAsJsonArray().get(2).getAsString();
			Triplet.add(lbl);
			
			String jsonStr = client.target("http://127.0.0.1:8080/~")
					.path(UrlList.get(n))
					.path("/DATA/la")
					.request(MediaType.APPLICATION_JSON)
					.header("X-M2M-Origin", "admin:admin")
					.get(String.class);

			JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
			String tempe = jsonObject.getAsJsonObject("m2m:cin").get("con").getAsString();
			Triplet.add(tempe);
			TempeAll.add(Triplet);
		}
		return TempeAll;
	}

	@Path("out")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static float getOutsideTemperature() {
		Client client = ClientBuilder.newClient();
		String jsonStr = client
				.target("http://api.openweathermap.org/data/2.5/weather?q=Toulouse&APPID=a4f7422d0e286eb0e0d5575ce0b2c8c8")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);

		JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
		Float tempe = jsonObject.getAsJsonObject("main").get("temp").getAsFloat();

		// Conversion en degres
		tempe = (float) (tempe - 273.15);
		//String result = String.valueOf(tempe);

		return tempe;
	}

	@Path("UrlList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static ArrayList<String> getlistURL() {
		Client client = ClientBuilder.newClient();

		ArrayList<String> RoomList = getUrlRoom();
		ArrayList<String> UrlAll = new ArrayList<String>();

		for (int n = 0; n < RoomList.size(); n++) {

			String jsonStr = client.target("http://127.0.0.1:8080/~")
					.path(RoomList.get(n))
					// room1-cse/room1
					.queryParam("fu", 1)
					.queryParam("lbl", "Type/sensor")
					.request(MediaType.APPLICATION_JSON)
					.header("X-M2M-Origin", "admin:admin")
					.get(String.class);

			JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
			JsonArray listtemp = jsonObject.getAsJsonArray("m2m:uril");

			Type listType = new TypeToken<ArrayList<String>>(){}.getType();
			ArrayList<String> finalList = new Gson().fromJson(listtemp, listType);

			UrlAll.addAll(finalList);
		}

		return UrlAll;
	}

	@Path("RoomList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static ArrayList<String> getlistRoom() {
		Client client = ClientBuilder.newClient();
		String jsonStr = client.target("http://127.0.0.1:8080/~/in-cse?fu=1&ty=16")
				.request(MediaType.APPLICATION_JSON)
				.header("X-M2M-Origin", "admin:admin")
				.get(String.class);

		JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
		JsonArray listtemp = jsonObject.getAsJsonArray("m2m:uril");

		Type listType = new TypeToken<ArrayList<String>>(){}.getType();
		ArrayList<String> finalList = new Gson().fromJson(listtemp, listType);

		return finalList;
	}

	@Path("RoomUrl")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static ArrayList<String> getUrlRoom() {
		Client client = ClientBuilder.newClient();

		ArrayList<String> UrlList = new ArrayList<String>();
		ArrayList<String> RoomList = getlistRoom();

		for (int n = 0; n < RoomList.size(); n++) {
			String jsonStr = client.target("http://127.0.0.1:8080/~/")
					.path(RoomList.get(n))
					.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
					.get(String.class);

			JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
			String csi = jsonObject.getAsJsonObject("m2m:csr").get("csi").getAsString();
			String rn = jsonObject.getAsJsonObject("m2m:csr").get("rn").getAsString();

			String url = csi + "/" + rn;
			UrlList.add(n, url);

		}

		return UrlList;
	}
	
	@GET
	@Path("{roomId}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTempe(@PathParam("roomId") Integer roomId, @PathParam("id") Integer id) {
		
		Client client = ClientBuilder.newClient();
		String resp = client.target("http://127.0.0.1:8080/~/room"+roomId+"-cse/room"+roomId)
				.path("TEMP_" + id + "/DATA/la")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin").get(String.class);

		
			JsonObject jsonObject = JsonParser.parseString(resp).getAsJsonObject();
			String temp = jsonObject.getAsJsonObject("m2m:cin").get("con").getAsString();

	return temp; 	
	}
	
	@POST
	@Path("setTempe/{roomId}/{id}/{tempe}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setTempe (@PathParam("roomId") Integer roomId, @PathParam("id") Integer id, @PathParam("tempe") String tempe){
		JsonObject con = new JsonObject();
		con.addProperty("con", tempe);
		JsonObject cin = new JsonObject();
		cin.add("m2m:cin", con);
		System.out.println(cin);
		System.out.println("Coucou");
		Client client = ClientBuilder.newClient();
		Response resp = client.target("http://127.0.0.1:8080/~/room"+roomId+"-cse/room"+roomId)
				.path("TEMP_" + id + "/DATA")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin")
				.header("Content-Type", "application/json;ty=4")
				.post(Entity.json(cin));
	}

}
