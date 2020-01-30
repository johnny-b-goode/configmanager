package net.scientifichooliganism.configmanager;

import java.util.Collection;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs;

import net.scientifichooliganism.configmanager.api.ConfigManager;

public class ZooKeeperConfigManager implements ConfigManager {
	private static final String PROPERTY_SERVER = "zookeeper.server";
	private static final String PROPERTY_ROOT_NODE = "zookeeper.rootNode";
	private static final String PROPERTY_SESSION_TIMEOUT = "zookeeper.sessionTimeout";
	private static final String DEFAULT_SERVER = "127.0.0.1:2181";
	private static final String DEFAULT_SESSION_TIMEOUT = "2000";
	private static ConfigManager instance;
	private ZooKeeper zk;

	private ZooKeeperConfigManager () {
		try {
			if ((System.getProperty(ZooKeeperConfigManager.PROPERTY_SERVER) == null) || (System.getProperty(ZooKeeperConfigManager.PROPERTY_SERVER).isEmpty())) {
				System.setProperty(ZooKeeperConfigManager.PROPERTY_SERVER, ZooKeeperConfigManager.DEFAULT_SERVER);
			}

			if ((System.getProperty(ZooKeeperConfigManager.PROPERTY_SESSION_TIMEOUT) == null) || (System.getProperty(ZooKeeperConfigManager.PROPERTY_SESSION_TIMEOUT).isEmpty())) {
				System.setProperty(ZooKeeperConfigManager.PROPERTY_SESSION_TIMEOUT, ZooKeeperConfigManager.DEFAULT_SESSION_TIMEOUT);
			}

			if ((System.getProperty(ZooKeeperConfigManager.PROPERTY_ROOT_NODE) == null) || (System.getProperty(ZooKeeperConfigManager.PROPERTY_ROOT_NODE).isEmpty())) {
				System.setProperty(ZooKeeperConfigManager.PROPERTY_ROOT_NODE, "/" + getClass().getName());
			}

			zk = new ZooKeeper(System.getProperty(ZooKeeperConfigManager.PROPERTY_SERVER), Integer.parseInt(System.getProperty(PROPERTY_SESSION_TIMEOUT)), null);
		}
		catch (Exception exc) {
			exc.printStackTrace();
			zk = null;
		}
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ZooKeeperConfigManager();
		}

		return instance;
	}

	public void setConfig (String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.setConfig(String, String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.setConfig(String, String) String is empty");
		}

		if (value == null) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.setConfig(String, String) String is null");
		}

		System.setProperty(key, value);

		try {
			if (zk.exists(getRootNode(), false) == null) {
				zk.create(getRootNode(), "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}

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

	public String getConfig (String key) {
		if (key == null) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.getConfig(String) String is null");
		}

		if (key.isEmpty()) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.getConfig(String) String is empty");
		}

		throw new UnsupportedOperationException("ZooKeeperConfigManager.getConfig(String)");
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

	public Collection<String> getConfigs () {
		throw new UnsupportedOperationException("SystemPropertyConfigManager.getConfigs()");
	}

	public String getRootNode () {
		return System.getProperty(ZooKeeperConfigManager.PROPERTY_ROOT_NODE);
	}

	public void setRootNode (String in) {
		if (in == null) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.setRootNode(String) was called with a null String");
		}

		if (in.isEmpty()) {
			throw new IllegalArgumentException("ZooKeeperConfigManager.setRootNode(String) was called with an empty String");
		}

		if (! in.startsWith("/")) {
			in = "/" + in;
		}

		System.setProperty(ZooKeeperConfigManager.PROPERTY_ROOT_NODE, in);
	}
}