package com.integrate.web.common;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrate.model.SMessage;






public class Message {
	private final static Logger logger = LoggerFactory.getLogger(Message.class);

	public final static void writeError(HttpServletResponse response, com.integrate.enums.IMsgEnumType msgEnumType) {
		try {
			SMessage sm = new SMessage();
			sm.setCode(msgEnumType.getCode());
			sm.setMsg(msgEnumType.getMessage());
			String smJson = com.integrate.common.util.JsonUtils.toJsonString(sm);
			ServletOutputStream output = response.getOutputStream();
			response.setHeader("charset", "UTF-8");
			response.setContentType("application/json");
			output.write(smJson.getBytes(Charsets.UTF_8));
			output.flush();
			output.close();
			if (logger.isInfoEnabled()) {
				logger.info("write: {}", smJson);
			}
		} catch (IOException  e) {
			logger.warn("", e);
		}
	}
	
	public final static void writeMsg(HttpServletResponse response,com.integrate.enums.IMsgEnumType msgEnumType, Object data) {
		try {
			SMessage sm = new SMessage();
			if(data!=null){
				sm.setData(data);	
			}
			sm.setCode(msgEnumType.getCode());
			sm.setMsg(msgEnumType.getMessage());
			String smJson = com.integrate.common.util.JsonUtils.toJsonString(sm);
			ServletOutputStream output = response.getOutputStream();
			response.setHeader("charset", "UTF-8");
			response.setContentType("application/json");
			output.write(smJson.getBytes(Charsets.UTF_8));
			output.flush();
			output.close();
			if (logger.isInfoEnabled()) {
				logger.info("write: {}", smJson);
			}
		} catch (Exception e) {
			logger.warn("", e);
		}
	}
	
	
	public final static void writeSuccess(HttpServletResponse response) {
		try {
			SMessage sm = new SMessage();
			sm.setCode(0);
			sm.setMsg("");
			String smJson = com.integrate.common.util.JsonUtils.toJsonString(sm);
			ServletOutputStream output = response.getOutputStream();
			response.setHeader("charset", "UTF-8");
			response.setContentType("application/json");
			output.write(smJson.getBytes(Charsets.UTF_8));
			output.flush();
			output.close();
			if (logger.isInfoEnabled()) {
				logger.info("write: {}", smJson);
			}
		} catch (Exception e) {
			logger.warn("", e);
		}
	}
	
	
}
