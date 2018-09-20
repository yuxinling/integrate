package com.integrate.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.model.Article;
import com.integrate.web.dao.ArticleDao;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public List<Map<String,Object>> getArticleTitles() {
		return articleDao.getArticleTitles();
	}
	
	public Article  getArticle(Long id){
		return articleDao.getArticle(id);
	}
}
