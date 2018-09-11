package com.integrate.enums;

import java.util.HashMap;
import java.util.Map;

public enum PlatFormType {

    /** 1-ios */
    IOS(1),

    /** 2-android */
    ANDROID(2)

    ;

    private int id;

    private static Map<Integer, PlatFormType> map = new HashMap<Integer, PlatFormType>();
    static {
        for(PlatFormType type : values()) {
            map.put(type.getId(), type);
        }
    }

    PlatFormType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PlatFormType valuesOf(int id) {
        return map.get(id);
    }
}
