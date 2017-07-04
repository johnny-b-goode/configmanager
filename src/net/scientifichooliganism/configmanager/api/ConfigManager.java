package net.scientifichooliganism.configmanager.api;

public interface ConfigManager {
	//realistically this probably isn't even necessary, as configs will be set from other places (ie, not the code using this class)
	public void setConfig (String key, String value);
	public void updateConfigs ();
	public String getRootNode ();
	public void setRootNode (String in);
}