package com.integrate.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 对象拷贝工具类
 */
public class BeanUtil {
	private static Logger log = LoggerFactory.getLogger(BeanUtil.class);
	
	private static  BeanUtilsBean beanUtilsBean;
	private BeanUtil(){}
	static{
		if(beanUtilsBean == null){
			beanUtilsBean = BeanUtilsBean.getInstance();
		}
	}
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public static Object buildBean(Map map, Class clazz){
	    	if(map == null){
	    		return null;
	    	}
	    	Object bean = null;
	    	try{
	    		bean = clazz.newInstance();
	    		PropertyDescriptor[] pds = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(clazz);
	    		for(PropertyDescriptor pd : pds){
	    			String fieldName = pd.getName();
	    			if(map.containsKey(fieldName)){
	    				Object mapValue = map.get(fieldName);
	    				Class beanType = pd.getPropertyType();
	    				Object beanValue = mapValue;
	    				
	    				
	        			if(beanType.isEnum()){
	        				if(mapValue != null){
	              				if(mapValue instanceof String){
	            					if(String.valueOf(mapValue).matches("\\d+")){//数字型
	            						mapValue = Integer.parseInt(String.valueOf(mapValue));
	        	                    	int intValue = (Integer)mapValue;
	        	                    	
	        	                    	Method method = beanType.getMethod("values");
	        	                    	Object[] enumValues = (Object[])method.invoke(beanType);
	        	                    	if(intValue >= 0 &&  intValue <  enumValues.length){
	        	                    		beanValue = enumValues[intValue];
	        	                    	} else {
	        	                    		continue;
	        	                    	}
	            					} else {//字符串标识的枚举值 
	        	                    	try{
	                						beanValue = Enum.valueOf(beanType, String.valueOf(mapValue));
	                					} catch (IllegalArgumentException e) {//是一个错误的值
	                						continue;
	        							}
	            					}
	            					
	            				} else if(mapValue instanceof Integer){//整型
	            					int intValue = (Integer)mapValue;
	            					Method method = beanType.getMethod("values");
	    	                    	Object[] enumValues = (Object[])method.invoke(beanType);
	    	                    	if(intValue >= 0 &&  intValue <  enumValues.length){
	    	                    		beanValue = enumValues[intValue];
	    	                    	} else {//超过了枚举的int值范围
	    	                    		continue;
	    	                    	}
	            				}
	        				}
	        			} else if(beanType.equals(java.util.Date.class)){
	        				if(mapValue != null){
	        					if(mapValue instanceof String){
	        						try{
	        							DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        							beanValue = format.parse(String.valueOf(mapValue));
	        						} catch (ParseException e) {
										log.error("BeanUtil buildBean string 转 Date 出错!");
										continue;
									}
	        						
	        					}
	        				}
	        			}
	        			
	        			beanUtilsBean.copyProperty(bean, fieldName, beanValue);
	        				 
	    			}
	    			
	    		}
	    		return bean;
	    	}catch (Throwable e) {
	    		log.error("BeanUtil 根据map创建bean出错:", e);
	    		throw new RuntimeException(e);
			}
	    }
	    
	
	
    

	/**
	 * 对象拷贝
	 * @param dest
	 * @param orig
     */
	public static void copyProperties(Object dest, Object orig) {
		try {
			if (orig != null && dest != null) {
				BeanUtils.copyProperties(dest, orig);
			}
		} catch (Exception e) {
			log.error("copyProperties出错:", e);
			throw new RuntimeException(e);
		}
	}
    
    
    
    
}
