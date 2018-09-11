package com.integrate.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrate.enums.SysMsgEnumType;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.AppService;

@Controller
public class AppVersionController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AppService appService;
	
	@RequestMapping(value = UrlCommand.app_version, method = RequestMethod.POST)
	@ResponseBody
	public void getAppVersion(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> version = appService.getVserion();
		
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, version);
		
	}
	
}
