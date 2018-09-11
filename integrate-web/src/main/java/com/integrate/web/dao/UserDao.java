package com.integrate.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.web.common.AppDbService;
import com.integrate.web.model.BankCardInfo;
import com.integrate.web.model.ProxyInfo;
import com.integrate.web.model.UserBaseInfo;

@Component
public class UserDao {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AppDbService jdbc;

	/**
	 * 判断手机号是否已注册
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean isMobileExists(String mobile) {
		String sql = "SELECT COUNT(1) FROM t_user WHERE mobile=? ";
		int result = jdbc.queryInt(sql, mobile);

		return result > 0;
	}

	public boolean userLogin(String mobile, String pwd) {
		String sql = "SELECT COUNT(1) FROM t_user WHERE mobile=? and login_pwd=? ";
		int result = jdbc.queryInt(sql, mobile, pwd);

		return result > 0;
	}

	public boolean userloginPWD(long userId, String pwd) {
		String sql = "SELECT COUNT(1) FROM t_user WHERE user_id=? and login_pwd=? ";
		int result = jdbc.queryInt(sql, userId, pwd);

		return result > 0;
	}

	public boolean usertradePWD(long userId, String tradepwd) {
		String sql = "SELECT COUNT(1) FROM t_user WHERE user_id=? and trade_pwd=? ";
		int result = jdbc.queryInt(sql, userId, tradepwd);

		return result > 0;
	}

	public UserBaseInfo getUserBaseInfo(String mobile) {
		String sql = "select mobile,nick_name,is_area_proxy,user_id ,area_code,city_code from t_user where mobile=? ";
		return jdbc.queryT(sql, baseMapper, mobile);
	}

	public UserBaseInfo getUserBaseInfoById(long id) {
		String sql = "select mobile,nick_name,is_area_proxy,user_id ,area_code,city_code from t_user where user_id=? ";
		return jdbc.queryT(sql, baseMapper, id);
	}

	public Map<String, Object> getUserIntegrate(long id) {
		String sql = "select integrate from t_user where user_id=? ";
		Map<String, Object> map = jdbc.queryForMap(sql, id);
		return map;
	}
	
	public Map<String, Object> getFreezeAll(long id) {
		String sql = "select is_freeze_all from t_user where user_id=? ";
		Map<String, Object> map = jdbc.queryForMap(sql, id);
		return map;
	}
	
	public Map<String, Object> getFreezeAndDays(long id) {
		String sql = "select is_freeze_all,fix_day,give_day from t_user where user_id=? ";
		Map<String, Object> map = jdbc.queryForMap(sql, id);
		return map;
	}

	public long insertUser(String mobile, String nickname, String regCode, int cityCode, int areaCode, String loginPwd,
			String tradePwd, String recommendPerson) {

		String sql = "INSERT INTO t_user " + "(`nick_name`,   "
				+ " `mobile`, `login_pwd`,`trade_pwd`,`recommend_person`, `create_time`, `modify_time`, "
				+ " `area_code`, `city_code`) " + " VALUES (?,?,?,?,?, ?,?,?,?) ";

		long userId = jdbc.insert(sql, "user_id", nickname, mobile, loginPwd, tradePwd, recommendPerson,
				System.currentTimeMillis(), System.currentTimeMillis(), areaCode, cityCode);

		return userId;
	}

	public boolean updatePwd(String mobile, String pwd) {
		String sql = "UPDATE t_user SET login_pwd=? WHERE mobile=?";
		return jdbc.update(sql, pwd, mobile);

	}

	public boolean updateloginPwd(long userId, String oldpwd) {
		String sql = "UPDATE t_user SET login_pwd=? WHERE user_id=?";
		return jdbc.update(sql, oldpwd, userId);

	}

	public boolean updatetradePwd(long userId, String tradepwd) {
		String sql = "UPDATE t_user SET trade_pwd=? WHERE user_id=?";
		return jdbc.update(sql, tradepwd, userId);

	}

	public boolean updateIntegrate(long userId, int money) {
		long time = System.currentTimeMillis();
		String sql = "UPDATE t_user SET modify_time=?, integrate=integrate-? WHERE user_id=? AND integrate-?>=0";
		return jdbc.update(sql, time, money, userId, money);
	}

	public boolean addIntegrate(long userId, int money) {
		String sql = "UPDATE t_user SET integrate=integrate+? WHERE user_id=?";
		return jdbc.update(sql, money, userId);
	}

	/**
	 * 获取银行卡信息
	 */
	public BankCardInfo getBankCardInfo(long userId) {
		String sql = "SELECT * FROM t_bankcard WHERE USER_ID=? limit 1 ";
		return jdbc.queryT(sql, bankMapper, userId);
	}

