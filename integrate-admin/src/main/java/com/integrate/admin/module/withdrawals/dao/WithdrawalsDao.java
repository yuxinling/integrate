package com.integrate.admin.module.withdrawals.dao;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.integrate.admin.common.AppDbService;

@Component
public class WithdrawalsDao {

	@Autowired
	private AppDbService jdbc;

	public Map<String , Object> getBankinfo(long userId){
		String sql="SELECT bank_name,number,cardholder,branch FROM t_bankcard WHERE USER_ID=? LIMIT 1";
		return jdbc.queryForMap(sql, userId);
	}
	
	public boolean update(String [] ids){
		String sql="UPDATE t_withdrawals SET state=1 WHERE id in (" + StringUtils.join(ids, ",") + ") ";
		
		return jdbc.update(sql);
	}
	public boolean updateState(String [] ids){
		String sql="UPDATE t_transaction_record SET state=1 WHERE withdrawals_id in (" + StringUtils.join(ids, ",") + ") ";
	
		return jdbc.update(sql);
	}
	
	public Map<String, Object> getWithDrawlsTotal(int state,long begin ,long end){
		String sql="SELECT SUM(money) total FROM t_withdrawals  WHERE state=?  AND `time` BETWEEN ? AND ? ";
		return jdbc.queryForMap(sql, state,begin,end);
	}
	
}
