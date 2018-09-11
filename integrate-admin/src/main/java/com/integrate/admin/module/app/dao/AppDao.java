package com.integrate.admin.module.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.admin.common.AppDbService;
import com.integrate.admin.module.app.model.AppInfo;

@Component
public class AppDao {

	@Autowired
	private AppDbService jdbc;
	public AppInfo getApp(){
		String sql="SELECT * FROM t_app_version LIMIT 1";
		return jdbc.queryT(sql, appMapper);
	}
	
	
	public boolean update(String url,String version){
		long time=System.currentTimeMillis();
		String sql="UPDATE t_app_version SET url=?,VERSION=?,TIME=?";
		return jdbc.update(sql, url,version,time);
	}
	
	RowMapper<AppInfo> appMapper=new RowMapper<AppInfo>() {
		
		@Override
		public AppInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			AppInfo a=new AppInfo();
			a.setId(rs.getInt("id"));
			a.setTime(rs.getLong("time"));
			a.setUrl(rs.getString("url"));
			a.setVersion(rs.getString("version"));
			return a;
		}
	};
}
