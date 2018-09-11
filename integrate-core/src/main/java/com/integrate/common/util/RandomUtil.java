package com.integrate.common.util;

public class RandomUtil {
	
	/**
	 * 随机n位字符串
	 * @param size
	 * @return
	 */
	public static String randomChar(int size) {
		String result="";  
        for(int i=0;i<size;i++){  
            int intVal=(int)(Math.random()*26+97);  
            result=result+(char)intVal;  
        }  
        return result;
	}
	
	/**
	 * 随机n位数字
	 * @return
	 */
	public static String randomNum(int size) {
		return String.valueOf((int)((Math.random()*9+1)*Math.pow(10, size-1)));
	}

}
