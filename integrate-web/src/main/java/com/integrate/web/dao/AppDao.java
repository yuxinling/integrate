package com.integrate.web.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.integrate.web.common.AppDbService;

@Component
public class AppDao {

	@Autowired
	private AppDbService jdbc;
	
	public Map<String, Object> getVersion(){
		String sql="SELECT version FROM t_app_version LIMIT 1  " ;
		return jdbc.queryForMap(sql);
	}
}
