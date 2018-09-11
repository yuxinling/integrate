package com.integrate.admin.module.wallet;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.admin.common.AppDbService;
import com.integrate.admin.module.rechrage.model.Recharge;

@Component
public class WalletDao {

	@Autowired
	private AppDbService jdbc;

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
			return r;
		}
	};

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
	public long addHumanRechrageRecorde(int cityCode, int areaCode, long userId, int money, int type, long orderId, int state, long proxyUserId) {
		long time = System.currentTimeMillis();
		String sql = "INSERT INTO t_rechrage (city_code,area_code,user_id,money,TYPE,TIME,trade_no,state,is_artificial,fixed_integrate,give_integrate,proxy_user_id,residual_fixed_integral,residual_give_integral) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbc.insert(sql, "id", cityCode, areaCode, userId, money, type, time, orderId, state, 1, money, money * 3, proxyUserId, money, money * 3);
	}

	public Recharge getRechargeByOrder(long orderId) {
		String sql = "SELECT * FROM t_rechrage WHERE trade_no= ? ";
		return jdbc.queryT(sql, rmapper, orderId);
	}

	/**
	 * 更新状态 未已充值成功
	 */
	public boolean updateRecharge(long tradeNo, int state) {
		String sql = "UPDATE	 t_rechrage SET state=? WHERE trade_no=? ";
		return jdbc.update(sql, state, tradeNo);
	}

	/**
	 * 流水表
	 * 
	 * @param userId
	 * @param order
	 * @param rid
	 * @param wid
	 * @param money
	 * @param type
	 * @param state
	 * @return
	 */
	public boolean addRecord(long userId, String order, long rid, long wid, int money, int type, int state, long ctime) {

		String sql = "INSERT INTO t_transaction_record (user_id,`order`,recharge_id,withdrawals_id,money,TYPE,TIME,state,create_time) VALUES(?,?,?,?,?,?,?,?,?)";
		return jdbc.insert(sql, userId, order, rid, wid, money, type, ctime, state, ctime) > 0;
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

	public Recharge getRechargeById(long id) {
		String sql = "SELECT * FROM t_rechrage WHERE id= ? ";
		return jdbc.queryT(sql, rmapper, id);
	}

	public boolean adjustRecharge(long id, int opType, int changeMoney) {
		String sql = null;

		Recharge charge = this.getRechargeById(id);
		if (charge == null || changeMoney == 0) {
			return false;
		}
		int money = charge.getMoney();
		if (money != 0) {
			double rate = new BigDecimal(changeMoney).divide(new BigDecimal(money), 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
			if (opType == 0) {
				sql = "update t_rechrage set money = money + ? , fixed_integrate = fixed_integrate + ? , give_integrate = give_integrate + ? ,residual_fixed_integral = residual_fixed_integral + residual_fixed_integral * ? ,residual_give_integral = residual_give_integral + residual_give_integral * ? WHERE id= ? ";
			} else {
				sql = "update t_rechrage set money = money - ? , fixed_integrate = fixed_integrate - ? , give_integrate = give_integrate - ? ,residual_fixed_integral = residual_fixed_integral - residual_fixed_integral * ? ,residual_give_integral = residual_give_integral - residual_give_integral * ? WHERE id= ? ";
			}
			return jdbc.update(sql, changeMoney, changeMoney, changeMoney * 3, rate, rate, id);
		}else{
			if (opType == 0) {
				sql = "update t_rechrage set money = money + ? , fixed_integrate = fixed_integrate + ? , give_integrate = give_integrate + ? ,residual_fixed_integral = residual_fixed_integral + ? ,residual_give_integral = residual_give_integral + ? WHERE id= ? ";
			} else {
				sql = "update t_rechrage set money = money - ? , fixed_integrate = fixed_integrate - ? , give_integrate = give_integrate - ? ,residual_fixed_integral = residual_fixed_integral - ? ,residual_give_integral = residual_give_integral - ? WHERE id= ? ";
			}
			return jdbc.update(sql, changeMoney, changeMoney, changeMoney * 3, changeMoney, changeMoney*3, id);
			
		}
	}

	public boolean updateRechargeStatus(long id, int status) {
		String sql = "update t_rechrage set status =  ? WHERE id= ? ";
		return jdbc.update(sql, status, id);
	}

	public boolean addRechargeOplog(long rid, int beforeMoney, int opType, int changeMoney, String opUser) {
		long time = System.currentTimeMillis();
		String sql = "INSERT INTO t_rechrage_oplog (rid, before_money, op_type, change_money, op_user, op_time) VALUES (?,?,?,?,?,?)";
		return jdbc.insert(sql, rid, beforeMoney, opType, changeMoney, opUser, time) > 0;
	}

}
