package com.integrate.env;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertiesHelper {
	private static Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);
	private static Properties props;
	static {
		try {
			props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("integrate.properties"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static String getProp(String key) {
		return props.getProperty(key);
	}
}
