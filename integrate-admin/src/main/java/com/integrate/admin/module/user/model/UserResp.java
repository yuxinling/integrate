package com.integrate.admin.module.user.model;

public class UserResp {

	private Long userId;

	private String mobile;

	private String nickName;

	private String area;

	private int isAreaProxy;

	private Integer integrate;
	
	private int hasproxy;
	
	private int isFreeze;
	
	private int isFreezeAll;
	
	private int giveDay;
	
	private int fixDay;
	
	private int rechrageTotal;
	
	private int exchangeTotal;
	
	private int withdrawalsTotal;
	
	private String time;
	
	
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getRechrageTotal() {
		return rechrageTotal;
	}

	public void setRechrageTotal(int rechrageTotal) {
		this.rechrageTotal = rechrageTotal;
	}

	public int getExchangeTotal() {
		return exchangeTotal;
	}

	public void setExchangeTotal(int exchangeTotal) {
		this.exchangeTotal = exchangeTotal;
	}

	public int getWithdrawalsTotal() {
		return withdrawalsTotal;
	}

	public void setWithdrawalsTotal(int withdrawalsTotal) {
		this.withdrawalsTotal = withdrawalsTotal;
	}

	public int getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(int isFreeze) {
		this.isFreeze = isFreeze;
	}

	public int getHasproxy() {
		return hasproxy;
	}

	public void setHasproxy(int hasproxy) {
		this.hasproxy = hasproxy;
	}

	public Long getUserId() {
		return userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getIsAreaProxy() {
		return isAreaProxy;
	}

	public void setIsAreaProxy(int isAreaProxy) {
		this.isAreaProxy = isAreaProxy;
	}

	public Integer getIntegrate() {
		return integrate;
	}

	public void setIntegrate(Integer integrate) {
		this.integrate = integrate;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getIsFreezeAll() {
		return isFreezeAll;
	}

	public void setIsFreezeAll(int isFreezeAll) {
		this.isFreezeAll = isFreezeAll;
	}

	public int getGiveDay() {
		return giveDay;
	}

	public void setGiveDay(int giveDay) {
		this.giveDay = giveDay;
	}

	public int getFixDay() {
		return fixDay;
	}

	public void setFixDay(int fixDay) {
		this.fixDay = fixDay;
	}
	
}
