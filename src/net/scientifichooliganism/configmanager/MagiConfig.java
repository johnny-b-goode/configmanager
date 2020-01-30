package net.scientifichooliganism.configmanager;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import net.scientifichooliganism.configmanager.api.ConfigManager;

public class MagiConfig implements ConfigManager {
	private static ConfigManager instance;
	private String rootNode;
	private List <ConfigManager> configManagers;

	private MagiConfig () {
		rootNode = new String();
		configManagers = new Vector <ConfigManager> ();
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new MagiConfig();
		}

		return instance;
	}

	public void setConfig (String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("MagiConfig.setConfig(String, String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("MagiConfig.setConfig(String, String) String is empty");
		}

		if (value == null) {
			throw new IllegalArgumentException("MagiConfig.setConfig(String, String) String is null");
		}

		throw new UnsupportedOperationException("MagiConfig.setConfig(String, String)");
	}

	/** This method will return the first value associated with a given key found, starting with the highest priority ConfigManager*/
	public String getConfig (String key) {
		if (key == null) {
			throw new IllegalArgumentException("MagiConfig.getConfig(String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("MagiConfig.getConfig(String) String is empty");
		}

		String ret = null;

		try {
			for (ConfigManager cm : configManagers) {
				ret = cm.getConfig(key);

				if (ret != null) {
					break;
				}
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		return ret;
	}

	public Collection<String> getConfigs () {
		throw new UnsupportedOperationException("SystemPropertyConfigManager.getConfigs()");
	}

	public void updateConfigs () {
		throw new UnsupportedOperationException("MagiConfig.updateConfigs()");
	}

	public String getRootNode () {
		return rootNode;
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("MagiConfig.setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("MagiConfig.setRootNode(String) was called with an empty String");
		}

		rootNode = in;
	}

	public void registerConfigManager (int priority, ConfigManager cm) {
		if (cm == null) {
			throw new IllegalArgumentException("MagiConfig.registerConfigManager(int, ConfigManager) was called with a null ConfigManager");
		}

		if (priority < 0) {
			throw new IllegalArgumentException("MagiConfig.registerConfigManager(int, ConfigManager) was called with an invalid value for int. int must be greater than or equal to zero.");
		}

		//Lists are zero indexed, so the length / size of the List is equal to the last index plus one
		if (priority > configManagers.size()) {
			throw new IllegalArgumentException("MagiConfig.registerConfigManager(int, ConfigManager) was called with an invalid value for int. int must be less than, equal to, or one greater than the lowest priority ConfigManager currently registered.");
		}

		if (! configManagers.contains(cm)) {
			configManagers.add(priority, cm);
		}
		else {
			if (configManagers.indexOf(cm) != priority) {
				configManagers.remove(cm);
				configManagers.add(priority, cm);
			}
		}
	}

	/** A convencience method to add a ConfigManager at the lowest priority*/
	public void registerConfigManager (ConfigManager cm) {
		if (cm == null) {
			throw new IllegalArgumentException("MagiConfig.registerConfigManager(ConfigManager) was called with a null ConfigManager");
		}

		if (! configManagers.contains(cm)) {
			configManagers.add(cm);
		}
	}

	public void deregisterConfigManager (ConfigManager cm) {
		if (cm == null) {
			throw new IllegalArgumentException("MagiConfig.deregisterConfigManager(ConfigManager) was called with a null ConfigManager");
		}

		if (configManagers.contains(cm)) {
			configManagers.remove(cm);
		}
	}

	/**This method will synchronize all configuration items from the lowest priority ConfigManager to the next highest priority ConfigManager until all configuration items have been synchronized in the highest priority ConfigurationManager. This is opposite normal behavior. This method will create any configuration items missing from higher priority ConfigurationManagers.*/
	public void syncConfigsUp () {
//		throw new UnsupportedOperationException("MagiConfig.syncConfigsUp()");

		//there is no point synchronizing the zeroth ConfigManager, so we only iterate until the second highest priority in the outermost loop
		for (int i = (configManagers.size() - 1); i >= 1; i--) {
			ConfigManager current = configManagers.get(i);

			for (String key : current.getConfigs()) {
				for (int j = (i - 1); j >= 0; j--) {
					ConfigManager cm = configManagers.get(j);

					if (cm.getConfig(key) != current.getConfig(key)) {
						cm.setConfig(key, current.getConfig(key));
					}
				}
			}
		}  
	}

	/**This method will synchronize all configuration items from the highest priority ConfigManager to the next lowest priority ConfigManager until all configuration items have been synchronized in the lowest priority ConfigurationManager. This method will create any configuration items missing from higher priority ConfigurationManagers.*/
	public void syncConfigsDown () {
//		throw new UnsupportedOperationException("MagiConfig.syncDown()");

		for (int i = 0; i < configManagers.size(); i++) {
			ConfigManager current = configManagers.get(i);

			for (String key : current.getConfigs()) {
				for (ConfigManager cm : configManagers.subList((i + 1), configManagers.size())) {
					if (cm.getConfig(key) != current.getConfig(key)) {
						cm.setConfig(key, current.getConfig(key));
					}
				}
			}
		}  
	}
}