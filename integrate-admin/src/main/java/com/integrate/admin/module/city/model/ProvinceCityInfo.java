package com.integrate.admin.module.city.model;

import java.util.List;

public class ProvinceCityInfo extends ProvinceInfo {
	
	private List<CityInfo> cityList;
	
	public List<CityInfo> getCityList() {
		return cityList;
	}
	public void setCityList(List<CityInfo> cityList) {
		this.cityList = cityList;
	}
	
}
