package com.integrate.web.model;

public class AreaCharge {

	private String name;
	private String mobile;
	
	private long rechargeId;
	private int money;
	private long time;
	private long tradeNo;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public long getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(long rechargeId) {
		this.rechargeId = rechargeId;
	}
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(long tradeNo) {
		this.tradeNo = tradeNo;
	}

}
