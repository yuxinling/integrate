package com.integrate.admin.module.city.model;


public class ProvinceInfo {
	
	private int provinceCode;
	
	private String provinceName;

	public ProvinceInfo() {
	}

	public ProvinceInfo(int provinceCode, String provinceName) {
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
	}

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

}
