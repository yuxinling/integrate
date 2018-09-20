package com.integrate.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrate.enums.SysMsgEnumType;
import com.integrate.model.Article;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.service.ArticleService;

@Controller
public class ArticleNewController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = UrlCommand.article_titles, method = RequestMethod.GET)
	@ResponseBody
	public void getArticleTitles(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> titles = articleService.getArticleTitles();
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, titles);
	}
	
	@RequestMapping(value = UrlCommand.article_detail, method = RequestMethod.GET)
	@ResponseBody
	public void getArticleDetail(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") Long id){
		Article article = articleService.getArticle(id);
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, article);
	}
}
