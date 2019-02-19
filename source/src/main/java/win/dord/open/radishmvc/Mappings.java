package win.dord.open.radishmvc;

import java.util.HashMap;
import java.util.Map;

public class Mappings {
	private Mappings(){
	}
	
	public static Map<String, String> getServiceMappings(){
		return Holder.services;
	}
	
	public static Map<String, Map<String, String>> getActionMappings(){
		return Holder.actions;
	}
	
	public static Map<String, Map<String, String>> getParamMappings(){
		return Holder.params;
	}
	
	public static void clear(){
		Holder.services.clear();
		Holder.actions.clear();
		Holder.params.clear();
	}
	
	private static class Holder {
		private static Map<String, String> services = new HashMap<>();
		private static Map<String, Map<String, String>> actions = new HashMap<>();
		private static Map<String, Map<String, String>> params = new HashMap<>();
	}
	
	
}
