package com.integrate.web.common;

/**
 * 错误码
 * @author ruan
 *
 */
public enum ErrorCode {
	/************************系统级错误******************************/
	/**
	 * 参数错误(1)
	 */
	paramError(1, "参数错误"),

	sysError(2, "系统未知错误"),
	
	/************************业务逻辑错误******************************/
	/*作品模块1000*/
	/**
	 * 作品不存在(1001)
	 */
	opusNotExists(1001, "作品不存在"),
	/*玩家模块2000*/
	/**
	 * 歌手不存在(2001)
	 */
	playerNotExists(2001, "歌手不存在");

	private int code;
	private String msg;

	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg.trim();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
