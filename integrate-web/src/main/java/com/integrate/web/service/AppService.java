package com.integrate.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.web.dao.AppDao;
@Service
public class AppService {

	@Autowired
	private AppDao appDao;

	public Map<String, Object> getVserion() {

		Map<String, Object> version = appDao.getVersion();
		return version;
	}
}
