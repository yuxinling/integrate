package com.integrate.enums;

/**
 * 七牛对应的空间名
 */
public enum QiniuBucket {

	/**
	 * 头像模块
     */
	HEAD_IMG("ykzbhd", "http://oflheqgex.bkt.clouddn.com/");

	QiniuBucket(String bucket, String domain) {
		this.bucket = bucket;
		this.domain = domain;
	}

	private String bucket;
	private String domain;

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
