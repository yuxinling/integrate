package com.integrate.admin.module.rechrage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.admin.dao.DaoSupport;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.rechrage.model.RechargeOplog;
import com.integrate.admin.module.rechrage.model.RechargeOplogResp;
import com.integrate.common.util.BeanUtil;

@Service
public class RechrageOplogService {

	@Autowired
	private DaoSupport dao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	Logger logger = LoggerFactory.getLogger(getClass());
	@SuppressWarnings("unchecked")
	public List<RechargeOplogResp> getRechrageOplogs(Page page) {
		List<RechargeOplog> list = null;
		try {
			list = (List<RechargeOplog>) dao.findForList("RechargeOplogMapper.datalistPage", page);
			logger.warn(" list  size:[{}]", list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertRechrage2RechrageResp(list);
	}

	private List<RechargeOplogResp> convertRechrage2RechrageResp(List<RechargeOplog> list) {
		List<RechargeOplogResp> result = new ArrayList<RechargeOplogResp>();

		if (list != null) {
			for (RechargeOplog r : list) {
				RechargeOplogResp rr = new RechargeOplogResp();
				BeanUtil.copyProperties(rr, r);

				if (r.getTime() > 0) {
					String time = sdf.format(new Date(r.getTime()));
					rr.setTime(time);
				}
				if (r.getRechargeTime() > 0) {
					String time = sdf.format(new Date(r.getRechargeTime()));
					rr.setRechargeTime(time);
				}
				result.add(rr);
			}
		}
		return result;
	}

}
