package net.scientifichooliganism.configmanager;

import net.scientifichooliganism.configmanager.api.ConfigManager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertiesFileConfigManagerTest {
	@Disabled
	@Test
	public void test01 () {
		assertThrows(Exception.class, () -> {
			//this should not compile
			//PropertiesFileConfigManager pfcm = new PropertiesFileConfigManager();
		});
	}

	@Test
	public void test02 () {
		assertNotNull(PropertiesFileConfigManager.getInstance());
	}

	@Test
	public void test03 () {
		String key = "testkey01";
		String value = "testvalue01";
		ConfigManager cm = PropertiesFileConfigManager.getInstance();

		assertThrows(UnsupportedOperationException.class, () -> {
			cm.setConfig(key, value);
		});

		assertThrows(UnsupportedOperationException.class, () -> {
			cm.getConfig(key);
		});
	}

	@Disabled
	@Test
	public void test04 () {
		//verify root node functionality
	}

	@Disabled
	@Test
	public void test05 () {
		//verify property is updated correctly
	}

	@Test
	public void test06 () {
		String key01 = "testkey01";
		String value01 = "testvalue01";
		String key02 = "testkey02";
		String value02 = "testvalue02";
		String key03 = "testkey03";
		String value03 = "testvalue03";
		ConfigManager cm = PropertiesFileConfigManager.getInstance();

		assertThrows(UnsupportedOperationException.class, () -> {
			cm.getConfigs();
		});
	}
}