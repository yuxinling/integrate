package com.integrate.admin.module.user.model;

public class User {
	 private Long userId;

	    private String mobile;

	    private String loginPwd; 

	    private Long createTime;

	    private Long modifyTime;

	    private String nickName;

	    private Integer areaCode;

	    private Integer cityCode;

	    private int isAreaProxy;

	    private Integer integrate;

	    private String recommendPerson;

	    private String tradePwd;
	    
	    private int isFreeze;
	    
	    private int isFreezeAll;
	    
	    private int giveDay;
	    
	    private int fixDay;
	    

	    public int getIsFreeze() {
			return isFreeze;
		}

		public void setIsFreeze(int isFreeze) {
			this.isFreeze = isFreeze;
		}

		public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }

	    public String getMobile() {
	        return mobile;
	    }

	    public void setMobile(String mobile) {
	        this.mobile = mobile == null ? null : mobile.trim();
	    }

	    public String getLoginPwd() {
	        return loginPwd;
	    }

	    public void setLoginPwd(String loginPwd) {
	        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
	    }

	    public Long getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Long createTime) {
	        this.createTime = createTime;
	    }

	    public Long getModifyTime() {
	        return modifyTime;
	    }

	    public void setModifyTime(Long modifyTime) {
	        this.modifyTime = modifyTime;
	    }

	    public String getNickName() {
	        return nickName;
	    }

	    public void setNickName(String nickName) {
	        this.nickName = nickName == null ? null : nickName.trim();
	    }

	    public Integer getAreaCode() {
	        return areaCode;
	    }

	    public void setAreaCode(Integer areaCode) {
	        this.areaCode = areaCode;
	    }

	    public Integer getCityCode() {
	        return cityCode;
	    }

	    public void setCityCode(Integer cityCode) {
	        this.cityCode = cityCode;
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

	    public String getRecommendPerson() {
	        return recommendPerson;
	    }

	    public void setRecommendPerson(String recommendPerson) {
	        this.recommendPerson = recommendPerson == null ? null : recommendPerson.trim();
	    }

	    public String getTradePwd() {
	        return tradePwd;
	    }

	    public void setTradePwd(String tradePwd) {
	        this.tradePwd = tradePwd == null ? null : tradePwd.trim();
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