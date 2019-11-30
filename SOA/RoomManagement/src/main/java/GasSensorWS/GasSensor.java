package GasSensorWS;


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

import org.eclipse.om2m.commons.resource.ContentInstance;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import fr.insa.om2m.mapper.Mapper;




/**
 * Root resource (exposed at "resource" path)
 */
@Path("")

public class GasSensor {

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

}
