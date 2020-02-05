package net.scientifichooliganism.configmanager;

import net.scientifichooliganism.configmanager.api.ConfigManager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagiConfigTest {
	@Disabled
	@Test
	public void test01 () {
		assertThrows(Exception.class, () -> {
			//this should not compile
			//MagiConfig mc = new MagiConfig();
		});
	}

	@Test
	public void test02 () {
		assertNotNull(MagiConfig.getInstance());
	}

	@Test
	public void test03 () {
		String key = "testkey01";
		String value = "testvalue01";
		ConfigManager cm = MagiConfig.getInstance();

		//no ConfigManagers have been registered yet
		assertThrows(RuntimeException.class, () -> {
			cm.setConfig(key, value);
		});
	}

//the simplest use case
	@Test
	public void test04 () {
		String key = "systestkey01";
		String value = "systestvalue01";
		MagiConfig mc = MagiConfig.getInstance();
		mc.registerConfigManager(SystemPropertyConfigManager.getInstance());
		mc.setConfig(key, value);
		assertTrue(mc.getConfig(key).equals(value));
	}

//TODO: Should MagiConfig permit the root node to be configured for all registered ConfigManagers?
	@Disabled
	@Test
	public void test06 () {
		//
	}

	@Disabled
	@Test
	public void test07 () {
		String key = "envtestkey01";
		String value = "envtestvalue01";
		MagiConfig mc = MagiConfig.getInstance();
		mc.registerConfigManager(SystemPropertyConfigManager.getInstance());
		assertNull(mc.getConfig(key));
		EnvironmentVariableConfigManager.getInstance().setRootNode("magiconfig");
		mc.registerConfigManager(EnvironmentVariableConfigManager.getInstance());
		assertTrue(mc.getConfig(key).equals(value));
		mc.syncConfigsUp();
		mc.deregisterConfigManager(EnvironmentVariableConfigManager.getInstance());
		assertTrue(mc.getConfig(key).equals(value));
	}

	@Disabled
	@Test
	public void test08 () {
		String key01 = "systestkey01";
		String value01 = "systestvalue01";
		String key02 = "systestkey01";
		String value02 = "systestvalue01";
		String key03 = "envtestkey01";
		MagiConfig mc = MagiConfig.getInstance();
		EnvironmentVariableConfigManager.getInstance().setRootNode("magiconfig");
		mc.registerConfigManager(SystemPropertyConfigManager.getInstance());
		mc.registerConfigManager(EnvironmentVariableConfigManager.getInstance());
		assertTrue(mc.getConfigs().contains(key01));
		assertTrue(mc.getConfigs().contains(key02));
		assertTrue(mc.getConfigs().contains(key03));
	}

//TODO: Test changing the priority of ConfigManagers
	@Disabled
	@Test
	public void test09 () {
		//
	}

	@Test
	public void lastTest () {
		MagiConfig mc = MagiConfig.getInstance();
		Exception exc = assertThrows(UnsupportedOperationException.class, () -> {
			mc.hammer();
		});
		assertTrue(exc.getMessage().equals("can't touch this"));
	}
}