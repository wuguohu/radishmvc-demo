package com.radishmvc.demo.service;

import win.dord.open.radishmvc.annotation.Service;

@Service("/index")
public class IndexService extends BaseService {

	@Override
	public String execute() {
		return "login.jsp";
	}

}
