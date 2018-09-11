package com.integrate.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付类型枚举
 */
public enum PayType {

    /** 0-余额 */
    BALANCE(0),

    /** 1-支付宝 */
    ALIPAY(1),

    /** 2-微信APP */
    WXAPP(2),

    /** 3-appstore */
    APPSTORE(3),

    ;

    private int id;

    private static Map<Integer, PayType> map = new HashMap<Integer, PayType>();
    static {
        for(PayType type : values()) {
            map.put(type.getId(), type);
        }
    }

    PayType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PayType valuesOf(int id) {
        return map.get(id);
    }
}
