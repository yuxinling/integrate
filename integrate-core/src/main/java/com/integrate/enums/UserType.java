package com.integrate.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型枚举
 */
public enum UserType {

    /** 1 - 男 */
    MAIL(1),

    /** 0  - 女 */
    GIRL(0),

    ;

    private int id;

    private static Map<Integer, UserType> map = new HashMap<Integer, UserType>();
    static {
        for(UserType type : values()) {
            map.put(type.getId(), type);
        }
    }

    UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserType valuesOf(int type) {
        return map.get(type);
    }
}
