package com.integrate.admin.controller.user;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.city.model.AreaInfo;
import com.integrate.admin.module.city.model.CityInfo;
import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.rechrage.model.Rechrage;
import com.integrate.admin.module.rechrage.model.RechrageResp;
import com.integrate.admin.module.rechrage.model.UserRecordResp;
import com.integrate.admin.module.user.model.UserBase;
import com.integrate.admin.module.user.model.UserResp;
import com.integrate.admin.module.user.service.UserService;
import com.integrate.admin.module.withdrawals.model.WithdrawalsResp;
import com.integrate.admin.module.withdrawals.service.WithdrawalService;
import com.integrate.admin.util.AppUtil;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.PageData;
import com.integrate.exception.BusinessException;
import com.integrate.url.UrlCommand;

@Controller
public class UserAdminController extends BaseController {
	Logger logger = LoggerFactory.getLogger(getClass());
	String menuUrl = UrlCommand.user_list; // 菜单地址(权限用)

	@Autowired
	private UserService userService;
	@Autowired
	private CityService cityService;

	@Autowired
	private WithdrawalService withdrawalService;

	@RequestMapping(value = UrlCommand.user_list)
	public ModelAndView userlist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();

		String citycode = request.getParameter("cityCode");
		String areacode = request.getParameter("areaCode");
		String provinceCode = request.getParameter("provinceCode");
		String isAreaProxy = request.getParameter("isAreaProxy");
		String mobile = request.getParameter("mobile");

		if (StringUtils.isNumeric(mobile)) {
			pd.put("mobile", mobile);
		}
		if (StringUtils.isNumeric(areacode)) {
			pd.put("areacode", areacode);
		}
		if (StringUtils.isNumeric(citycode)) {
			pd.put("citycode", citycode);
		}
		if (StringUtils.isNumeric(isAreaProxy)) {
			pd.put("isAreaProxy", isAreaProxy);
		}

		page.setPd(pd);
		List<UserResp> userList = userService.getUserList(page);
		Map<Integer, String> provinceMap = cityService.getProvinceCodeAndNameMap();
		logger.warn("pcode :[{}],  ccode:[{}]", provinceCode, citycode);
		if (StringUtils.isNumeric(provinceCode) && StringUtils.isNumeric(citycode)) {

			List<CityInfo> cityMap = cityService.getCityListByProvinceCode(Integer.parseInt(provinceCode));
			List<AreaInfo> areaInfoList = cityService.getAreaInfoList(Integer.parseInt(citycode));

			mv.addObject("cityMap", cityMap);
			mv.addObject("areaInfoList", areaInfoList);
			mv.addObject("provincecode", provinceCode);
			mv.addObject("citycode", citycode);
			mv.addObject("areacode", areacode);

		}
		mv.addObject("provinceMap", provinceMap);
		mv.setViewName("user/user_list");

