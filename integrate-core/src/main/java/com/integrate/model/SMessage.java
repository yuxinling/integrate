package com.integrate.model;

import com.integrate.common.util.JsonUtils;


public class SMessage {
	private int code;     //状态码
	private String msg;     //状态描述
	private Object data;    //Json 内容

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}	
	
	public String toString(){
		return JsonUtils.toJsonString(this);
	}
}
