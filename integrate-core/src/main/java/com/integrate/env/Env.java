package com.integrate.env;


public abstract class Env {
	private static String env = PropertiesHelper.getProp("env");
	
	/**
	 * @return true 外网生产环境
	 */
	public static boolean isProd() {
		//默认是生产环境，以免外网出错
		return env == null || "prod".equals(env);
	}
	
	/**
	 * @return true 开发人员自己的环境
	 */
	public static boolean isDev() {
		return "dev".equals(env);
	}
}
