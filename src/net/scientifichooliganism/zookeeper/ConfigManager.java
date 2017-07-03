package net.scientifichooliganism.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;

public class ConfigManager {
	private static ConfigManager instance;
	private String rootNode;
	private ZooKeeper zk;

	private ConfigManager () {
		try {
			zk = new ZooKeeper("127.0.0.1:2181", 2000, null);
		}
		catch (Exception exc) {
			exc.printStackTrace();
			zk = null;
		}

		rootNode = new String();
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}

		return instance;
	}

	//realistically this probably isn't even necessary, as configs will be set from other places (ie, not the code using this class)
	public void setConfig (String key, String value) {
		try {
			//see if key exists in zk
			//if it does, update it
			//if it does not, create it
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		//set system.property
	}

	public void updateConfigs () {
		try {
			//get root node from zk
			//iterate through children
				//set properties accordingly
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public String getRootNode () {
		return rootNode;
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("setRootNode(String) was called with an empty String");
		}

		rootNode = in;
	}
}