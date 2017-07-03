package net.scientifichooliganism.zookeeper;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

/**
* Demo to retrieve data from zookeeper.
*/
@Path("/zookeeper")
public class ServiceDemo {
	private Gson gson;
	private ConfigManager cfg;

	/**
	* No argument constructor for the Zookeeper Demo Service.
	*/
	public ServiceDemo() {
		gson = new Gson();
		cfg = ConfigManager.getInstance();
		cfg.setRootNode(this.getClass().getName());
	}

	/**
	* 
	*/
	@GET
	@Path("/helloworld")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		return gson.toJson("Hello World");
		//return gson.toJson(system.property.message);
	}

		/**
	* 
	*/
	@GET
	@Path("/updateConfig")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateConfig() {
		String ret = new String();

		try {
			cfg.updateConfigs();
			ret = "configuration updated successfully";
		}
		catch (Exception exc) {
			ret = "an error was encountered while attempting to update configuration";
			exc.printStackTrace();
		}

		return gson.toJson(ret);
	}
}