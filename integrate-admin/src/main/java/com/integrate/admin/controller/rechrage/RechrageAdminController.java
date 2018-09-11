package com.integrate.admin.controller.rechrage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.user.model.UserBase;
import com.integrate.admin.module.user.service.UserService;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.entity.Page;
import com.integrate.admin.entity.system.AdminUser;
import com.integrate.admin.module.rechrage.model.RechrageResp;
import com.integrate.admin.module.rechrage.service.RechrageService;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.PageData;
import com.integrate.url.UrlCommand;

@Controller
public class RechrageAdminController extends BaseController {

	@Autowired
	private RechrageService rechrageService;
	@Autowired
	private UserService userService;
	@Autowired
	private CityService cityService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Logger logger = LoggerFactory.getLogger(getClass());
	String menuUrl = UrlCommand.rechrage_exchange; // 菜单地址(权限用)

	/**
	 * 0:充值，1：兑换
	 * 
	 * @throws ParseException
	 */

	@RequestMapping(value = UrlCommand.rechrage_exchange)
	public ModelAndView exchangelist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();

		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
		String type = request.getParameter("type");
		logger.warn("beginTime:[{}],endTime:[{}],type:[{}]", beginTime, endTime, type);
		if (StringUtils.isNotBlank(type)) {
			pd.put("type", type.trim());
		}
		if (StringUtils.isNotBlank(beginTime)) {

			Date b = sdf.parse(beginTime);
			begin = b.getTime();

		}
		if (StringUtils.isNotBlank(endTime)) {
			Date b = sdf.parse(endTime);
			end = b.getTime() + 1000 * 60 * 60 * 24;
		}

		pd.put("begin", begin);
		pd.put("end", end);
		page.setPd(pd);
		List<RechrageResp> rechrageList = rechrageService.getRechrageList(page);
		logger.warn(" size:[{}]", rechrageList.size());
		mv.setViewName("recharge/exchange");
		mv.addObject("type", type);
		mv.addObject("varList", rechrageList);
		mv.addObject("pd", pd);
		if (beginTime == null || StringUtils.isBlank(beginTime)) {
			beginTime = "";
		}
		if (endTime == null || StringUtils.isBlank(endTime)) {
			endTime = "";
		}
		mv.addObject("begin", beginTime);
		mv.addObject("end", endTime);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = UrlCommand.rechrage)
	public ModelAndView rechargelist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();

		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
		String type = request.getParameter("type");
		logger.warn("beginTime:[{}],endTime:[{}],type:[{}]", beginTime, endTime, type);
		if (StringUtils.isNotBlank(type)) {
			pd.put("type", type.trim());
		}
		if (StringUtils.isNotBlank(beginTime)) {

			Date b = sdf.parse(beginTime);
			begin = b.getTime();

		}
		if (StringUtils.isNotBlank(endTime)) {
			Date b = sdf.parse(endTime);
			end = b.getTime() + 1000 * 60 * 60 * 24;
		}

		pd.put("begin", begin);
		pd.put("end", end);
		page.setPd(pd);
		List<RechrageResp> rechrageList = rechrageService.getRechrageList(page);
		logger.warn(" size:[{}]", rechrageList.size());
		mv.setViewName("recharge/recharge");
		mv.addObject("type", type);
		mv.addObject("varList", rechrageList);
		mv.addObject("pd", pd);
		if (beginTime == null || StringUtils.isBlank(beginTime)) {
			beginTime = "";
		}
		if (endTime == null || StringUtils.isBlank(endTime)) {
			endTime = "";
		}
		mv.addObject("begin", beginTime);
		mv.addObject("end", endTime);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = UrlCommand.human_recharge_goto_edit)
	public ModelAndView humanRechargeGotoEdit(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		String userId = request.getParameter("userId");

		Map<Integer, String> provinceMap = cityService.getProvinceCodeAndNameMap();

		UserBase userBase = userService.getUserBase(Long.valueOf(userId));
		mv.addObject("userBase", userBase);
		mv.addObject("provinceMap", provinceMap);
		mv.setViewName("recharge/human_recharge");

		return mv;
	}

	@RequestMapping(value = UrlCommand.human_recharge_edit)
	public ModelAndView humanRechargeEdit(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String proxy = request.getParameter("proxy");
		String areaCode = request.getParameter("areaCode");
		String money = request.getParameter("money");

		if (!StringUtils.isNumeric(proxy) || !StringUtils.isNumeric(userId) || !StringUtils.isNumeric(areaCode) || !StringUtils.isNumeric(money)) {
			throw new BusinessException(SysMsgEnumType.PARAM_LACK);
		}

		rechrageService.humanRecharge(Long.valueOf(userId), Integer.valueOf(areaCode), Integer.valueOf(money),
				Long.parseLong(proxy));
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	 /**
     * 设置 取消 冻结
     * @param request
     * @return
     */
    
    @RequestMapping(value = UrlCommand.rechrage_isfreeze)
	public ModelAndView updateIsFreeze(HttpServletRequest request) {

		String id = request.getParameter("id");

		String isFreeze=request.getParameter("isFreeze");
		userService.Rechrageisfreeze(Integer.parseInt(isFreeze), Long.parseLong(id));
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
    
    /**
     * 充值积分调整
     * @param request
     * @return
     */
    
    @RequestMapping(value = UrlCommand.rechrage_edit)
    public ModelAndView rechrageEdit(HttpServletRequest request) {
    	
    	String rid = request.getParameter("id");
    	String opType = request.getParameter("opType");
    	String changeMoney = request.getParameter("changeMoney");
    	
    	AdminUser user = (AdminUser)request.getSession().getAttribute(Const.SESSION_USER);
    	this.rechrageService.adjustRecharge(Long.parseLong(rid), Integer.parseInt(opType), Integer.parseInt(changeMoney), user.getUSERNAME());
    	
    	ModelAndView mv = this.getModelAndView();
    	mv.addObject("msg", "success");
    	mv.setViewName("save_result");
    	return mv;
    }
    
    @RequestMapping(value = UrlCommand.rechrage_to_edit)
    public ModelAndView rechrageToEdit(HttpServletRequest request) {
    	
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	String money = request.getParameter("money");
    	
    	ModelAndView mv = this.getModelAndView();
    	mv.addObject("id", id);
    	mv.addObject("name", name);
    	mv.addObject("money", money);
    	mv.setViewName("recharge/recharge_edit");
    	return mv;
    }
    
    @RequestMapping(value = UrlCommand.exchange_to_edit)
    public ModelAndView exchangeToEdit(HttpServletRequest request) {
    	
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	String mobile = request.getParameter("mobile");
    	String status = request.getParameter("status");
    	String money = request.getParameter("money");
    	
    	ModelAndView mv = this.getModelAndView();
    	mv.addObject("id", id);
    	mv.addObject("name", name);
    	mv.addObject("mobile", mobile);
    	mv.addObject("money", money);
    	mv.addObject("status", status);
    	mv.setViewName("recharge/exchange_edit");
    	return mv;
    }
    
    @RequestMapping(value = UrlCommand.exchange_edit)
    public ModelAndView exchangeEdit(HttpServletRequest request) {
    	
    	String rid = request.getParameter("id");
    	String status = request.getParameter("status");
    	
    	this.rechrageService.updateRechargeStatus(Long.parseLong(rid), Integer.parseInt(status));
    	ModelAndView mv = this.getModelAndView();
    	mv.addObject("msg", "success");
    	mv.setViewName("save_result");
    	
    	return mv;
    }

}
