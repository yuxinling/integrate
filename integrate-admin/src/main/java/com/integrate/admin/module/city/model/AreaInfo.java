package com.integrate.admin.module.city.model;

public class AreaInfo {
	private int areaCode;
	private String areaName;

	public AreaInfo() {
	}

	public AreaInfo(int areaCode, String areaName) {
		this.areaCode = areaCode;
		this.areaName = areaName;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
