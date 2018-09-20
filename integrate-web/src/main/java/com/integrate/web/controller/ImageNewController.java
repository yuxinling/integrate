package com.integrate.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrate.enums.SysMsgEnumType;
import com.integrate.model.Image;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.ImageService;

@Controller
public class ImageNewController {
	@Autowired
	private ImageService ImageService;
	
	@RequestMapping(value = UrlCommand.news_images, method = RequestMethod.GET)
	@ResponseBody
	public void getImageNews(HttpServletRequest request, HttpServletResponse response){
		List<Image> images = ImageService.getImages(0L);
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, images);
	}

}
