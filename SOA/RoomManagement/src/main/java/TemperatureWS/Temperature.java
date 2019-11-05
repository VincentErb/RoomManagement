package TemperatureWS;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "resource" path)
 */
@Path("")
public class Temperature {
	@Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTemperature() {
    	Client client = ClientBuilder.newClient();
    	String jsonStr = client.target("http://127.0.0.1:8080/~/room1-cse/room1")
    			.request(MediaType.APPLICATION_JSON)
    			.header("X-M2M-Origin", "admin:admin")
    			.get(String.class);
    	return jsonStr;
    }
	
	@Path("out")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOutsideTemperature() {
    	Client client = ClientBuilder.newClient();
    	String jsonStr = client.target("http://api.openweathermap.org/data/2.5/weather?q=Toulouse&APPID=a4f7422d0e286eb0e0d5575ce0b2c8c8")
    			.request(MediaType.APPLICATION_JSON)
    			.get(String.class);
    	return jsonStr;
    }
	
}
