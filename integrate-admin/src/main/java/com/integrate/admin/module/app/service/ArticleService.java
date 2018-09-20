package com.integrate.admin.module.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.exception.BusinessException;
import com.integrate.model.Article;

@Service("articleService")
public class ArticleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public Article insertArticle(Article article) throws Exception {
		if (article != null) {
			int count = (int) dao.save("ArticleMapper.insertArticle", article);
			if(count == 1){
				return article;
			}
			throw new BusinessException("Insert the article new failure.");
		}
		return null;
	}
	
	public Article updateArticle(Article article) throws Exception {
		if (article != null) {
			int count = (int)dao.update("ArticleMapper.updateArticle", article);
			if(count == 1){
				return article;
			}
			throw new BusinessException("Update the article new failure.");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Article> getArticles() throws Exception {
		return (List<Article>) dao.findForList("ArticleMapper.getArticles", null);
	}
	
	public Article getArticle(Long id) throws Exception {
		return (Article) dao.findForObject("ArticleMapper.getArticle", id);
	}
	
	public int deleteArticle(Long id) throws Exception {
		return (int) dao.delete("ArticleMapper.deleteArticle", id);
	}

}
