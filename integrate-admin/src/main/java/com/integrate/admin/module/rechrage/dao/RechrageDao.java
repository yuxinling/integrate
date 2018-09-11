package com.integrate.admin.module.rechrage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.admin.common.AppDbService;
import com.integrate.admin.module.rechrage.model.Rechrage;
import com.integrate.admin.module.withdrawals.model.Withdrawals;

@Component
public class RechrageDao {

	@Autowired
	private AppDbService jdbc;

	public Map<String, Object> getArea(int areaCode) {
		String sql = "SELECT city_name ,area_name FROM t_province_city_area WHERE area_code =? ";
		Map<String, Object> map = jdbc.queryForMap(sql, areaCode);
		return map;
	}
	
	public Map<String, Object> getBaseUserInfo(long userId){
		String sql="SELECT mobile,nick_name,area_code,is_area_proxy FROM t_user WHERE USER_ID =?";
		
		return jdbc.queryForMap(sql,userId);
	}
	
	//获取用户充值
	public List<Rechrage> getRechrageByUserId(long userId){
		String sql="SELECT id,money,TIME,trade_no,is_artificial,proxy_user_id FROM t_rechrage  WHERE TYPE=0 AND  state=1 AND USER_ID=?";
		return jdbc.query(sql, rMapper, userId);
	}
	
	//获取用户兑换
	public List<Rechrage> getexchangeByUserId(long userId){
		String sql="SELECT id,money,TIME,trade_no,is_artificial,proxy_user_id FROM t_rechrage  WHERE TYPE=1 AND  state=1 AND USER_ID=?";
		return jdbc.query(sql, rMapper, userId);
	}
	
	//用户提现列表
	public List<Withdrawals> getWithdrawals(long userId){
		String sql="SELECT * FROM t_withdrawals  WHERE  USER_ID=? ";
		return jdbc.query(sql, wMapper, userId);
	}
	
	RowMapper<Rechrage> rMapper=new RowMapper<Rechrage>() {
		
		@Override
		public Rechrage mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Rechrage r=new Rechrage();
			r.setId(rs.getLong("id"));
			r.setMoney(rs.getInt("money"));
			r.setTime(rs.getLong("time"));
			r.setTradeNo(rs.getLong("trade_no"));
			r.setArtificial(rs.getInt("is_artificial"));
			r.setProxyUserId(rs.getLong("proxy_user_id"));
			return r;
		}
	};
	
	RowMapper<Withdrawals> wMapper=new RowMapper<Withdrawals>() {
		
		@Override
		public Withdrawals mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Withdrawals w=new Withdrawals();
			w.setId(rs.getLong("id"));
			w.setMoney(rs.getInt("money"));
			w.setState(rs.getInt("state"));
			w.setTime(rs.getLong("time"));
			w.setUserId(rs.getLong("user_id"));
			return w;
		}
	};
}