		mv.addObject("varList", userList);
		mv.addObject("isAreaProxy", isAreaProxy);
		mv.addObject("mobile", mobile);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = UrlCommand.city_list)
	public void getCityList(PrintWriter out, HttpServletRequest req) {

		int provinceCode = Integer.valueOf(StringUtils.defaultString(req.getParameter("provinceCode"), "0"));
		List<CityInfo> cityMap = cityService.getCityListByProvinceCode(provinceCode);

		out.write(JSON.toJSONString(cityMap));
		out.close();
	}

	@RequestMapping(value = UrlCommand.area_list)
	public void getAreaList(PrintWriter out, HttpServletRequest req) {

		int cityCode = Integer.valueOf(StringUtils.defaultString(req.getParameter("cityCode"), "0"));
		List<AreaInfo> areaInfoList = cityService.getAreaInfoList(cityCode);

		out.write(JSON.toJSONString(areaInfoList));
		out.close();
	}

	/**
	 * 根据区域获取代理人
	 * 
	 */
	@RequestMapping(value = UrlCommand.proxy_list)
	public void getProxyList(PrintWriter out, HttpServletRequest req) {

		int areaCode = Integer.valueOf(StringUtils.defaultString(req.getParameter("areaCode"), "0"));

		List<UserBase> list = userService.getUserBaseByArea(areaCode);
		out.write(JSON.toJSONString(list));
		out.close();
	}

	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UrlCommand.user_to_edit)
	public ModelAndView goAdd(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		String id = request.getParameter("id");
		String hasproxy = request.getParameter("hasproxy");
		String isAreaProxy = request.getParameter("isAreaProxy");
		String giveDay = request.getParameter("giveDay");
		String fixDay = request.getParameter("fixDay");
		mv.addObject("uid", id);
		mv.addObject("hasproxy", hasproxy);
		mv.addObject("isAreaProxy", isAreaProxy);
		mv.addObject("giveDay", giveDay);
		mv.addObject("fixDay", fixDay);
		mv.setViewName("user/user_edit");

		return mv;
	}

	/**
	 * 设置 取消 区域代理
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = UrlCommand.user_edit)
	public ModelAndView update(HttpServletRequest request) {

		String id = request.getParameter("uid");
		String isAreaProxy = request.getParameter("isAreaProxy");
		String giveDay = request.getParameter("giveDay");
		String fixDay = request.getParameter("fixDay");
		
		userService.updateUser(Long.parseLong(id), Integer.parseInt(isAreaProxy), 
				Integer.parseInt(fixDay), Integer.parseInt(giveDay));
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**************************** 2.14 **************************************/

	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UrlCommand.user_isfreeze_to_edit)
	public ModelAndView isfreezeToEdit(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		String id = request.getParameter("userId");
		String isFreeze = request.getParameter("isFreeze");
		mv.addObject("userId", id);
		mv.addObject("isFreeze", isFreeze);
		mv.setViewName("user/isfreeze_edit");

		return mv;
	}

	/**
	 * 设置 取消 冻结
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = UrlCommand.user_isfreeze_edit)
	public ModelAndView updateIsFreeze(HttpServletRequest request) {

		String id = request.getParameter("userId");

		String isFreeze = request.getParameter("isFreeze");
		userService.isfreeze(Integer.parseInt(isFreeze), Integer.parseInt(id));
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = UrlCommand.user_to_record)
	public ModelAndView toRecord(Page page, HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		long userId = Long.parseLong(StringUtils.defaultIfBlank(pd.getString("userId"), "0"));
		pd.put("type", "0");
		pd.put("userId", userId);
		page.setPd(pd);
		List<UserRecordResp> list = userService.getRechrageByUserId(page);
		mv.addObject("type", 0);
		mv.addObject("userId", userId);
		mv.addObject("varList", list);
		mv.setViewName("user/user_record");
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = UrlCommand.user_record)
	public ModelAndView UserRecord(Page page, HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		long userId = Long.parseLong(StringUtils.defaultIfBlank(pd.getString("userId"), "0"));
		long type = Long.parseLong(StringUtils.defaultIfBlank(pd.getString("type"), "0"));

		if (type == 0) {
			pd.put("type", "0");
		} else {
			pd.put("type", type);
		}

		pd.put("userId", userId);
		page.setPd(pd);

		if (type < 2) {
			List<UserRecordResp> list = userService.getRechrageByUserId(page);
			mv.addObject("varList", list);
		} else {
			List<WithdrawalsResp> withdrawlsList = withdrawalService.getWithdrawlsList(page);
			mv.addObject("varList", withdrawlsList);
		}

		mv.addObject("userId", userId);
		mv.addObject("type", type);
		mv.setViewName("user/user_record");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value = UrlCommand.user_freeze_all,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object freezeAllUser() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 200);
		result.put("msg", "解冻所有用户失败！");
		try {
			if(userService.freezeAll(1)){
				result.put("msg", "解冻所有用户成功！");
			}
		} catch (Exception e) {
			if(e instanceof BusinessException){
				BusinessException be = (BusinessException) e;
				result.put("msg", be.getMessage());
			}
		}
		
		return AppUtil.returnObject(new PageData(), result);
	}
	
	@RequestMapping(value = UrlCommand.user_un_freeze_all,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object unFreezeAllUser() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 200);
		result.put("msg", "解冻所有用户失败！");
		try {
			if(userService.freezeAll(0)){
				result.put("msg", "解冻所有用户成功！");
			}
		} catch (Exception e) {
			if(e instanceof BusinessException){
				BusinessException be = (BusinessException) e;
				result.put("msg", be.getMessage());
			}
		}
		
		return AppUtil.returnObject(new PageData(), result);
	}

}
