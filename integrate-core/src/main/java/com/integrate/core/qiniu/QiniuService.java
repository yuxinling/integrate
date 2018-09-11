package com.integrate.core.qiniu;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.integrate.common.util.JsonUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Component
public class QiniuService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${qiniu.accessKey}")
    private String ACCESS_KEY;
	
	@Value("${qiniu.secretKey}")
    private String SECRET_KEY;

	private Auth auth;
	
	private UploadManager uploadManager ;

	@PostConstruct
	public void  init() {
		auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		uploadManager= new UploadManager();
	}

	public String getUpToken(String bucketname) {
		return getUpToken(bucketname, null);
	}

	public String getUpToken(String bucketname, String key) {
		if (StringUtils.isBlank(key)) {
			return auth.uploadToken(bucketname);
		}

		return auth.uploadToken(bucketname, key);
	}

	/**
	 * 七牛图片上传
	 * @param data
	 * @param bucketname
     * @return  返回图片资源地址key
     */
	public String upload(byte[] data, String bucketname) {
		//创建上传对象
		try {
			//调用put方法上传
			Response res = uploadManager.put(data, null, getUpToken(bucketname));
			QiniuUploadResp resp = JsonUtils.readValue(res.bodyString(), QiniuUploadResp.class);

			return resp.getKey();
		} catch (QiniuException e) {
			Response r = e.response;
			logger.error("error while upload Qiniu response:{}", r.toString(), e);
		}

		return null;
	}

	/**
	 * 七牛文件上传
	 * @param data
	 * @param bucketname
     * @return  返回文件
     */
	public String uploadFile(File file, String bucketname) {
		//创建上传对象
		try {
			//调用put方法上传
			Response res = uploadManager.put(file, null,  getUpToken(bucketname));
			QiniuUploadResp resp = JsonUtils.readValue(res.bodyString(), QiniuUploadResp.class);

			logger.warn("resp.gethash():[{}],resp.getKey():[{}]",resp.getHash(),resp.getKey());
			return resp.getKey();
		} catch (QiniuException e) {
			Response r = e.response;
			logger.error("error while upload Qiniu response:{}", r.toString(), e);
		}

		return null;
	}

}
