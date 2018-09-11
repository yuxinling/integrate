package com.integrate.common.util;
/**
 */
public interface R {
	
	public final int SUCCESS = 0;
	
	
	public final int VERIFY_LACK = 1; 
	
	public final int MD5_LACK = 2;
	
	public final int MD5_UNCHECK = 3;
	
	/** 非法请求, 如url不存在 */
    public final int INVALID_REQUEST = 4;
    
	
	/** 登录参数错误 */
	public final int LOGIN_PARAM_LACK = 6;
	
	public final int MD5_MUST_CHECK = 7;
	
	
	public final int VERIFY_ERROR = 10;
	
	
	
	
	
	
	
	
	/** 防刷预留错误码*/
	public final int SERVICE_BUSY = 100;
	
	
	
	
	/** 操作不成功，冗余表示。 */
	public final int FAIL = 1001;
	
	/** 参数检验不符要求 */
	public final int PARAM_LACK = 1002;
	
	/** 部分参数值为空 */
	public final int PARAM_BLANK = 1003;
	
	/** 频繁注册 */
	public final int REGISTER_FREQUENT = 1004;
	

	/** 注册验证码错误 **/
	public final int INVAILD_RESGISTER_CODE = 2001;

	/** 手机号已被注册 **/
	public final int REPEAT_RESGISTER_MOBILE = 2002;

	/** 密码为空 **/
	public final int PWD_EMPTY = 2003;
	
	/**操作失败**/
	public final int SYS_FAIL=2004;
	
	
	public final int SYSTEM_ERROR = 9000;//系统错误
	
	
}
