package com.integrate.admin.module.proportion.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrate.admin.module.proportion.dao.ProportionDao;
import com.integrate.admin.module.proportion.model.Proportion;

@Service
public class ProportionService {

	@Autowired
	private ProportionDao proportionDao;

	public Proportion getProportion() {
		Proportion p = new Proportion();
		Map<String, Object> proportion = proportionDao.getProportion();
		if (proportion != null) {
			p.setFixed(Integer.parseInt(proportion.get("fixed") + ""));
			p.setGive(Integer.parseInt(proportion.get("give") + ""));
		}
		return p;
	}
	
	public boolean update(int fixed, int give){
		return proportionDao.Update(fixed, give);
	}
}
