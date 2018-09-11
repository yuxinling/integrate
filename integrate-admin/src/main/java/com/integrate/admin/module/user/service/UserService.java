package com.integrate.admin.module.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.rechrage.dao.RechrageDao;
import com.integrate.admin.module.rechrage.model.Rechrage;
import com.integrate.admin.module.rechrage.model.UserRecordResp;
import com.integrate.admin.module.user.dao.UserDao;
import com.integrate.admin.module.user.model.User;
import com.integrate.admin.module.user.model.UserBase;
import com.integrate.admin.module.user.model.UserResp;
import com.integrate.admin.module.withdrawals.model.Withdrawals;
import com.integrate.admin.util.PageData;
import com.integrate.common.util.BeanUtil;
import com.integrate.common.util.StringUtil;
import com.integrate.exception.BusinessException;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private DaoSupport dao;

	@Autowired
	private RechrageDao rechrageDao;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public List<UserResp> getUserList(Page page) {
		List<UserResp> result = new ArrayList<>();
		List<User> list = null;
		try {
			list = (List<User>) dao.findForList("UserMapper.datalistPage", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getUserRespByUserList(list);
	}

	public List<UserResp> getExcelUserList(PageData pd) {
		List<UserResp> result = new ArrayList<>();
		List<User> list = null;
		try {
			list = (List<User>) dao.findForList("UserMapper.excelSelectAll", pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getUserRespByUserList(list);
	}

	public List<UserResp> getUserRespByUserList(List<User> list) {
		List<UserResp> result = new ArrayList<>();
		if (list != null) {
			for (User u : list) {
				UserResp ur = new UserResp();
				BeanUtil.copyProperties(ur, u);
				if(u.getCreateTime()>0){
					Date date=new Date(u.getCreateTime());
					String format = sdf.format(date);
					ur.setTime(format);
				}

				Map<String, Object> userArea = userDao.getUserArea(u.getAreaCode());

				Map<String, Object> exchangeTotal = userDao.exchangeTotal(u.getUserId());
				Map<String, Object> rechrageTotal = userDao.rechrageTotal(u.getUserId());
				Map<String, Object> withdrawalsTotal = userDao.withdrawalsTotal(u.getUserId());
				if (!exchangeTotal.isEmpty() && exchangeTotal.get("total") != null) {
					ur.setExchangeTotal(Integer.parseInt(exchangeTotal.get("total") + ""));
				}

				if (!rechrageTotal.isEmpty() && rechrageTotal.get("total") != null) {
					ur.setRechrageTotal(Integer.parseInt(rechrageTotal.get("total") + ""));
				}
				if (!withdrawalsTotal.isEmpty() && withdrawalsTotal.get("total") != null) {
					ur.setWithdrawalsTotal(Integer.parseInt(withdrawalsTotal.get("total") + ""));
				}
				if (userDao.hasProxy(u.getAreaCode())) {
					ur.setHasproxy(0);
				} else {
					ur.setHasproxy(1);
				}
				if (userArea != null) {// city_name,area_name
					ur.setArea(userArea.get("city_name") + "-" + userArea.get("area_name"));
				}

				result.add(ur);
			}
		}

		return result;
	}

	public boolean updateUser(long userId, int isAreaProxy,int fixDay,int giveDay) {
		return userDao.updateUser(userId, isAreaProxy, fixDay, giveDay);
	}

	public UserBase getUserBase(long userId) {
		return userDao.getUserBase(userId);
	}

	public List<UserBase> getUserBaseByArea(int areaCode) {
		return userDao.getUserBaseByArea(areaCode);
	}

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
		// 冻结用户所有的充值订单 退还积分
		boolean rechrageFreeze = userDao.rechrageFreeze(isFreeze, userId);
		boolean isfreeze = userDao.isfreeze(isFreeze, userId);
		if (!rechrageFreeze || !isfreeze) {
			throw new BusinessException("是否冻结用户所有的充值订单失败");
		}
		return true;

	}
	
	/**
	 * 冻结用户的所有功能:积分提现，积分兑换，积分奖励
	 * @param isFreeze
	 * @param userId
	 * @return
	 */
	public boolean freezeAll(int isFreeze) {
		boolean isfreeze = userDao.isfreezeAll(isFreeze);
		if (!isfreeze) {
			throw new BusinessException("冻结用户的所有功能失败");
		}
		return true;
	}

	public boolean Rechrageisfreeze(int isFreeze, long id) {
		// 冻结用户所有的充值订单 退还积分
		boolean rechrageFreeze = userDao.rechrageFreezeByid(isFreeze, id);

		return true;

	}

	// 获取用户充值
	public List<UserRecordResp> getRechrageByUserId(Page page) {
		List<UserRecordResp> list=new ArrayList<>();
		
		List<Rechrage> rechrageByUserId = null;
		try {
			rechrageByUserId = (List<Rechrage>) dao.findForList("RechrageMapper.datalistPage", page);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rechrageByUserId!=null){
			for(Rechrage r:rechrageByUserId){
				UserRecordResp ur=new UserRecordResp();
				BeanUtils.copyProperties(r, ur);
				if(r.getTime()>0){
				Date date=new Date(r.getTime());
				String format = sdf.format(date);
				ur.setTime(format);
				
				}
				if(StringUtil.isNumber(r.getProxyUserId())){
					UserBase userBase = userDao.getUserBase(r.getProxyUserId());
					if(userBase!=null){
						ur.setNickName(userBase.getName());
					}
				}
				list.add(ur);
			}
		}
		return list;
	}

	// 获取用户兑换
	public List<Rechrage> getexchangeByUserId(long userId) {
		return rechrageDao.getexchangeByUserId(userId);
	}

	// 用户提现列表
	public List<Withdrawals> getWithdrawals(long userId) {
		return rechrageDao.getWithdrawals(userId);
	}
	
	public int rechrageTotalV( int areaCode, long proxyUserId, long begin, long end) {
		Map<String, Object> rechrageTotalV = userDao.rechrageTotalV( areaCode, proxyUserId, begin, end);
		if(rechrageTotalV!=null&&StringUtil.isNumber(rechrageTotalV.get("total"))){
			return Integer.parseInt(rechrageTotalV.get("total")+"");
		}
		return 0;
	}

	public int returnTotal( int areaCode, long proxyUserId, long begin, long end) {
		Map<String, Object> rechrageTotalV = userDao.returnTotal( areaCode, proxyUserId, begin, end);
		if(rechrageTotalV!=null && StringUtil.isNumber(rechrageTotalV.get("fixedtotal")) &&StringUtil.isNumber(rechrageTotalV.get("givetotal"))){
			 int total=Integer.parseInt(rechrageTotalV.get("fixedtotal")+"")+Integer.parseInt(rechrageTotalV.get("givetotal")+"");
			 return total;
		}
		return 0;
	}
	
	public int exchangeTotalV(long begin, long end) {
		Map<String, Object> exchangeTotalV = userDao.exchangeTotalV( begin, end);
		if(exchangeTotalV!=null &&StringUtil.isNumber(exchangeTotalV.get("total"))){
			 int total=Integer.parseInt(exchangeTotalV.get("total")+"");
			 return total;
		}
		return 0;
	}
	
}
