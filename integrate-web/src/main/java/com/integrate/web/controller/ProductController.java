package com.integrate.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrate.common.util.StringUtil;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.model.Product;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.ProductService;

@Controller
public class ProductController {
	
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value = UrlCommand.product, method = RequestMethod.GET)
	@ResponseBody
	public void getProducts(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> products = productService.getProducts();
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, products);
	}
	
	@RequestMapping(value = UrlCommand.product_search, method = RequestMethod.GET)
	@ResponseBody
	public void searchProducts(HttpServletRequest request, HttpServletResponse response){

		String keyword = StringUtil.getString(request.getParameter("kw"));
		if(StringUtils.isBlank(keyword)){
			Message.writeError(response, SysMsgEnumType.SEARCH_FAIL);
		}
		List<Map<String,Object>> products = productService.searchProducts(keyword);
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, products);
	}
	
	@RequestMapping(value = UrlCommand.product_detail, method = RequestMethod.GET)
	@ResponseBody
	public void getProduct(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") Long id){
		Product product = productService.getProduct(id);
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, product);
	}

}
