package com.integrate.enums;

public interface IMsgEnumType {
	/**
	 * 获取消息代码
	 * @return
	 */
	public int getCode();

	/**
	 * 获取消息描述
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 获取当前项名称
	 * @return
	 */
	public String getProject();

}
