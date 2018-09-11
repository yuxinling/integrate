package com.integrate.admin.controller.rechrage;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.rechrage.model.RechargeOplogResp;
import com.integrate.admin.module.rechrage.service.RechrageOplogService;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.PageData;
import com.integrate.url.UrlCommand;

@Controller
public class RechrageOplogController extends BaseController {

	@Autowired
	private RechrageOplogService rechrageOplogService;

	@RequestMapping(value = UrlCommand.rechrage_oplogs)
	public ModelAndView exchangelist(Page page, HttpServletRequest request) throws ParseException {
		String mobile = request.getParameter("mobile");
		
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		if(StringUtils.isNotBlank(mobile)){
			pd.put("mobile", mobile);
		}
		
		page.setPd(pd);
		List<RechargeOplogResp> rechrageOplogs = rechrageOplogService.getRechrageOplogs(page);
		logger.warn(" size:[{}]", rechrageOplogs.size());
		mv.setViewName("recharge/recharge_oplog");
		mv.addObject("varList", rechrageOplogs);
		mv.addObject("pd", pd);
		mv.addObject("mobile", mobile);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}
}
