package com.integrate.web.model;

public class RechargeResp {

	private long rechargeId;
	private int money;
	private long time;
	private long tradeNo;
	private int type;
	private Integer exchangeStatus;
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public Integer getExchangeStatus() {
		return exchangeStatus;
	}
	public void setExchangeStatus(Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}
	
}

