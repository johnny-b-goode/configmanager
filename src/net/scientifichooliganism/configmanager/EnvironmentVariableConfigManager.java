package net.scientifichooliganism.configmanager;

import java.util.Collection;
import java.util.Vector;

import net.scientifichooliganism.configmanager.api.ConfigManager;

public class EnvironmentVariableConfigManager implements ConfigManager {
	private static ConfigManager instance;
	private String rootNode;
	private Collection <String> keys;

	private EnvironmentVariableConfigManager () {
		rootNode = new String();
		keys = new Vector <String> ();
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new EnvironmentVariableConfigManager();
		}

		return instance;
	}

	public void setConfig (String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.setConfig(String, String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.setConfig(String, String) String is empty");
		}

		if (value == null) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.setConfig(String, String) String is null");
		}

		throw new UnsupportedOperationException("EnvironmentVariableConfigManager.setConfig(String, String)");
	}

	public String getConfig (String key) {
		if (key == null) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.getConfig(String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.getConfig(String) String is empty");
		}

		String ret = new String();

		try {
			if (rootNode.isEmpty()) {
				ret = System.getenv(key);
			}
			else {
				ret = System.getenv(rootNode + "." + key);
				ret = ret.replaceFirst(rootNode + "\\.", "");
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		return ret;
	}

	public Collection<String> getConfigs () {
		if ((keys.size() < 1) && (! rootNode.isEmpty())) {
			for (String key : System.getenv().keySet()) {
				if (key.startsWith(rootNode + ".")) {
					keys.add(key.replaceFirst(rootNode + "\\.", ""));
				}
			}
		}

		return keys;
	}

	public void updateConfigs () {
		throw new UnsupportedOperationException("EnvironmentVariableConfigManager.updateConfigs()");
	}

	public String getRootNode () {
		return rootNode;
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("EnvironmentVariableConfigManager.setRootNode(String) was called with an empty String");
		}

		rootNode = in;
	}
}