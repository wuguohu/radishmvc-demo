package win.dord.open.radishmvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionService implements IActionService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public void setContext(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}
}
