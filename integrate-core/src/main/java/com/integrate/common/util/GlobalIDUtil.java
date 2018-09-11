package com.integrate.common.util;

public class GlobalIDUtil {
	public final static long START_TIME = 1262275200000L;

    public static long getInitTimestampFromID(long id) {
    	long time = id>>22 ;
        return time + START_TIME;
    }


    public static long createID(long timestamp) {
        if (timestamp<START_TIME){
            throw new IllegalArgumentException("起始时间不能早于 2010-01-01 00:00:00");
        }
        return ((timestamp-START_TIME)<<22) | 0;
    }

}
