package com.integrate.admin.module.city.model;

public class CityInfo {
	private int cityCode;
	private String viewCityName;

	public CityInfo() {
	}

	public CityInfo(int cityCode, String viewCityName) {
		this.cityCode = cityCode;
		this.viewCityName = viewCityName;
	}

	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public String getViewCityName() {
		return viewCityName;
	}
	public void setViewCityName(String viewCityName) {
		this.viewCityName = viewCityName;
	}
	
}
