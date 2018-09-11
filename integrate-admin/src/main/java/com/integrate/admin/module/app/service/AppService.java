package com.integrate.admin.module.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.admin.module.app.dao.AppDao;
import com.integrate.admin.module.app.model.AppInfo;

@Service
public class AppService {

	@Autowired
	private AppDao appDao;
	
	public AppInfo getAppinfo(){
		return appDao.getApp();
	}
	
	public boolean update(String  url,String version){
		return appDao.update(url, version);
	}
}

