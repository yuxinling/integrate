package com.integrate.admin.module.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.admin.common.AppDbService;
import com.integrate.admin.module.user.model.Area;
import com.integrate.admin.module.user.model.City;
import com.integrate.admin.module.user.model.Province;
import com.integrate.admin.module.user.model.UserBase;

@Component
public class UserDao {

	@Autowired
	private AppDbService jdbc;

	/*
	 * public List<Province> getProvince() { String sql =
	 * "SELECT DISTINCT province_name FROM t_province_city_area "; return
	 * jdbc.query(sql, pMapper); }
	 * 
	 * public List<City> getCity(int pcode) { String sql =
	 * "SELECT DISTINCT city_code,city_name FROM t_province_city_area WHERE province_code=?"
	 * ; return jdbc.query(sql, cMapper, pcode); }
	 * 
	 * public List<Area> getArea(int citycode) { String sql =
	 * "SELECT  area_code ,area_name FROM t_province_city_area WHERE city_code=? "
	 * ; return jdbc.query(sql, aMapper, citycode); }
	 */
	public Map<String, Object> getUserInfo(long userId) {
		String sql = "select mobile,nick_name,user_id ,area_code from t_user where user_id=? ";
		return jdbc.queryForMap(sql, userId);
	}

	public boolean hasProxy(int areaCode) {
		String sql = "SELECT COUNT(1) FROM t_user WHERE area_code=? AND is_area_proxy=1";
		return jdbc.queryInt(sql, areaCode) < 1;
	}

	public boolean updateUser(long userId, int isAreaProxy, int fixDay, int giveDay) {
		String sql = "UPDATE t_user SET is_area_proxy=?, fix_day = ?, give_day = ? WHERE USER_ID =? ";
		return jdbc.update(sql, isAreaProxy, fixDay, giveDay, userId);
	}

	public Map<String, Object> getUserArea(int areaCode) {
		String sql = "SELECT DISTINCT province_name,city_name,area_name FROM t_province_city_area WHERE  area_code=?";

		Map<String, Object> list = jdbc.queryForMap(sql, areaCode);

		return list;
	}

	public UserBase getUserBase(long userId) {
		String sql = "SELECT user_id, nick_name, mobile FROM t_user WHERE user_id=? ";
		return jdbc.queryT(sql, userBaseMapper, userId);
	}

	public List<UserBase> getUserBaseByArea(int areaCode) {
		String sql = "SELECT user_id, nick_name, mobile FROM t_user WHERE area_code=? AND is_area_proxy=1";
		return jdbc.query(sql, userBaseMapper, areaCode);
	}

	RowMapper<Province> pMapper = new RowMapper<Province>() {

		@Override
		public Province mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Province p = new Province();
			p.setProvinceCode(rs.getInt("province_code"));
			p.setProvinceName(rs.getString("province_name"));
			return p;
		}
	};

	RowMapper<City> cMapper = new RowMapper<City>() {

		@Override
		public City mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			City c = new City();
			c.setCityCode(rs.getInt("city_code"));
			c.setCityName(rs.getString("city_name"));
			return c;
		}
	};

	RowMapper<Area> aMapper = new RowMapper<Area>() {

		@Override
		public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Area a = new Area();
			a.setAreaCode(rs.getInt("area_code"));
			a.setAreaName(rs.getString("area_name"));
			return a;
		}
	};

	RowMapper<UserBase> userBaseMapper = new RowMapper<UserBase>() {

		@Override
		public UserBase mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			UserBase a = new UserBase();
			a.setUserId(rs.getLong("user_id"));
			a.setName(rs.getString("nick_name"));
			a.setMobile(rs.getString("mobile"));
			return a;
		}
	};

	/************************************
	 * 2.13,2.14修改
	 *********************************************/

	/**
	 * 冻结用户
	 * 
	 * @param isFreeze
	 * @param userId
	 * @return
	 */
	public boolean isfreeze(int isFreeze, long userId) {
		String sql = "UPDATE	t_user SET is_freeze =? WHERE USER_ID=?";
		return jdbc.update(sql, isFreeze, userId);

	}
	public boolean isfreezeAll(int isFreeze) {
		String sql = "UPDATE	t_user SET is_freeze_all = ?";
		return jdbc.update(sql, isFreeze);
	}

	public boolean rechrageFreeze(int isFreeze, long userId) {
		String sql = "UPDATE	t_rechrage  SET is_freeze =? WHERE type=0 and  USER_ID=? ";
		return jdbc.update(sql, isFreeze, userId);
	}

	public boolean rechrageFreezeByid(int isFreeze, long id) {
		String sql = "UPDATE	t_rechrage  SET is_freeze =? WHERE id=? ";
		return jdbc.update(sql, isFreeze, id);
	}

	// 充值
	public Map<String, Object> rechrageTotalV(int areaCode, long proxyUserId, long begin, long end) {
		String sql = "SELECT SUM(money) total FROM t_rechrage  WHERE TYPE=0  AND state=1  AND `time` BETWEEN ? AND ?";

		if (areaCode > 0) {
			sql += " AND area_code=" + areaCode;
		}
		if (proxyUserId > 0) {
			sql += " AND proxy_user_id=" + proxyUserId;
		}

		return jdbc.queryForMap(sql, begin, end);
	}

	// 退還的剩餘積分
	public Map<String, Object> returnTotal(int areaCode, long proxyUserId, long begin, long end) {
		String sql = "SELECT  SUM(residual_fixed_integral) fixedtotal,SUM(residual_give_integral) givetotal FROM t_rechrage  WHERE TYPE=0  AND state=1  AND `time` BETWEEN ? AND ? ";

		if (areaCode > 0) {
			sql += " AND area_code=" + areaCode;
		}
		if (proxyUserId > 0) {
			sql += " AND proxy_user_id=" + proxyUserId;
		}

		return jdbc.queryForMap(sql, begin, end);
	}

	// 兑换
	public Map<String, Object> exchangeTotalV(long begin, long end) {
		String sql = "SELECT SUM(money) total FROM t_rechrage  WHERE TYPE=1  AND state=1 AND `time` BETWEEN ? AND ?";
		return jdbc.queryForMap(sql, begin, end);
	}

	// 充值
	public Map<String, Object> rechrageTotal(long userId) {
		String sql = "SELECT SUM(money) total FROM t_rechrage  WHERE TYPE=0  AND state=1 AND USER_ID=?";

		return jdbc.queryForMap(sql, userId);
	}

	// 兑换
	public Map<String, Object> exchangeTotal(long userId) {
		String sql = "SELECT SUM(money) total FROM t_rechrage  WHERE TYPE=1  AND state=1 AND USER_ID=?";
		return jdbc.queryForMap(sql, userId);
	}

	// 提现
	public Map<String, Object> withdrawalsTotal(long userId) {
		String sql = "SELECT SUM(money) total FROM t_withdrawals  WHERE  state=1 AND USER_ID=? ";
		return jdbc.queryForMap(sql, userId);
	}

}
