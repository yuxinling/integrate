package com.integrate.web.model;

import java.util.List;


public class ProvinceCityAreaInfo {
private int provinceCode;
	
	private String provinceName;
	
	private List<CityInfo> cityList;
	
	public int getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public List<CityInfo> getCityList() {
		return cityList;
	}
	public void setCityList(List<CityInfo> cityList) {
		this.cityList = cityList;
	}
}
