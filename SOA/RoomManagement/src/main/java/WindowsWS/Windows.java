package WindowsWS;

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
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;



/**
 * Root resource (exposed at "resource" path)
 */
@Path("")

public class Windows {
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static ArrayList<ArrayList<String>> getState() {
		Client client = ClientBuilder.newClient();

		ArrayList<String> UrlList = getlistURL();
		ArrayList<ArrayList<String>> StateAll = new ArrayList<ArrayList<String>>();

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
					.path("/STATUS/la")
					.request(MediaType.APPLICATION_JSON)
					.header("X-M2M-Origin", "admin:admin")
					.get(String.class);

			JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
			String tempe = jsonObject.getAsJsonObject("m2m:cin").get("con").getAsString();
			Triplet.add(tempe);
			StateAll.add(Triplet);
		}
		return StateAll;
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
					.queryParam("lbl", "Type/actuator")
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
				.path("WINDOW_" + id + "/STATUS/la")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin").get(String.class);

		
			JsonObject jsonObject = JsonParser.parseString(resp).getAsJsonObject();
			String temp = jsonObject.getAsJsonObject("m2m:cin").get("con").getAsString();

	return temp; 	
	}
	
	@POST
	@Path("setState/{roomId}/{id}/{state}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setState (@PathParam("roomId") Integer roomId, @PathParam("id") Integer id, @PathParam("state") Boolean state){
		/*ContentInstance cin = new ContentInstance();
		cin.setContent(String.valueOf(tempe));
		System.out.println(cin);
		Client client = ClientBuilder.newClient();
		Response resp = client.target("http://127.0.0.1:8080/~/room"+roomId+"-cse/room"+roomId)
				.path("TEMP_" + id + "/DATA/la")
				.request(MediaType.APPLICATION_JSON).header("X-M2M-Origin", "admin:admin").post(Entity.entity(Serializer.toXML(cin), "application/xml;ty=4"));*/
	}

}
