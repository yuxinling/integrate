package com.integrate.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integrate.common.util.BeanUtil;
import com.integrate.common.util.GlobalIDUtil;
import com.integrate.common.util.R;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;
import com.integrate.web.dao.UserDao;
import com.integrate.web.dao.WalletDao;
import com.integrate.web.model.AreaCharge;
import com.integrate.web.model.Recharge;
import com.integrate.web.model.RechargeInfoResp;
import com.integrate.web.model.RechargeResp;
import com.integrate.web.model.Record;
import com.integrate.web.model.RecordResp;
import com.integrate.web.model.UserBaseInfo;

@Service
public class WalletService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private UserDao userDao;

	private Interner<String> lockKeys = Interners.newWeakInterner();

	// 提现
	@Transactional
	public boolean add(long userId, int money, String tradePwd) {
		synchronized (lockKeys.intern(String.valueOf(userId))) {
			Integer integrate = userintegrate(userId);
			logger.warn("提现金额 userId:{}, money:{},用户金额 integrate:{}", userId, money, integrate);
			if (money > integrate) {
				throw new BusinessException(SysMsgEnumType.INTEGRATE_FAIL);
			}

			long id = walletDao.add(userId, money);
			if (id < 1) {
				throw new BusinessException(R.SYS_FAIL, "提现失败");
			}

			// 扣除用户积分
			boolean updateIntegrate = userDao.updateIntegrate(userId, money);
			if (!updateIntegrate) {
				throw new BusinessException(R.SYS_FAIL, "扣除用户积分失败");
			}
			// 插入流水表
			long currentTime = System.currentTimeMillis();
			// 生成内部订单
			long orderId = GlobalIDUtil.createID(currentTime);
			boolean addRecord = addRecord(userId, orderId + "", 0, id, money, 1, 0, currentTime);
			logger.warn("提现金额 userId:{}, money:{},addRecord:{}, orderId:{}", userId, money, addRecord, orderId);
			if (!addRecord) {
				throw new BusinessException(R.SYS_FAIL, "生成流水失败");
			}
			return true;
		}
	}

	// 流水
	public boolean addRecord(long userId, String order, long rid, long wid, int money, int type, int state,
			long ctime) {
		return walletDao.addRecord(userId, order, rid, wid, money, type, state, ctime);
	}

	// 兑换 和充值
	@Transactional
	public boolean addRechrageRecorde(int cityCode, int areaCode, long userId, int money, int type) {
		// 插入流水表
		long currentTime = System.currentTimeMillis();
		// 先生成内部订单
		long orderId = GlobalIDUtil.createID(currentTime);
		// 充值表

		// 充值
		if (type == 0) {
			/*
			 * boolean pay = true;
			 *//**
				 * to do
				 **//*
					 * // 银联支付 if (!pay) { throw new
					 * BusinessException(R.SYS_FAIL, "支付失败"); }
					 * 
					 * logger.warn("orderId:[{}]", orderId); boolean addRecord =
					 * addRecord(userId, orderId + "", rechargeId, 0, money, 0,
					 * 1); if (!addRecord) { throw new
					 * BusinessException(R.SYS_FAIL, "生成流水失败"); } // 积分退还记录
					 * boolean addBalance = false; // 有就累加 if
					 * (walletDao.isexist(userId)) { addBalance =
					 * walletDao.updateBalance(userId, money, money * 3); } else
					 * { addBalance = walletDao.addBalance(userId, money, money
					 * * 3, rechargeId); } if (!addBalance) { throw new
					 * BusinessException(R.SYS_FAIL, "积分记录失败"); }
					 */
		}
		// 兑换
		else if (type == 1) {
			synchronized (lockKeys.intern(String.valueOf(userId))) {
				Integer integrate = userintegrate(userId);
				if (money > integrate) {
					throw new BusinessException(SysMsgEnumType.INTEGRATE_FAIL);
				}

				long rechargeId = walletDao.addRechrageRecorde(cityCode, areaCode, userId, money, type, orderId, 1, null);
				if (rechargeId < 1) {
					throw new BusinessException(R.SYS_FAIL, "兑换失败");
				}

				// 扣除用户积分
				boolean updateIntegrate = userDao.updateIntegrate(userId, money);
				if (!updateIntegrate) {
					throw new BusinessException(R.SYS_FAIL, "扣除用户积分失败");
				}

				logger.warn("addRechrageRecorde userId:{}, type:{}, money:{}, orderId:{}", userId, type, money, orderId);
				boolean addRecord = addRecord(userId, orderId + "", rechargeId, 0, money, 2, 1, currentTime);
				if (!addRecord) {
					throw new BusinessException(R.SYS_FAIL, "生成流水失败");
				}
			}
		}

		return true;

	}

	public Integer userintegrate(long userId) {
		Map<String, Object> map = userDao.getUserIntegrate(userId);

		if (map == null) {
			throw new BusinessException(SysMsgEnumType.FAIL);
		}
		int integrate = Integer.parseInt(map.get("integrate") + "");
		return integrate;

	}
	
	/**
	 * is_freeze_all 不等于 ‘0’时，表示冻结
	 * @param userId
	 * @return
	 */
	public boolean isFreezeAll(long userId) {
		Map<String, Object> map = userDao.getFreezeAll(userId);
		
		if (map == null) {
			throw new BusinessException(SysMsgEnumType.FAIL);
		}
		int freezeAll = Integer.parseInt(map.get("is_freeze_all") + "");
		return freezeAll != 0 ;
	}

	public List<RechargeResp> getUserOrderList(long userId, int type, long lastId, int pageSize) {
		List<Recharge> userOrderList = walletDao.getUserOrderList(userId, type, lastId, pageSize);
		List<RechargeResp> resp = new ArrayList<>();
		if (userOrderList != null) {
			for (Recharge r : userOrderList) {
				RechargeResp rr = new RechargeResp();
				rr.setMoney(r.getMoney());
				rr.setRechargeId(r.getId());
				rr.setTime(r.getTime());
				rr.setTradeNo(r.getTradeNo());
				rr.setType(r.getType());
				if(r.getExchangeStatus() != 0){
					rr.setExchangeStatus(r.getExchangeStatus());
				}
				resp.add(rr);
			}
		}

		return resp;
	}

	public List<RecordResp> getUserRecordList(long userId, long lastId, int pageSize) {
		List<Record> userRecordList = walletDao.getUserRecordList(userId, lastId, pageSize);
		List<RecordResp> list = new ArrayList<>();
		if (userRecordList != null) {
			for (Record r : userRecordList) {
				RecordResp rr = new RecordResp();
				BeanUtil.copyProperties(rr, r);
				rr.setRecordId(r.getId());
				list.add(rr);

			}
		}
		return list;

	}

	// 区域用户充值记录
	public Map<String, Object> getareaOrderList(long userId, long lastId, int pageSize) {
		Map<String, Object> map = new HashMap<>();

		/*
		 * UserBaseInfo user = userDao.getUserBaseInfoById(userId);
		 * 
		 * if (user == null) { throw new BusinessException(R.SYS_FAIL,
		 * "用户信息不存在"); }
		 */
		map.put("total", gettotal(userId));
		List<Recharge> getareaOrderList = walletDao.getareaOrderList(userId, lastId, pageSize);
		List<AreaCharge> resp = new ArrayList<>();
		if (getareaOrderList != null) {
			for (Recharge r : getareaOrderList) {
				AreaCharge rr = new AreaCharge();
				UserBaseInfo userBaseInfo = userDao.getUserBaseInfoById(r.getUserId());
				if (userBaseInfo != null) {
					rr.setMobile(userBaseInfo.getMobile());
					rr.setName(userBaseInfo.getNickname());
				}
				rr.setMoney(r.getMoney());
				rr.setRechargeId(r.getId());
				rr.setTime(r.getTime());
				rr.setTradeNo(r.getTradeNo());
				resp.add(rr);
			}
		}
		map.put("areaRecharge", resp);
		return map;
	}

	public int gettotal(long proxyUserId) {
		Map<String, Object> map = walletDao.gettotle(proxyUserId);
		if (map != null) {
			return Integer.parseInt(map.get("total") + "");
		}
		return 0;
	}

	public RechargeInfoResp getRechragetotal(long userId) {
		RechargeInfoResp r = new RechargeInfoResp();
		Map<String, Object> map = walletDao.getRechrageTotal(userId);
		if (!map.isEmpty() && map.get("total") != null) {

			r.setTotal(Integer.parseInt(map.get("total") + ""));
		} else {
			r.setTotal(0);
		}
		return r;
	}
	
	public RechargeInfoResp getWithdrawalsTotal(long userId) {
		RechargeInfoResp r = new RechargeInfoResp();
		Map<String, Object> map = walletDao.getWithdrawalsTotal(userId);
		if (!map.isEmpty() && map.get("total") != null) {
			r.setTotal(Integer.parseInt(map.get("total") + ""));
		} else {
			r.setTotal(0);
		}
		return r;
	}

}
