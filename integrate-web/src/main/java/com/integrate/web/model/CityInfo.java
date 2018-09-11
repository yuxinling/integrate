package com.integrate.web.model;

import java.util.List;

public class CityInfo {
	private int cityCode;
	private String viewCityName;
	private List<AreaInfo> areaList;
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	
	public List<AreaInfo> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<AreaInfo> areaList) {
		this.areaList = areaList;
	}
	public String getViewCityName() {
		return viewCityName;
	}
	public void setViewCityName(String viewCityName) {
		this.viewCityName = viewCityName;
	}
	
	
}
