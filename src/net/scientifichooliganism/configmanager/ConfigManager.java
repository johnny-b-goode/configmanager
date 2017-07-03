package net.scientifichooliganism.configmanager;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;

public class ConfigManager {
	private static final String PROPERTY_SERVER = "zookeeper.server";
	private static final String PROPERTY_ROOT_NODE = "zookeeper.rootNode";
	private static final String PROPERTY_SESSION_TIMEOUT = "zookeeper.sessionTimeout";
	private static final String DEFAULT_SERVER = "127.0.0.1:2181";
	private static final String DEFAULT_SESSION_TIMEOUT = "2000";
	private static ConfigManager instance;
	private ZooKeeper zk;

	private ConfigManager () {
		try {
			if ((System.getProperty(ConfigManager.PROPERTY_SERVER) == null) || (System.getProperty(ConfigManager.PROPERTY_SERVER).isEmpty())) {
				System.setProperty(ConfigManager.PROPERTY_SERVER, ConfigManager.DEFAULT_SERVER);
			}

			if ((System.getProperty(ConfigManager.PROPERTY_SESSION_TIMEOUT) == null) || (System.getProperty(ConfigManager.PROPERTY_SESSION_TIMEOUT).isEmpty())) {
				System.setProperty(ConfigManager.PROPERTY_SESSION_TIMEOUT, ConfigManager.DEFAULT_SESSION_TIMEOUT);
			}

			if ((System.getProperty(ConfigManager.PROPERTY_ROOT_NODE) == null) || (System.getProperty(ConfigManager.PROPERTY_ROOT_NODE).isEmpty())) {
				System.setProperty(ConfigManager.PROPERTY_ROOT_NODE, "/" + getClass().getName());
			}

			zk = new ZooKeeper(System.getProperty(ConfigManager.PROPERTY_SERVER), Integer.parseInt(System.getProperty(PROPERTY_SESSION_TIMEOUT)), null);
		}
		catch (Exception exc) {
			exc.printStackTrace();
			zk = null;
		}
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}

		return instance;
	}

	//realistically this probably isn't even necessary, as configs will be set from other places (ie, not the code using this class)
	public void setConfig (String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("ConfigManager.setConfig(String, String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("ConfigManager.setConfig(String, String) String is empty");
		}

		if (value == null) {
			throw new IllegalArgumentException("ConfigManager.setConfig(String, String) String is null");
		}

		System.setProperty(key, value);

		try {
			if (zk.exists((getRootNode() + "/" + key), false) == null) {
				zk.create((getRootNode() + "/" + key), System.getProperty(key).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			else {
				zk.setData((getRootNode() + "/" + key), System.getProperty(key).getBytes(), -1);
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void updateConfigs () {
		try {
			for (String key : zk.getChildren(getRootNode(), false)) {
				String value = new String();

				try {
					value = new String(zk.getData((getRootNode() + "/" + key), false, zk.exists((getRootNode() + "/" + key), false)));
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}

				if (value == null) {
					value = "";
				}

				System.setProperty(key, value);
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public String getRootNode () {
		return System.getProperty(ConfigManager.PROPERTY_ROOT_NODE);
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("ConfigManager.setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("ConfigManager.setRootNode(String) was called with an empty String");
		}

		if (! in.startsWith("/")) {
			in = "/" + in;
		}

		System.setProperty(ConfigManager.PROPERTY_ROOT_NODE, in);
	}
}