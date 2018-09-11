package com.integrate.web.model;

public class Record {

	private long id;
	private long userId;
	private long rechargeId;
	private long withdrawalsId;
	private String orderId;
	private int money;
	private int type;
	private long time;
	private int state;
	private long createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(long rechargeId) {
		this.rechargeId = rechargeId;
	}
	public long getWithdrawalsId() {
		return withdrawalsId;
	}
	public void setWithdrawalsId(long withdrawalsId) {
		this.withdrawalsId = withdrawalsId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
}
