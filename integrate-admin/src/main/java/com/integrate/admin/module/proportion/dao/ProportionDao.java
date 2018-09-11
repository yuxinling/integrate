package com.integrate.admin.module.proportion.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.integrate.admin.common.AppDbService;

@Component
public class ProportionDao {

	@Autowired
	private AppDbService jdbc;

	public Map<String, Object> getProportion() {
		String sql = "SELECT `fixed`,give FROM t_proportion LIMIT 1";
		return jdbc.queryForMap(sql);
	}

	public boolean Update(int fixed, int give) {
		long time = System.currentTimeMillis();
		String sql = "UPDATE t_proportion SET FIXED=?,give=? ,TIME=?";
		return jdbc.update(sql, fixed, give, time);

	}
}
