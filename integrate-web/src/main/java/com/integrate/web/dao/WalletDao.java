package com.integrate.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.common.util.GlobalIDUtil;
import com.integrate.web.common.AppDbService;
import com.integrate.web.model.Recharge;
import com.integrate.web.model.Record;

@Component
public class WalletDao {

	@Autowired
	private AppDbService jdbc;

	/**
	 * 提现记录
	 * 
	 * @return
	 */
	public long add(long userId, int money) {
		long time = System.currentTimeMillis();
		String sql = " INSERT INTO t_withdrawals (user_id,money,TIME) VALUES (?,?,?)";
		return jdbc.insert(sql, "id", userId, money, time);
	}

	/**
	 * 流水表
	 * 
	 * @param userId
	 * @param order
	 * @param rid
	 * @param wid
	 * @param money
	 * @param integrate
	 * @param type
	 * @param state
	 * @return
	 */
	public boolean addRecord(long userId, String order, long rid, long wid, int money, int type, int state, long ctime) {

		String sql = "INSERT INTO t_transaction_record (user_id,`order`,recharge_id,withdrawals_id,money,TYPE,TIME,state,create_time) VALUES(?,?,?,?,?,?,?,?,?)";
		return jdbc.insert(sql, userId, order, rid, wid, money, type, ctime, state, ctime) > 0;
	}

	/**
	 * 充值兑换表
	 * 
	 * @param cityCode
	 * @param areaCode
	 * @param userId
	 * @param money
	 * @param type
	 * @return
	 */
	public long addRechrageRecorde(int cityCode, int areaCode, long userId, int money, int type, long orderId, int state, String txntime) {
		long time = System.currentTimeMillis();
		String sql = "INSERT INTO t_rechrage (city_code,area_code,user_id,money,TYPE,TIME,trade_no,state,txntime,status) VALUES (?,?,?,?,?,?,?,?,?,?)";
		return jdbc.insert(sql, "id", cityCode, areaCode, userId, money, type, time, orderId, state, txntime, 1);
	}

	public long addRechrageRecordeV2(int cityCode, int areaCode, long userId, int money, int type, long orderId, int state, String txntime, long proxyUserId) {
		long time = System.currentTimeMillis();
		String sql = "INSERT INTO t_rechrage (city_code,area_code,user_id,money,TYPE,TIME,trade_no,state,txntime,proxy_user_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
		return jdbc.insert(sql, "id", cityCode, areaCode, userId, money, type, time, orderId, state, txntime, proxyUserId);
	}

	/**
	 * 更新状态 未已充值成功
	 */
	public boolean updateRecharge(long tradeNo, int state) {
		String sql = "UPDATE	 t_rechrage SET state=? WHERE trade_no=? ";
		return jdbc.update(sql, state, tradeNo);
	}

	/**
	 * 积分记录表
	 * 
	 * @param userId
	 * @param fixed
	 * @param give
	 * @param rid
	 * @return
	 */
	public boolean addBalance(long userId, int fixed, int give, long rid) {
		long time = System.currentTimeMillis();
		String sql = "INSERT	INTO t_balance (user_id,fixed_integrate,give_integrate,recharge_id,update_time,create_time) VALUES(?,?,?,?,?,?)";
		return jdbc.insert(sql, userId, fixed, give, rid, time, time) > 0;
	}

	public boolean isexist(long userId) {
		String sql = "SELECT COUNT(1) FROM t_balance WHERE USER_ID=? ";
		return jdbc.queryInt(sql, userId) > 0;
	}

	public boolean updateBalance(long userId, int fixed, int give) {
		long time = System.currentTimeMillis();
		String sql = "UPDATE t_balance SET fixed_integrate=fixed_integrate+?,give_integrate=give_integrate+?,update_time=? WHERE USER_ID=?";

		return jdbc.update(sql, fixed, give, time, userId);
	}

	// 用户充值和兑换记录
	public List<Recharge> getUserOrderList(long userId, int type, long lastId, int pageSize) {
		String sql = "SELECT * FROM t_rechrage WHERE USER_ID=? AND TYPE=? and state =1 ";
		if (lastId > 0) {
			sql += " and id < " + lastId;
		}
		sql += " order by id desc limit ?";

		return jdbc.query(sql, rmapper, userId, type, pageSize);
	}

	// 区域用户充值记录
	public List<Recharge> getareaOrderList(long proxyUserId, long lastId, int pageSize) {
		String sql = "SELECT * FROM t_rechrage WHERE  proxy_user_id=? AND TYPE=0 and state=1";
		if (lastId > 0) {
			sql += " and id < " + lastId;
		}
		sql += " order by id desc limit ?";

		return jdbc.query(sql, rmapper, proxyUserId, pageSize);
	}

	public Recharge getRechargeByOrder(long orderId) {
		String sql = "SELECT * FROM t_rechrage WHERE trade_no= ? ";
		return jdbc.queryT(sql, rmapper, orderId);
	}

	// 区域用户充值
	public Map<String, Object> gettotle(long proxyUserId) {
		String sql = "SELECT sum(money) AS total  FROM t_rechrage WHERE  proxy_user_id=? AND TYPE=0 and state=1 ";

		return jdbc.queryForMap(sql, proxyUserId);
	}

	// 用户记录SELECT * FROM t_transaction_record WHERE USER_ID=1
	public List<Record> getUserRecordList(long userId, long lastId, int pageSize) {
		String sql = "SELECT * FROM t_transaction_record WHERE USER_ID=?";
		if (lastId > 0) {
			sql += " and id < " + lastId;
		}
		sql += " order by id desc limit ?";

		return jdbc.query(sql, recordMapper, userId, pageSize);
	}

	RowMapper<Recharge> rmapper = new RowMapper<Recharge>() {

		@Override
		public Recharge mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Recharge r = new Recharge();
			r.setAreaCode(rs.getInt("area_code"));
			r.setCityCode(rs.getInt("city_code"));
			r.setId(rs.getLong("id"));
			r.setMoney(rs.getInt("money"));
			r.setTime(rs.getLong("time"));
			r.setTradeNo(rs.getLong("trade_no"));
			r.setType(rs.getInt("type"));
			r.setUserId(rs.getLong("user_id"));
			r.setState(rs.getInt("state"));
			r.setTxnTime(rs.getString("txntime"));
			r.setExchangeStatus(rs.getInt("status"));
			return r;
		}
	};

	RowMapper<Record> recordMapper = new RowMapper<Record>() {

		@Override
		public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Record r = new Record();
			r.setCreateTime(rs.getLong("create_time"));
			r.setId(rs.getLong("id"));

			r.setMoney(rs.getInt("money"));
			r.setOrderId(rs.getString("order"));
			r.setRechargeId(rs.getLong("recharge_id"));
			r.setState(rs.getInt("state"));
			r.setTime(rs.getLong("time"));
			r.setType(rs.getInt("type"));
			r.setUserId(rs.getLong("user_id"));
			r.setWithdrawalsId(rs.getLong("withdrawals_id"));
			return r;
		}
	};

	public Map<String, Object> getRechrageTotal(long userId) {
		String sql = "SELECT SUM(money)  total FROM t_rechrage  WHERE TYPE=0  AND state=1 AND   USER_ID=? ";
		return jdbc.queryForMap(sql, userId);
	}

	// 提现
	public Map<String, Object> getWithdrawalsTotal(long userId) {
		String sql = "SELECT SUM(money) total FROM t_withdrawals  WHERE  state=1 AND USER_ID=? ";
		return jdbc.queryForMap(sql, userId);
	}
}
