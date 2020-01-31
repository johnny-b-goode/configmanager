package net.scientifichooliganism.configmanager;

import net.scientifichooliganism.configmanager.api.ConfigManager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemPropertyConfigManagerTest {
	@Disabled
	@Test
	public void test01 () {
		assertThrows(Exception.class, () -> {
			//this should not compile
			//SystemPropertyConfigManager spcm = new SystemPropertyConfigManager();
		});
	}

	@Test
	public void test02 () {
		assertNotNull(SystemPropertyConfigManager.getInstance());
	}

	@Test
	public void test03 () {
		String key = "testkey01";
		String value = "testvalue01";
		ConfigManager cm = SystemPropertyConfigManager.getInstance();
		cm.setConfig(key, value);
		assertTrue(cm.getConfig(key).equals(value));
	}

	@Test
	public void test04 () {
		String key = "testkey01";
		String value = "testvalue01";
		//the content of rootNode is specifically problematic because it appears in value as well
		String rootNode = "test";
		ConfigManager cm = SystemPropertyConfigManager.getInstance();
		cm.setRootNode(rootNode);
		cm.setConfig(key, value);
		assertTrue(System.getProperty(rootNode + "." + key).equals(value));
	}

	@Test
	public void test05 () {
		String key = "testkey01";
		String value = "testvalue01";
		String newValue = "newtestvalue01";
		ConfigManager cm = SystemPropertyConfigManager.getInstance();
		cm.setConfig(key, value);
		assertTrue(cm.getConfig(key).equals(value));
		cm.setConfig(key, newValue);
		assertTrue(cm.getConfig(key).equals(newValue));
	}

	@Test
	public void test06 () {
		String key01 = "testkey01";
		String value01 = "testvalue01";
		String key02 = "testkey02";
		String value02 = "testvalue02";
		String key03 = "testkey03";
		String value03 = "testvalue03";
		ConfigManager cm = SystemPropertyConfigManager.getInstance();
		cm.setConfig(key01, value01);
		cm.setConfig(key02, value02);
		cm.setConfig(key03, value03);
		assertTrue(cm.getConfigs().contains(key01));
		assertTrue(cm.getConfigs().contains(key02));
		assertTrue(cm.getConfigs().contains(key03));
	}
}