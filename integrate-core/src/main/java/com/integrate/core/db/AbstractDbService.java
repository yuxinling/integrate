package com.integrate.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.integrate.common.util.StringUtil;

public abstract class AbstractDbService {
	
	public Logger log = LoggerFactory.getLogger(getClass());
	
	public JdbcTemplate jdbc;
	
	public <T> List<T> query(String sql, final RowMapper<T> rowMapper, Object... values) {
		final List<T> list = new ArrayList<T>();
		jdbc.query(sql, values, new ResultSetExtractor<List<T>>() {
			public List<T> extractData(ResultSet rs) {
				try {
					int i = 0;
					while (rs.next()) {
						list.add(rowMapper.mapRow(rs, i));
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("RowMapper-query error", e);
				}
				return list;
			}
		});
		return list;
	}
	public List<Map<String, Object>> queryList(String sql, Object... args) {
		try {
			return jdbc.queryForList(sql, args);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public <T> T queryT(String sql, final RowMapper<T> rowMapper, Object... values) {
		try {
			return jdbc.queryForObject(sql, rowMapper, values);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public <T> List<T> queryForList(String sql , Class<T> elementType , Object... args){
		try {
			return jdbc.queryForList(sql, elementType, args);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	

	public Map<String, Object> queryForMap(String sql, Object... args) {
		try {
			return jdbc.queryForMap(sql, args);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	
	public int queryInt(String sql, Object... value) {
		try {
			return StringUtil.getInt(jdbc.queryForObject(sql, value, Integer.class));
		} catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	
	
	public boolean update(String sql, Object... args) {
		int c = jdbc.update(sql, args);
		return c > 0;
	}
	public long insert(final String sql, final String key , final Object... args ){
		long result = -1L;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator psc  = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql,new String[] { key });
					if ( args != null) {
						for(int i=0; i<args.length;i++ ){
							ps.setObject(i+1, args[i]);
						}
					}
					return ps;
				}
			};
		result = jdbc.update(psc, keyHolder);
		return result>0?keyHolder.getKey().intValue():-1;
	}
	public int insert(final String sql , final Object... args ){
		int result = 0;
		PreparedStatementCreator psc  = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql);
					if ( args != null) {
						for(int i=0; i<args.length;i++ ){
							ps.setObject(i+1, args[i]);
						}
					}
					return ps;
				}
			};
		result = jdbc.update(psc);
		return result;
	}
	
	
	public int calStart(int page , int pagesize ) {
		if(page<1) {
			page=1;	
		}
		return (page-1)*pagesize;
	}
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return jdbc.queryForList( sql,args);
	}
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
		return jdbc.queryForObject(sql,requiredType,args);
	}

}
