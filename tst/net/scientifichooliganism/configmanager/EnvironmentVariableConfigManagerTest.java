package net.scientifichooliganism.configmanager;

import net.scientifichooliganism.configmanager.api.ConfigManager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnvironmentVariableConfigManagerTest {
	@Disabled
	@Test
	public void test01 () {
		assertThrows(Exception.class, () -> {
			//this should not compile
			//EnvironmentVariableConfigManager evcm = new EnvironmentVariableConfigManager();
		});
	}

	@Test
	public void test02 () {
		assertNotNull(EnvironmentVariableConfigManager.getInstance());
	}

	@Disabled
	@Test
	public void test03 () {
		String key = "testkey01";
		String value = "testvalue01";
		ConfigManager cm = EnvironmentVariableConfigManager.getInstance();

		//it is a bit of an undertaking to set an environment variable in Java
		assertThrows(UnsupportedOperationException.class, () -> {
			cm.setConfig(key, value);
		});

		//it is pretty straightforward to read an environment variable though
		assertTrue(cm.getConfig(key).equals(value));
	}

	@Disabled
	@Test
	public void test04 () {
		String key = "testkey01";
		String value = "testvalue01";
		//the content of rootNode is specifically problematic because it appears in value as well
		String rootNode = "test";
		ConfigManager cm = EnvironmentVariableConfigManager.getInstance();
		//verify root node functionality
	}

	@Disabled
	@Test
	public void test05 () {
		String key = "testkey01";
		String value = "testvalue01";
		String newValue = "newtestvalue01";
		ConfigManager cm = EnvironmentVariableConfigManager.getInstance();
		//verify property is updated correctly
	}

	@Disabled
	@Test
	public void test06 () {
		String key01 = "testkey01";
		String value01 = "testvalue01";
		String key02 = "testkey02";
		String value02 = "testvalue02";
		String key03 = "testkey03";
		String value03 = "testvalue03";
		ConfigManager cm = EnvironmentVariableConfigManager.getInstance();
		//verify keys are included in keys returned by ConfigManager.getConfigs()
	}
}