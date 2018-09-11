package com.integrate.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserBaseInfo {

	private long userId;
	private String mobile;
	private String nickname;
	private int proxy;
	private String token;
	@JsonIgnore
	private int areaCode;
	@JsonIgnore
	private int cityCode;
	
	private String area;
	
	private int isBond;
	
	
	
	
	public int getIsBond() {
		return isBond;
	}
	public void setIsBond(int isBond) {
		this.isBond = isBond;
	}
	public int getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getProxy() {
		return proxy;
	}
	public void setProxy(int proxy) {
		this.proxy = proxy;
	}
	@Override
	public String toString() {
		return "UserBaseInfo [userId=" + userId + ", mobile=" + mobile + ", nickname=" + nickname + ", proxy=" + proxy
				+ ", token=" + token + ", areaCode=" + areaCode + ", cityCode=" + cityCode + ", area=" + area + "]";
	}
	
	
	
	
}
