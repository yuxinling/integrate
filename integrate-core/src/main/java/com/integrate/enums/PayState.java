package com.integrate.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付状态枚举
 */
public enum PayState {

    /** 0-未支付 */
    NONE(0),
    /** 1-完成支付 */
    DONE(1),
    /** 2-支付失败 */
    FAIL(2),
    ;

    private int id;

    private static Map<Integer, PayState> map = new HashMap<Integer, PayState>();
    static {
        for(PayState type : values()) {
            map.put(type.getId(), type);
        }
    }

    PayState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PayState valuesOf(int id) {
        return map.get(id);
    }
}
