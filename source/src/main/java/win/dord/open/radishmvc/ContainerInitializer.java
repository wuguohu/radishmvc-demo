package win.dord.open.radishmvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import win.dord.open.radishmvc.annotation.Action;
import win.dord.open.radishmvc.annotation.Param;
import win.dord.open.radishmvc.annotation.Service;
import win.dord.open.radishmvc.service.ActionService;

@HandlesTypes(ActionService.class)  
public class ContainerInitializer implements ServletContainerInitializer{
	Logger logger = LogManager.getLogger(ContainerInitializer.class);
	@SuppressWarnings("unchecked")
	@Override
	public void onStartup(Set<Class<?>> services, ServletContext context) throws ServletException {
		if(RadishConfig.log){
			logger.debug("radishmvc Initialize started");
		}
		
		Mappings.clear();
		
		Iterator<Class<?>> iterator = services.iterator();
		while (iterator.hasNext()) {
			Class<ActionService> actionServiceClass = (Class<ActionService>)iterator.next();
			String serviceName = actionServiceClass.getName();
			if(RadishConfig.log){
				logger.debug("action service: " + serviceName);
			}
			
			Service serviceAnnotation = actionServiceClass.getAnnotation(Service.class);
			if(serviceAnnotation == null){
				continue;
			}
			
			//Service Mapping
			String path = StringUtils.trimToEmpty(serviceAnnotation.value());
			if(StringUtils.isEmpty(path)){
				path = StringUtils.lowerCase(actionServiceClass.getSimpleName());
			}
			if(!path.startsWith("/")){
				path = "/" + path;
			}
			if(RadishConfig.log){
				logger.debug("service path: " + path);
			}
			Mappings.getServiceMappings().put(path, serviceName);
			
			//MethodMapping
			Map<String, String> actionMap = new HashMap<>();
			actionMap.put("execute", "execute");
			for(Method method : actionServiceClass.getMethods()){
				Action actionAnnotation = method.getAnnotation(Action.class);
				if(actionAnnotation != null){
					String action = StringUtils.trimToEmpty(actionAnnotation.value());
					if(StringUtils.isEmpty(action)){
						action = method.getName();
					}
					if(RadishConfig.log){
						logger.debug("service action: " + action);
					}
					actionMap.put(action, method.getName());
				}
			}
			Mappings.getActionMappings().put(serviceName, actionMap);
			
			//FieldMapping
			Map<String, String> fieldMap = new HashMap<>();
			for(Field field : actionServiceClass.getDeclaredFields()){
				field.setAccessible(true);
				Param paramAnnotation = field.getAnnotation(Param.class);
				if(paramAnnotation != null){
					String param = StringUtils.trimToEmpty(paramAnnotation.value());
					if(StringUtils.isEmpty(param)){
						param = field.getName();
					}
					if(RadishConfig.log){
						logger.debug("service param: " + field.getName());
					}
					fieldMap.put(field.getName(), param);
				}
			}
			
			Mappings.getParamMappings().put(serviceName, fieldMap);
		}
		
		if(RadishConfig.log){
			logger.debug("radishmvc Initialize finished");
		}
	}

}
