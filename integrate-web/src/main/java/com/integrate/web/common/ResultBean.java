package com.integrate.web.common;

import com.integrate.common.util.DateUtil;


public class ResultBean {
	private long servertime = System.currentTimeMillis()/1000L;
	private int status = 1;//1成功 0 失败
	private String errorcode;
	private Object data;

	public ResultBean(int status, String errorcode, Object data) {
		this.status = status;
		this.errorcode = errorcode;
		this.data = data;
	}

	public ResultBean() {

	}

	public long getServertime() {
		return servertime;
	}

	public void setServertime(long servertime) {
		this.servertime = servertime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
