package com.integrate.web.model;

public class Recharge {

	private long id;
	private int cityCode;
	private int areaCode;
	private long userId;
	private int money;
	private int type;
	private long time;
	private long tradeNo;
	private int state;
	private String txnTime;
	private int exchangeStatus;
	
	//新加记录
	
	private int fixedIntegrate;
	private int giveIntegrate;
	private long proxyUserId;
	private int residualFixedIntegral;
	private int residualGiveIntegral;
	private int isScan;
	private int isFreeze;
	
	
	
	
	
	
	

	public int getFixedIntegrate() {
		return fixedIntegrate;
	}

	public void setFixedIntegrate(int fixedIntegrate) {
		this.fixedIntegrate = fixedIntegrate;
	}

	public int getGiveIntegrate() {
		return giveIntegrate;
	}

	public void setGiveIntegrate(int giveIntegrate) {
		this.giveIntegrate = giveIntegrate;
	}

	public long getProxyUserId() {
		return proxyUserId;
	}

	public void setProxyUserId(long proxyUserId) {
		this.proxyUserId = proxyUserId;
	}

	public int getResidualFixedIntegral() {
		return residualFixedIntegral;
	}

	public void setResidualFixedIntegral(int residualFixedIntegral) {
		this.residualFixedIntegral = residualFixedIntegral;
	}

	public int getResidualGiveIntegral() {
		return residualGiveIntegral;
	}

	public void setResidualGiveIntegral(int residualGiveIntegral) {
		this.residualGiveIntegral = residualGiveIntegral;
	}

	public int getIsScan() {
		return isScan;
	}

	public void setIsScan(int isScan) {
		this.isScan = isScan;
	}

	public int getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(int isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public int getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(int exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}
}
