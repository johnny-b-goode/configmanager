package net.scientifichooliganism.configmanager;

import java.util.Collection;
import java.util.Vector;

import net.scientifichooliganism.configmanager.api.ConfigManager;

public class PropertiesFileConfigManager implements ConfigManager {
	private static ConfigManager instance;

	private PropertiesFileConfigManager () {
		//
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new PropertiesFileConfigManager();
		}

		return instance;
	}

	public void setConfig (String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.setConfig(String, String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.setConfig(String, String) String is empty");
		}

		if (value == null) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.setConfig(String, String) String is null");
		}

		throw new UnsupportedOperationException("PropertiesFileConfigManager.setConfig(String, String)");
	}

	public String getConfig (String key) {
		if (key == null) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.getConfig(String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.getConfig(String) String is empty");
		}

		throw new UnsupportedOperationException("PropertiesFileConfigManager.getConfig(String)");
	}

	public Collection<String> getConfigs () {
		throw new UnsupportedOperationException("PropertiesFileConfigManager.getConfigs()");
	}

	public void updateConfigs () {
		throw new UnsupportedOperationException("PropertiesFileConfigManager.updateConfigs()");
	}

	public String getRootNode () {
		throw new UnsupportedOperationException("PropertiesFileConfigManager.getRootNode()");
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("PropertiesFileConfigManager.setRootNode(String) was called with an empty String");
		}

		throw new UnsupportedOperationException("PropertiesFileConfigManager.setRootNode(String)");
	}
}