	/**
	 * 添加银行卡
	 */
	public boolean BondBankcard(long userId, String bankName, String number, String cardholder,String branch) {
		long time = System.currentTimeMillis();
		String sql = "INSERT INTO t_bankcard (user_id,bank_name,number,update_time,cardholder,branch) VALUES (?,?,?,?,?,?)";
		return jdbc.insert(sql, userId, bankName, number, time, cardholder,branch) > 0;
	}

	/**
	 * 修改银行卡
	 */
	public boolean updateBankcard(int id, String bankName, String number, String cardholder,String branch) {
		long time = System.currentTimeMillis();
		String sql = "UPDATE t_bankcard SET bank_name=?,NUMBER=?,update_time=?,cardholder=?,branch=? WHERE id=?";
		return jdbc.update(sql, bankName, number, time, cardholder,branch, id);
	}

	RowMapper<BankCardInfo> bankMapper = new RowMapper<BankCardInfo>() {

		@Override
		public BankCardInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			BankCardInfo bank = new BankCardInfo();
			bank.setBankName(rs.getString("bank_name"));
			bank.setBid(rs.getInt("id"));
			bank.setUpdateTime(rs.getLong("update_time"));
			bank.setNumber(rs.getString("number"));
			bank.setUserId(rs.getLong("user_id"));
			bank.setCardholder(rs.getString("cardholder"));
			bank.setBranch(rs.getString("branch"));
			return bank;
		}
	};

	RowMapper<UserBaseInfo> baseMapper = new RowMapper<UserBaseInfo>() {

		@Override
		public UserBaseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			UserBaseInfo base = new UserBaseInfo();

			base.setMobile(rs.getString("mobile"));
			base.setNickname(rs.getString("nick_name"));
			base.setProxy(rs.getInt("is_area_proxy"));
			base.setUserId(rs.getLong("user_id"));
			base.setAreaCode(rs.getInt("area_code"));
			base.setCityCode(rs.getInt("city_code"));
			return base;
		}
	};

	/*************************************
	 * 2.13 新增
	 ******************************************/

	public boolean updatetradePwdBymobile(String mobile, String tradepwd) {
		String sql = "UPDATE t_user SET trade_pwd=? WHERE mobile=?";
		return jdbc.update(sql, tradepwd, mobile);

	}

	public boolean updateNickname(String name, long userId) {
		String sql = "UPDATE t_user SET nick_name=? WHERE user_id=? ";
		return jdbc.update(sql, name, userId);
	}

	public List<ProxyInfo> getProxyList(int areaCode) {
		String sql = " SELECT user_id,nick_name FROM t_user WHERE area_code=? AND is_area_proxy=1";
		return jdbc.query(sql, pMapper, areaCode);
	}

	RowMapper<ProxyInfo> pMapper = new RowMapper<ProxyInfo>() {

		@Override
		public ProxyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			ProxyInfo base = new ProxyInfo();

			base.setName(rs.getString("nick_name"));

			base.setUserId(rs.getLong("user_id"));

			return base;
		}
	};

	
	
}
