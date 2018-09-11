package com.integrate.web.controller;

import java.util.List;

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
import com.integrate.web.model.ProvinceCityAreaInfo;
import com.integrate.web.service.AreaService;

@Controller
public class AreaController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AreaService areaService;
	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.city_arealist,method = RequestMethod.POST)
	@ResponseBody
	public void cityAreaList(HttpServletRequest request,HttpServletResponse response) {
		List<ProvinceCityAreaInfo> list = areaService.getAreaList();
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, list);
	}
}
