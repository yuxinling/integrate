package com.integrate.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.IntegtateService;

@Controller
public class UpdateDataController {

	@Autowired
	private IntegtateService integtateService;
	
	/*@RequestMapping(value = UrlCommand.update_data, method = RequestMethod.POST)
	@ResponseBody
	public void updateData(HttpServletRequest request, HttpServletResponse response) {
		integtateService.updateData();
		Message.writeSuccess(response);

	}*/
}
