package com.integrate.admin.module.rechrage.model;

public class Rechrage {

	private Long id;

	private Integer cityCode;

	private Integer areaCode;

	private Long userId;

	private Integer money;

	private Integer type;

	private Long time;

	private Long tradeNo;
	
	private int status;

	private int isFreeze;

	//id,money,TIME,trade_no,is_artificial,proxy_user_id
	private long proxyUserId;
	private int artificial;
	
	
	
	
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

	public int getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(int isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(Long tradeNo) {
		this.tradeNo = tradeNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
