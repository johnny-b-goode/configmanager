package net.scientifichooliganism.configmanager;

import java.util.Collection;
import java.util.Vector;

import net.scientifichooliganism.configmanager.api.ConfigManager;

public class SystemPropertyConfigManager implements ConfigManager {
	private static ConfigManager instance;
	private String rootNode;
	private Collection <String> keys;

	private SystemPropertyConfigManager () {
		rootNode = new String();
		keys = new Vector <String> ();
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

		if (! keys.contains(key)) {
			keys.add(key);
		}

		System.setProperty(rootNode + "." + key, value);
	}

	public String getConfig (String key) {
		if (key == null) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.getConfig(String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("SystemPropertyConfigManager.getConfig(String) String is empty");
		}

		String ret = new String();

		try {
			ret = System.getProperty(rootNode + "." + key);
			ret = ret.replaceFirst(rootNode + ".", "");
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		return ret;
	}

	public Collection<String> getConfigs () {
		return keys;
	}

	public void updateConfigs () {
		throw new UnsupportedOperationException("SystemPropertyConfigManager.updateConfigs()");
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