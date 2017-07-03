package net.scientifichooliganism.configmanager;

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
	private static final String PROPERTY_MESSAGE = "serviceDemo.message";
	private Gson gson;
	private ConfigManager cfg;

	/**
	* No argument constructor for the ConfigManager Demo Service.
	*/
	public ServiceDemo() {
		try {
			gson = new Gson();
			cfg = ConfigManager.getInstance();
			cfg.setRootNode(getClass().getName());
			cfg.setConfig(ServiceDemo.PROPERTY_MESSAGE, "Hello World!");
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	* 
	*/
	@GET
	@Path("/helloworld")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		String ret = new String();

		try {
			ret = System.getProperty(ServiceDemo.PROPERTY_MESSAGE);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		return gson.toJson(ret);
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