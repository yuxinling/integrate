package com.integrate.admin.module.withdrawals.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.integrate.admin.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.user.dao.UserDao;
import com.integrate.admin.module.withdrawals.dao.WithdrawalsDao;
import com.integrate.admin.module.withdrawals.model.Withdrawals;
import com.integrate.admin.module.withdrawals.model.WithdrawalsResp;
import com.integrate.common.util.BeanUtil;

@Service
public class WithdrawalService {

	@Autowired
	private DaoSupport dao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WithdrawalsDao withdrawalsDao;
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd :HH:mm:ss");

	public List<WithdrawalsResp> getWithdrawlsList(Page page) {
		List<WithdrawalsResp> result = new ArrayList<>();
		List<Withdrawals> list = null;
		try {
			list = (List<Withdrawals>) dao.findForList("WithdrawalsMapper.datalistPage", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertWithdrawals2WithdrawalsResp(list);
	}

	private List<WithdrawalsResp> convertWithdrawals2WithdrawalsResp(List<Withdrawals> list) {
		List<WithdrawalsResp> result = new ArrayList<>();

		if (list != null) {
			for (Withdrawals w : list) {
				WithdrawalsResp wr = new WithdrawalsResp();
				BeanUtil.copyProperties(wr, w);
				Map<String, Object> userInfo = userDao.getUserInfo(w.getUserId());
				if (userInfo != null) {
					wr.setMobile(userInfo.get("mobile") + "");
					wr.setNickName(userInfo.get("nick_name") + "");
					Map<String, Object> userArea = userDao
							.getUserArea(Integer.parseInt(userInfo.get("area_code") + ""));
					if (userArea != null) {
						wr.setArea(userArea.get("city_name") + "-" + userArea.get("area_name"));
					}
					Map<String, Object> bankinfo = withdrawalsDao.getBankinfo(w.getUserId());
					if (bankinfo != null) {
						wr.setBankName(bankinfo.get("bank_name") + "");
						wr.setNumber(bankinfo.get("number") + "");
						wr.setCardholder(bankinfo.get("cardholder") + "");
						if (bankinfo.get("branch") == null) {
							wr.setBranch( "未填写");
						} else {
							wr.setBranch(bankinfo.get("branch") + "");// 开户行
						}
					}
				}

				if (w.getTime() > 0) {
					Date date = new Date(w.getTime());
					String time = sdf.format(date);
					wr.setTime(time);
				}

				result.add(wr);
			}
		}

		return result;
	}

	public List<WithdrawalsResp> getExcelWithdrawlsList(PageData pd) {
		List<WithdrawalsResp> result = new ArrayList<>();
		List<Withdrawals> list = null;
		try {
			list = (List<Withdrawals>) dao.findForList("WithdrawalsMapper.excelSelectAll", pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertWithdrawals2WithdrawalsResp(list);
	}

	public boolean update(String ids) {
		boolean updateState = withdrawalsDao.updateState(ids.split(","));
		if (!updateState) {
			return false;
		}
		return withdrawalsDao.update(ids.split(","));
	}

	public int getWithDrawlsTotal(int state, long begin, long end) {
		Map<String, Object> withDrawlsTotal = withdrawalsDao.getWithDrawlsTotal(state, begin, end);
		if (withDrawlsTotal != null && withDrawlsTotal.get("total") != null) {

			return Integer.parseInt(withDrawlsTotal.get("total") + "");
		}
		return 0;
	}
}
