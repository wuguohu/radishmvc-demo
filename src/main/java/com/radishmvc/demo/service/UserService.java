package com.radishmvc.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import win.dord.open.radishmvc.annotation.Action;
import win.dord.open.radishmvc.annotation.Param;
import win.dord.open.radishmvc.annotation.Service;

@Service("/user")
public class UserService extends BaseService {
	Logger logger = LogManager.getLogger(UserService.class);
	@Param("username")
	private String user;
	@Param
	private String password;

	@Override
	public String execute() {
		return null;
	}
	
	@Action
	public String login() {
		logger.info("login");
		//do something
		getRequest().setAttribute("user", user);
		getRequest().setAttribute("password", password);
		return "result.jsp";
	}
	

}
