package net.scientifichooliganism.configmanager;

import net.scientifichooliganism.configmanager.api.ConfigManager;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;

public class SystemPropertyConfigManager implements ConfigManager {
	private static ConfigManager instance;
	private String rootNode;

	private SystemPropertyConfigManager () {
		rootNode = new String();
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new SystemPropertyConfigManager();
		}

		return instance;
	}

	public void setConfig (String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.setConfig(String, String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.setConfig(String, String) String is empty");
		}

		if (value == null) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.setConfig(String, String) String is null");
		}

		System.setProperty(key, value);
	}

	public void updateConfigs () {
		return;
	}

	public String getRootNode () {
		return rootNode;
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.setRootNode(String) was called with an empty String");
		}

		rootNode = in;
	}
}