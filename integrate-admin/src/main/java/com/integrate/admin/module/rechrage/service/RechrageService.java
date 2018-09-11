package com.integrate.admin.module.rechrage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.rechrage.dao.RechrageDao;
import com.integrate.admin.module.rechrage.model.Recharge;
import com.integrate.admin.module.rechrage.model.Rechrage;
import com.integrate.admin.module.rechrage.model.RechrageResp;
import com.integrate.admin.module.wallet.WalletDao;
import com.integrate.admin.util.PageData;
import com.integrate.common.util.BeanUtil;
import com.integrate.common.util.GlobalIDUtil;
import com.integrate.common.util.StringUtil;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;

@Service
public class RechrageService {

	@Autowired
	private DaoSupport dao;
	@Autowired
	private WalletDao walletDao;
	@Autowired
	private CityService cityService;

	@Autowired
	private RechrageDao rechrageDao;
	Logger logger = LoggerFactory.getLogger(getClass());
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	Date date = null;

	public List<RechrageResp> getRechrageList(Page page) {
		List<RechrageResp> result = new ArrayList<RechrageResp>();
		List<Rechrage> list = null;
		try {
			list = (List<Rechrage>) dao.findForList("RechrageMapper.datalistPage", page);
			logger.warn(" list  size:[{}]", list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertRechrage2RechrageResp(list);
	}

	public List<RechrageResp> getExcelRechrageList(PageData pd) {
		List<RechrageResp> result = new ArrayList<RechrageResp>();
		List<Rechrage> list = null;
		try {
			list = (List<Rechrage>) dao.findForList("RechrageMapper.excelSelectAll", pd);
			logger.warn(" list  size:[{}]", list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertRechrage2RechrageResp(list);
	}

	private List<RechrageResp> convertRechrage2RechrageResp(List<Rechrage> list) {
		List<RechrageResp> result = new ArrayList<RechrageResp>();

		if (list != null) {
			for (Rechrage r : list) {
				RechrageResp rr = new RechrageResp();
				BeanUtil.copyProperties(rr, r);

				if (r.getTime() > 0) {
					date = new Date(r.getTime());
					String time = sdf.format(date);
					rr.setTime(time);
				}

				Map<String, Object> baseUserInfo = rechrageDao.getBaseUserInfo(r.getUserId());
				if(StringUtil.isNumber(r.getProxyUserId())){
					Map<String, Object> base = rechrageDao.getBaseUserInfo(r.getProxyUserId());
					logger.warn("base :{}",base);
					if(base!=null){
						rr.setProxyName(base.get("nick_name")+"");
					}

				}

				if (baseUserInfo != null) {
					rr.setName(baseUserInfo.get("nick_name") + "");
					rr.setMobile(baseUserInfo.get("mobile") + "");

					Map<String, Object> area = rechrageDao
							.getArea(r.getAreaCode());
					if (area != null) {
						rr.setArea(area.get("city_name") + "-" + area.get("area_name"));
					}
				}

				result.add(rr);
			}
		}
		return result;
	}

	@Transactional
	public void humanRecharge(long userId, int areaCode, int money,long proxyUserId) {
		// 先生成内部订单
		int cityCode = cityService.getCityCodeByAreaCode(areaCode);
		String orderId = GlobalIDUtil.createID(System.currentTimeMillis()) + "";
		long rechargeId = walletDao.addHumanRechrageRecorde(cityCode, areaCode, userId, money, 0, Long.parseLong(orderId), 0,proxyUserId);
		if (rechargeId < 1) {
			throw new BusinessException(SysMsgEnumType.FAIL);
		}

		updateData(orderId, userId);
	}

	@Transactional
	public boolean updateData(String orderId, long userId) {
		Recharge recharge = walletDao.getRechargeByOrder(Long.parseLong(orderId));
		// 已充值成功直接返回true
		if (recharge.getState() == 1) {
			throw new BusinessException("重复充值");
		}

		boolean updateRecharge = walletDao.updateRecharge(Long.parseLong(orderId), 1);
		if (!updateRecharge) {
			logger.warn("积分充值失败:{}", orderId);
			throw new BusinessException("充值失败");
		}

		long rechargeId = recharge.getId();
		int money = recharge.getMoney();
		boolean addRecord = walletDao.addRecord(userId, orderId + "", rechargeId, 0, money, 0, 1,
				System.currentTimeMillis());
		if (!addRecord) {
			throw new BusinessException("生成流水失败");
		}
		// 积分退还记录
		boolean addBalance = false;
		// 有就累加
		if (walletDao.isexist(userId)) {
			addBalance = walletDao.updateBalance(userId, money, money * 3);
		} else {
			addBalance = walletDao.addBalance(userId, money, money * 3, rechargeId);
		}
		if (!addBalance) {
			throw new BusinessException("积分记录失败");
		}
		return true;
	}
	
	/**
	 * 充值积分调整：opType:0-增加；1-减少
	 * @param rid
	 * @param opType
	 * @param opMoney
	 */
	public  void adjustRecharge(Long rid,Integer opType,Integer changeMoney,String opUser){
		Recharge recharge = walletDao.getRechargeById(rid);
		if(recharge == null){
			throw new BusinessException("未找到对应的充值记录");
		}
		
		if(opType == 1 && changeMoney > recharge.getMoney()){
			throw new BusinessException("当前需要调整减少的积分大于充值的积分");
		}
		
		boolean flag = walletDao.adjustRecharge(rid, opType, changeMoney);
		if(flag){
			walletDao.addRechargeOplog(rid, recharge.getMoney(), opType, changeMoney, opUser);
		}
	}
	public  void updateRechargeStatus(Long rid,Integer status){
		Recharge recharge = walletDao.getRechargeById(rid);
		if(recharge == null){
			throw new BusinessException("未找到对应的充值记录");
		}
		walletDao.updateRechargeStatus(rid, status);
	}
}
