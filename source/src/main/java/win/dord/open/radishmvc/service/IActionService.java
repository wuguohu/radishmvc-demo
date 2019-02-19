package win.dord.open.radishmvc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import win.dord.open.radishmvc.annotation.Action;

public interface IActionService {
	String execute();
	HttpServletRequest getRequest();
	HttpServletResponse getResponse();
}
