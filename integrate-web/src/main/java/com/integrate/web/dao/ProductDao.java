package com.integrate.web.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.model.Product;
import com.integrate.web.common.AppDbService;

@Component
public class ProductDao {
	  private static final String DEFAULT_CHARSET = "utf-8"; 
	@Autowired
	private AppDbService jdbc;
	
	RowMapper<Product> rowMapper = new RowMapper<Product>() {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product r = new Product();
			r.setId(rs.getLong("id"));
			r.setName(rs.getString("name"));
			r.setThumbnail(rs.getString("thumbnail"));
			r.setSort(rs.getInt("sort"));
			r.setIntegrate(rs.getInt("integrate"));
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
	
	public List<Map<String,Object>> getProducts(){
		String sql="select id,name,thumbnail,integrate from t_product order by sort asc" ;
		return jdbc.queryForList(sql,new Object[]{});
	}
	
	public List<Map<String,Object>> getProducts(String keyword){
		if(StringUtils.isNotBlank(keyword)){
			keyword = "%"+keyword+"%";
		}
		String sql="select id,name,thumbnail,integrate from t_product where name like ? order by sort asc" ;
		return jdbc.queryForList(sql,new Object[]{keyword});
	}
	
	public Product getProduct(Long id){
		String sql="select * from t_product where id = ?" ;
		return jdbc.queryT(sql, rowMapper, id);
	}
}
