package com.integrate.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册类型枚举
 */
public enum RegisterType {

    /** 1 - 手机 */
    MOBILE(1),

    /** 2 - 微信 */
    WX(2),

    /** 3 - QQ */
    QQ(3),

    ;

    private int id;

    private static Map<Integer, RegisterType> map = new HashMap<Integer, RegisterType>();
    static {
        for(RegisterType type : values()) {
            map.put(type.getId(), type);
        }
    }

    RegisterType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RegisterType valuesOf(int id) {
        return map.get(id);
    }
}
