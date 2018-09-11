package com.integrate.admin.module.rechrage.model;

public class RechargeOplogResp {
	private Long id;
	private Long rid;
	private Integer beforeMoney;
	private Integer opType;
	private Integer changeMoney;
	private String opUser;
	private String time;
	private String rechargeTime;
	private Long userId;
	private String nickName;
	private String mobile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Integer getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(Integer beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

	
	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Integer getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(Integer changeMoney) {
		this.changeMoney = changeMoney;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
}
