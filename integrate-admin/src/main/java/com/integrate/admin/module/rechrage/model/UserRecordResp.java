package com.integrate.admin.module.rechrage.model;

public class UserRecordResp {

	private Long id;

	private Integer money;


	private String  time;

	private Long tradeNo;

	private long proxyUserId;
	private int artificial;
	
	private String nickName;

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getProxyUserId() {
		return proxyUserId;
	}

	public void setProxyUserId(long proxyUserId) {
		this.proxyUserId = proxyUserId;
	}

	public int getArtificial() {
		return artificial;
	}

	public void setArtificial(int artificial) {
		this.artificial = artificial;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}


	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(Long tradeNo) {
		this.tradeNo = tradeNo;
	}
}
