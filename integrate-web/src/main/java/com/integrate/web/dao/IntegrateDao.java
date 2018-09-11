package com.integrate.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.integrate.web.common.AppDbService;
import com.integrate.web.model.Balance;
import com.integrate.web.model.Recharge;
import com.integrate.web.model.TaskInfo;

@Component
public class IntegrateDao {

	@Autowired
	private AppDbService jdbc;

	// 获取退还的两个余额 分页

	public List<Balance> getBalance() {
		String sql = "SELECT id,USER_ID,fixed_integrate,give_integrate,update_time,create_time FROM t_balance  where id >? order by id asc  limit  ?   ";
		long id = 0;
		int pageSize = 500;
		List<Balance> all = new ArrayList<>();
		List<Balance> list = null;
		do {
			list = jdbc.query(sql, bMapper, id, pageSize);
			if (CollectionUtils.isNotEmpty(list)) {
				id = list.get(list.size() - 1).getId();
				all.addAll(list);
			}
		} while (list.size() >= pageSize);

		return all;
	}

	// 获取退还天数

	public Map<String, Object> getProportion() {
		String sql = "SELECT fixed,give FROM t_proportion LIMIT 1";
		return jdbc.queryForMap(sql);
	}

	// 监听退还积分task

	public boolean addTask(int state, String resaon, long time) {

		String sql = "INSERT INTO t_task (state,reason,`time`) VALUES(?,?,?)";
		return jdbc.insert(sql, state, resaon, time) > 0;
	}

	/*
	 * public boolean updateTask(int state, String reason,long time) {
	 * 
	 * String sql = "UPDATE t_task SET state= ?,reason=? WHERE TIME=?"; return
	 * jdbc.update(sql, state, reason, time); }
	 */

	public boolean exist(long time) {
		String sql = "SELECT COUNT(1) FROM t_task WHERE TIME=? ";
		return jdbc.queryInt(sql, time) > 0;
	}

	// 輪詢查
	public TaskInfo getTaskInfo(long time) {
		String sql = " SELECT * FROM t_task WHERE TIME=?  limit 1 ";
		return jdbc.queryT(sql, taskMapper, time);
	}

	RowMapper<TaskInfo> taskMapper = new RowMapper<TaskInfo>() {

		@Override
		public TaskInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			TaskInfo t = new TaskInfo();
			t.setId(rs.getInt("id"));
			t.setReason(rs.getString("reason"));
			t.setState(rs.getInt("state"));
			t.setTime(rs.getLong("time"));
			return t;
		}
	};

	RowMapper<Balance> bMapper = new RowMapper<Balance>() {

		@Override
		public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Balance b = new Balance();
			b.setFixed(rs.getInt("fixed_integrate"));
			b.setGive(rs.getInt("give_integrate"));
			b.setId(rs.getLong("id"));
			b.setUserId(rs.getLong("user_id"));
			b.setUpdateTime(rs.getLong("update_time"));
			b.setCreateTime(rs.getLong("create_time"));
			return b;
		}
	};

	/**
	 * 
	 * 修改积分规则 添加方法
	 * 
	 * 
	 */

	/**
	 * 获取 需要退还积分的充值记录
	 * 
	 * @return
	 */
	public List<Recharge> getRechargeList() {
		
		String sql = "SELECT * FROM t_rechrage  WHERE is_scan=0 AND is_freeze=0 AND TYPE=0 AND state=1 and  id >? ORDER BY id ASC limit ? ";
		
		long id = 0;
		int pageSize = 500;
		List<Recharge> all = new ArrayList<>();
		List<Recharge> list = null;
		do {
			list=jdbc.query(sql, rMapper, id, pageSize);
			if (CollectionUtils.isNotEmpty(list)) {
				id = list.get(list.size() - 1).getId();
				all.addAll(list);
			}
		} while (list.size() >= pageSize);

		return all;
		

	}

	// 退还积分 修改剩余积分 以及是否退还完毕
	public boolean updateRechange(long rid, int rfintegrate, int rgintegrate, int scan) {
		String sql = "UPDATE t_rechrage SET residual_fixed_integral=residual_fixed_integral-?,residual_give_integral=residual_give_integral-?,is_scan=? WHERE id=? ";
		return jdbc.update(sql, rfintegrate, rgintegrate, scan, rid);
	}

	// 更新两个余额

	public boolean updateBalance(int fixed, int give, long userId) {
		String sql = "UPDATE t_balance SET fixed_integrate=fixed_integrate-?,give_integrate=give_integrate-? WHERE USER_ID=? ";
		return jdbc.update(sql, fixed, give, userId);
	}

	RowMapper<Recharge> rMapper = new RowMapper<Recharge>() {

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

			r.setIsFreeze(rs.getInt("is_freeze"));
			r.setIsScan(rs.getInt("is_scan"));
			r.setResidualFixedIntegral(rs.getInt("residual_fixed_integral"));
			r.setResidualGiveIntegral(rs.getInt("residual_give_integral"));

			r.setFixedIntegrate(rs.getInt("fixed_integrate"));
			r.setGiveIntegrate(rs.getInt("give_integrate"));
			return r;
		}
	};

	
	//获取每个用户剩余积分
	public Map<String, Object>getUserBalance(long userId){
		String sql="SELECT user_id,fixed_integrate,give_integrate,fixed_integrate+give_integrate total FROM t_balance where user_id=? limit 1";
		return jdbc.queryForMap(sql,userId);
	} 
	
	//获取所有用户充值 总额
	public List<Map<String, Object>> getUserRechrage(){
		String sql="SELECT SUM(money) total,user_id FROM t_rechrage  WHERE  TYPE=0 AND state=1 GROUP BY USER_ID";
		return jdbc.queryList(sql);
	} 
	
	public  Map<String, Object> getRechrageMaxId(long userId){
		String sql="SELECT id,money FROM t_rechrage WHERE USER_ID=? ORDER BY money DESC  LIMIT 1";
		return jdbc.queryForMap(sql,userId);
	}
	
	//更新剩余积分数目
	public boolean updateData(int fix,int give,long id){
		String sql="UPDATE t_rechrage SET residual_fixed_integral=residual_fixed_integral-? , residual_give_integral=residual_give_integral-? WHERE id=? ";
		return jdbc.update(sql,fix,give,id);
	}
	
	
}
