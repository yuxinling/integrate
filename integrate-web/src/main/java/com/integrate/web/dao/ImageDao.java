package com.integrate.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.model.Image;
import com.integrate.web.common.AppDbService;

@Component
public class ImageDao {
	@Autowired
	private AppDbService jdbc;
	
	RowMapper<Image> rmapper = new RowMapper<Image>() {
		@Override
		public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
			Image r = new Image();
			r.setId(rs.getLong("id"));
			r.setTitle(rs.getString("title"));
			r.setSrc(rs.getString("src"));
			r.setSort(rs.getInt("sort"));
			r.setRid(rs.getLong("rid"));
			r.setCreateTime(rs.getLong("create_time"));
			r.setUpdateTime(rs.getLong("update_time"));
			return r;
		}
	};
	
	public List<Image> getImages(Long rid){
		String sql="select * from t_image where rid = ?" ;
		return jdbc.query(sql, rmapper, rid);
	}
}
