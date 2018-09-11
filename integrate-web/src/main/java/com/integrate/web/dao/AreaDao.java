package com.integrate.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.integrate.web.common.AppDbService;

@Component
public class AreaDao {
	@Autowired
	private AppDbService jdbc;

	/**
	 * 获取城市区域信息
	 * 
	 * @param cityVersion
	 */
	public List<Map<String, Object>> getAreaList(int cityVersion) {
		String sql = "select * from t_province_city_area where data_version = ? order by area_code ";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityVersion);
		if (list == null) {
			list = Lists.newArrayList();
		}
		return list;
	}

	/**
	 * 获取省份信息
	 * 
	 * @param cityVersion
	 */
	public List<Map<String, Object>> getProvinceList(int cityVersion) {
		String sql = "select distinct province_code,province_name,province_pinyin from t_province_city_area where data_version = ? order by province_pinyin";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityVersion);
		if (list == null) {
			list = Lists.newArrayList();
		}
		return list;
	}

	/**
	 * 获取城市信息
	 * 
	 * @param cityVersion
	 */
	public List<Map<String, Object>> getCityList(int cityVersion) {
		String sql = "select distinct city_code,view_cityname,province_code from t_province_city_area where data_version = ?  order by city_code ";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityVersion);
		if (list == null) {
			list = Lists.newArrayList();
		}
		return list;
	}

	public Map<String, Object> getArea(int cityCode, int areaCode) {
		String sql = "SELECT DISTINCT province_name,city_name,area_name FROM t_province_city_area WHERE city_code=? AND  area_code=?";

		Map<String, Object> list = jdbc.queryForMap(sql, cityCode, areaCode);

		return list;
	}
}
