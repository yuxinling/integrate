package com.integrate.web.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.model.Article;
import com.integrate.web.common.AppDbService;

@Component
public class ArticleDao {
	  private static final String DEFAULT_CHARSET = "utf-8"; 
	@Autowired
	private AppDbService jdbc;
	
	RowMapper<Article> rowMapper = new RowMapper<Article>() {
		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article r = new Article();
			r.setId(rs.getLong("id"));
			r.setTitle(rs.getString("title"));
			r.setCreateTime(rs.getLong("create_time"));
			r.setUpdateTime(rs.getLong("update_time"));
			
			Blob blob = rs.getBlob("detail");
			byte[] returnValue = null;
			if (null != blob) {
				returnValue = blob.getBytes(1, (int) blob.length());
			}
			try {
				// ###把byte转化成string
				if (returnValue == null) {
					r.setDetail(null);
				}
				r.setDetail(new String(returnValue, DEFAULT_CHARSET));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Blob Encoding Error!");
			}
			
			return r;
		}
	};
	
	public List<Map<String,Object>> getArticleTitles(){
		String sql="select id,title from t_article" ;
		return jdbc.queryForList(sql,new Object[]{});
	}
	
	public Article getArticle(Long id){
		String sql="select * from t_article where id = ?" ;
		return jdbc.queryT(sql, rowMapper, id);
	}
}
