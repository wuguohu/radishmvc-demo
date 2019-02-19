package win.dord.open.radishmvc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import win.dord.open.radishmvc.service.ActionService;

public class Dispatcher {
	private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());
	private static Dispatcher dispatcher = new Dispatcher();
	
	private Dispatcher(){
	}
	
	public static Dispatcher getInstance(){
		return dispatcher;
	}
	
	@SuppressWarnings("unchecked")
	public void dispatch(HttpServletRequest request, HttpServletResponse response, RadishConfig actionConfig) throws RadishException {
		//execute action
		String path = StringUtils.trimToEmpty(request.getServletPath());
		if(!path.startsWith("/")){
			path = "/" + path;
		}
		if(path.equals("/")){
			path = "/index";
		}
		if(RadishConfig.log){
			logger.info("dispatch path: " + path);
		}
		String serviceName = Mappings.getServiceMappings().get(path);
		if(StringUtils.isEmpty(serviceName)){
			throw new RadishException(RadishException.ERR_CODE_SERVICE_NOT_FOUND, "");
		}
		
		String action = request.getParameter(actionConfig.getActionParam());
		if(StringUtils.isEmpty(action)){
			action = "execute";
		}
		String method = Mappings.getActionMappings().get(serviceName).get(action);
		if(StringUtils.isEmpty(method)){
			throw new RadishException(RadishException.ERR_CODE_ACTION_NOT_FOUND, serviceName, action);
		}
		
		if(RadishConfig.log){
			logger.info("service: " + serviceName);
			logger.info("method: " + method);
		}
		
		String viewPath = "";
		try {
			Class<ActionService> serviceClass = (Class<ActionService>) Class.forName(serviceName);
			ActionService service = serviceClass.newInstance();
			if(service != null){
				service.setContext(request, response);
				//fields
				Map<String, String> fields = Mappings.getParamMappings().get(serviceName);
				if(fields != null){
					Iterator<String> iterator = fields.keySet().iterator();
					String fieldName = "";
					String fieldValue = "";
					while(iterator.hasNext()){
						fieldName = iterator.next();
						fieldValue = request.getParameter(fields.get(fieldName));
						if(StringUtils.isNotEmpty(fieldValue)){
							try {
								Field field = serviceClass.getDeclaredField(fieldName);
								field.setAccessible(true);
								field.set(service, getFieldValue(field.getType().getName(), fieldValue));
								if(RadishConfig.log){
									logger.info("param: " + fieldName);
								}
							} catch (NoSuchFieldException e) {
								//do nothing
							} catch (NumberFormatException e) {
								//do nothing
							}
						}
						
						
					}
					
				}
				
				//execute method
				Method methodObj = serviceClass.getMethod(method);
				String serviceView = (String)methodObj.invoke(service);
				
				//forward
				if(StringUtils.isNotEmpty(serviceView)){
					viewPath = actionConfig.getViewBase();
					
					if (!viewPath.endsWith("/")){
						viewPath = viewPath + "/";
					}
					viewPath = viewPath + serviceView;
					
					if(RadishConfig.log){
						logger.info("forward: " + viewPath);
					}
					
					RequestDispatcher rd = request.getRequestDispatcher(viewPath);
					rd.forward(request, response);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RadishException(RadishException.ERR_CODE_SERVICE_NOT_FOUND, serviceName);
		} catch (InstantiationException e) {
			throw new RadishException(RadishException.ERR_CODE_SERVICE_NOT_FOUND, serviceName);
		} catch (IllegalAccessException e) {
			throw new RadishException(RadishException.ERR_CODE_SERVICE_CANT_ACCESS, serviceName);
		} catch (NoSuchMethodException e) {
			throw new RadishException(RadishException.ERR_CODE_ACTION_NOT_FOUND, serviceName, action);
		} catch (SecurityException e) {
			throw new RadishException(RadishException.ERR_CODE_ACTION_FAILED, serviceName, action);
		} catch (IllegalArgumentException e) {
			throw new RadishException(RadishException.ERR_CODE_ACTION_FAILED, serviceName, action);
		} catch (InvocationTargetException e) {
			throw new RadishException(RadishException.ERR_CODE_ACTION_FAILED, serviceName, action);
		} catch (ServletException e) {
			throw new RadishException(RadishException.ERR_CODE_VIEW_FORWARD_FAILED, viewPath);
		} catch (IOException e) {
			throw new RadishException(RadishException.ERR_CODE_VIEW_FORWARD_FAILED, viewPath);
		}
	}
	
	private Object getFieldValue(String type, String value) throws RadishException, NumberFormatException {
		switch (type) {
		case "int":
		case "java.lang.Integer":
			return Integer.valueOf(value);
		case "short":
		case "java.lang.Short":
			return Short.valueOf(value);
		case "long":
		case "java.lang.Long":
			return Long.valueOf(value);
		case "float":
		case "java.lang.Float":
			return Float.valueOf(value);
		case "double":
		case "java.lang.Double":
			return Double.valueOf(value);
		case "boolean":
		case "java.lang.Boolean":
			return Boolean.valueOf(value);
		case "java.lang.String":
			return value;
		case "java.util.Date":
			return new Date(Long.valueOf(value));
		case "java.util.Calender":
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(value));
			return calendar;
		default:
			throw new RadishException(RadishException.ERR_CODE_TYPE_NOT_SUPPORT, type);
		}
	}

}
