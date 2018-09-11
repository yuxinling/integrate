package com.integrate.enums;

/**
 */
public enum MsgEnumType implements IMsgEnumType {
    

    INVALID_REGISTER_CODE(2001, "验证码错误"),

    REPEAT_REGISTER_MOBILE(2002, "手机号已被注册，请返回上一层直接登录或找回密码"),

    PWD_EMPTY(2003, "请输入密码"),

    USER_NOT_EXISTS(2004, "账号或密码错误"),

    ERROR_PWD(2005, "账号或密码错误"),

    MOBILE_NO_REGISTER(2006, "该手机号未注册"),

    INVALID_RESET_CODE(2007, "验证码错误"),

    REGISTER_SMS_ERROR(2008, "发送注册验证码失败"),

    RESET_SMS_ERROR(2009, "发送重置密码验证码失败"),

    UPDATE_HEADIMG_ERROR(2010, "保存头像失败"),

    OLD_PWD_ERROR(2011, "原密码错误"),

    RESET_PWD_ERROR(2012, "修改密码失败"),

    REPEAT_NICKNAME(2013, "昵称已被注册"),

    UPDATE_NICKNAME_ERROR(2014, "修改昵称失败"),
    UPDATE_SEX_ERROR(2015, "修改性别失败"),

    
    
    
    
    
    
    


   

    ;

    MsgEnumType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getProject() {
        // TODO Auto-generated method stub
        return null;
    }

}


