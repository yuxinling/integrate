package com.integrate.admin.controller.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.integrate.admin.util.ObjectExcelView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.city.model.AreaInfo;
import com.integrate.admin.module.city.model.CityInfo;
import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.rechrage.model.RechrageResp;
import com.integrate.admin.module.rechrage.service.RechrageService;
import com.integrate.admin.module.user.model.UserBase;
import com.integrate.admin.module.user.model.UserResp;
import com.integrate.admin.module.user.service.UserService;
import com.integrate.admin.module.withdrawals.model.WithdrawalsResp;
import com.integrate.admin.module.withdrawals.service.WithdrawalService;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.PageData;
import com.integrate.url.UrlCommand;

@Controller
public class StatisticsAdminController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	String menuUrl = UrlCommand.statistics_user; // 菜单地址(权限用)
	
	@Autowired
	private UserService userService;
	@Autowired
	private CityService cityService;
	@Autowired
	private RechrageService rechrageService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private WithdrawalService withdrawalService;

	@RequestMapping(value = UrlCommand.statistics_user)
	public ModelAndView userlist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();

		String citycode = request.getParameter("cityCode");
		String areacode = request.getParameter("areaCode");
		String provinceCode = request.getParameter("provinceCode");
		long begin = 0;
		long end = System.currentTimeMillis();

		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
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
		if (StringUtils.isNumeric(areacode)) {
			pd.put("areacode", areacode);
		}
		if (StringUtils.isNumeric(citycode)) {
			pd.put("citycode", citycode);
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
		mv.setViewName("statistics/user_list");
		mv.addObject("begin", beginTime);
		mv.addObject("end", endTime);
		mv.addObject("varList", userList);

		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = UrlCommand.statistics_user_excel)
	public ModelAndView excelUserlist() throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();

		String citycode = pd.getString("cityCode");
		String areacode = pd.getString("areaCode");
		long begin = 0;
		long end = System.currentTimeMillis();

		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
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
		if (StringUtils.isNumeric(areacode)) {
			pd.put("areacode", areacode);
		}
		if (StringUtils.isNumeric(citycode)) {
			pd.put("citycode", citycode);
		}

		List<UserResp> userList = userService.getExcelUserList(pd);

		ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("姓名"); 		//1
		titles.add("手机号");  		//2
		titles.add("区域");  		//3
		titles.add("积分");  		//4
		titles.add("注册时间");			//5
		dataMap.put("titles", titles);

		List<PageData> varList = new ArrayList<PageData>();
		for(UserResp st : userList){
			PageData vpd = new PageData();
			vpd.put("var1", st.getNickName());		//1
			vpd.put("var2", st.getMobile());		//2
			vpd.put("var3", st.getArea());		//3
			vpd.put("var4", String.valueOf(st.getIntegrate()));		//4
			vpd.put("var5", st.getTime());			//5

			varList.add(vpd);
		}
		dataMap.put("varList", varList);

		mv = new ModelAndView(erv,dataMap);

		return mv;
	}

	/**
	 * 0:充值，1：兑换
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = UrlCommand.statistics_rechrage)
	public ModelAndView exchangelist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();
		String citycode = request.getParameter("cityCode");
		String areacode = request.getParameter("areaCode");
		String provinceCode = request.getParameter("provinceCode");
		String proxyUserId = request.getParameter("proxyUserId");
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
		if (StringUtils.isNumeric(areacode)) {
			pd.put("areacode", areacode);
		}

		if (StringUtils.isNumeric(proxyUserId)) {
			pd.put("proxyUserId", proxyUserId);
		}
		if (StringUtils.isNumeric(citycode)) {
			pd.put("citycode", citycode);
		}
		pd.put("begin", begin);
		pd.put("end", end);
		page.setPd(pd);
		List<RechrageResp> rechrageList = rechrageService.getRechrageList(page);
		Map<Integer, String> provinceMap = cityService.getProvinceCodeAndNameMap();
		logger.warn("pcode :[{}],  ccode:[{}]", provinceCode, citycode);
		if (StringUtils.isNumeric(provinceCode) && StringUtils.isNumeric(citycode)) {

			List<CityInfo> cityMap = cityService.getCityListByProvinceCode(Integer.parseInt(provinceCode));
			List<AreaInfo> areaInfoList = cityService.getAreaInfoList(Integer.parseInt(citycode));
			List<UserBase> list = userService.getUserBaseByArea(Integer.parseInt(areacode));
			mv.addObject("cityMap", cityMap);
			mv.addObject("areaInfoList", areaInfoList);
			mv.addObject("provincecode", provinceCode);
			mv.addObject("citycode", citycode);
			mv.addObject("areacode", areacode);
			mv.addObject("proxy", list);
			mv.addObject("proxyUserId", proxyUserId);
		}
		mv.addObject("provinceMap", provinceMap);
		logger.warn(" size:[{}]", rechrageList.size());
		if (type.equals("0")) {
			int area = 0;
			long pid = 0;
			if (StringUtils.isNumeric(areacode)) {
				area = Integer.parseInt(areacode);
			}
			if (StringUtils.isNumeric(proxyUserId)) {
				pid = Long.parseLong(proxyUserId);
			}
			int rechrageTotalV = userService.rechrageTotalV(area, pid, begin, end);
			int returnTotal = userService.returnTotal(area, pid, begin, end);

			mv.addObject("rechrageTotal", rechrageTotalV);
			mv.addObject("returnTotal", rechrageTotalV * 4 - returnTotal);
			mv.setViewName("statistics/recharge");
		} else {
			int exchangeTotalV = userService.exchangeTotalV(begin, end);
			mv.addObject("exchangeTotalV", exchangeTotalV);

			mv.setViewName("statistics/exchange");
		}

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

	/**
	 * 0:充值，1：兑换
	 *
	 * @throws ParseException
	 */
	@RequestMapping(value = UrlCommand.statistics_rechrage_excel)
	public ModelAndView excelExchangelist() throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();
		String citycode = pd.getString("cityCode");
		String areacode = pd.getString("areaCode");
		String proxyUserId = pd.getString("proxyUserId");
		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
		String type = pd.getString("type");
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
		if (StringUtils.isNumeric(areacode)) {
			pd.put("areaCode", areacode);
		} else {
			pd.remove("areaCode");
		}

		if (StringUtils.isNumeric(proxyUserId)) {
			pd.put("proxyUserId", proxyUserId);
		} else {
			pd.remove("proxyUserId");
		}

		if (StringUtils.isNumeric(citycode)) {
			pd.put("citycode", citycode);
		}
		pd.put("begin", begin);
		pd.put("end", end);
		List<RechrageResp> rechrageList = rechrageService.getExcelRechrageList(pd);

		ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();

		if (type.equals("0")) {
			int area = 0;
			long pid = 0;
			if (StringUtils.isNumeric(areacode)) {
				area = Integer.parseInt(areacode);
			}
			if (StringUtils.isNumeric(proxyUserId)) {
				pid = Long.parseLong(proxyUserId);
			}
			int rechrageTotalV = userService.rechrageTotalV(area, pid, begin, end);
			int returnTotal = userService.returnTotal(area, pid, begin, end);

			titles.add("姓名"); 		//1
			titles.add("手机");  		//2
			titles.add("区域");  		//3
			titles.add("区域代理人");  		//4
			titles.add("充值积分");			//5
			titles.add("日期");			//6
			dataMap.put("titles", titles);

			List<PageData> varList = new ArrayList<PageData>();
			for(RechrageResp st : rechrageList){
				PageData vpd = new PageData();
				vpd.put("var1", st.getName());		//1
				vpd.put("var2", st.getMobile());		//2
				vpd.put("var3", st.getArea());		//3
				vpd.put("var4", st.getProxyName());		//4
				vpd.put("var5", String.valueOf(st.getMoney()));			//5
				vpd.put("var6", st.getTime());	//6
				varList.add(vpd);
			}

			PageData vpd = new PageData();
			vpd.put("var1", "充值总金额:");		//1
			vpd.put("var2", String.valueOf(rechrageTotalV));		//2
			vpd.put("var3", "已退还的积分:");		//3
			vpd.put("var4", String.valueOf(rechrageTotalV * 4 - returnTotal));		//4

			PageData emptyRow = new PageData();
			varList.add(emptyRow);
			varList.add(emptyRow);
			varList.add(vpd);

			dataMap.put("varList", varList);

		} else {
			int exchangeTotalV = userService.exchangeTotalV(begin, end);

			titles.add("姓名"); 		//1
			titles.add("手机");  		//2
			titles.add("兑换积分");  		//3
			titles.add("日期");  		//4
			dataMap.put("titles", titles);

			List<PageData> varList = new ArrayList<PageData>();
			for(RechrageResp st : rechrageList){
				PageData vpd = new PageData();
				vpd.put("var1", st.getName());		//1
				vpd.put("var2", st.getMobile());		//2
				vpd.put("var3", String.valueOf(st.getMoney()));		//3
				vpd.put("var4", st.getTime());		//4
				varList.add(vpd);
			}

			PageData vpd = new PageData();
			vpd.put("var1", "兑换总积分:");		//1
			vpd.put("var2", String.valueOf(exchangeTotalV));		//2

			PageData emptyRow = new PageData();
			varList.add(emptyRow);
			varList.add(emptyRow);
			varList.add(vpd);

			dataMap.put("varList", varList);
		}

		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	
	
	
	
	
	@RequestMapping(value = UrlCommand.statistics_withdrawls)
	public ModelAndView withdrawlslist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();

		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
		logger.warn("beginTime:[{}],endTime:[{}]", beginTime, endTime);
		
		if (StringUtils.isNotBlank(beginTime)) {

			Date b = sdf.parse(beginTime);
			begin = b.getTime();
		}
		if (StringUtils.isNotBlank(endTime)) {
			Date b = sdf.parse(endTime);
			end = b.getTime()+1000*60*60*24;
		}

		pd.put("begin", begin);
		pd.put("end", end);
		page.setPd(pd);
		List<WithdrawalsResp> withdrawlsList = withdrawalService.getWithdrawlsList(page);
		logger.warn(" size:[{}]", withdrawlsList.size());
		mv.setViewName("statistics/withdrawls");
		if (beginTime == null||StringUtils.isBlank(beginTime)) {
			beginTime="";
		}
		if (endTime == null||StringUtils.isBlank(endTime)) {
			endTime="";
		}
		int withDrawlsTotal = withdrawalService.getWithDrawlsTotal(0, begin, end);
		int withDrawlsTotal1 = withdrawalService.getWithDrawlsTotal(1, begin, end);
		mv.addObject("withDrawlsTotal", withDrawlsTotal);
		mv.addObject("withDrawlsTotal1", withDrawlsTotal1);
		mv.addObject("Total", withDrawlsTotal1+withDrawlsTotal);
		mv.addObject("begin", beginTime);
		mv.addObject("end", endTime);
		mv.addObject("varList", withdrawlsList);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = UrlCommand.statistics_withdrawls_excel)
	public ModelAndView excelWithdrawlslist() throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();

		String beginTime = pd.getString("begin");
		String endTime = pd.getString("end");
		logger.warn("beginTime:[{}],endTime:[{}]", beginTime, endTime);

		if (StringUtils.isNotBlank(beginTime)) {

			Date b = sdf.parse(beginTime);
			begin = b.getTime();
		}
		if (StringUtils.isNotBlank(endTime)) {
			Date b = sdf.parse(endTime);
			end = b.getTime()+1000*60*60*24;
		}

		pd.put("begin", begin);
		pd.put("end", end);
		List<WithdrawalsResp> withdrawlsList = withdrawalService.getExcelWithdrawlsList(pd);
		int withDrawlsTotal = withdrawalService.getWithDrawlsTotal(0, begin, end);
		int withDrawlsTotal1 = withdrawalService.getWithDrawlsTotal(1, begin, end);

		ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();

		titles.add("姓名"); 		//1
		titles.add("区域");  		//2
		titles.add("持卡人");  		//3
		titles.add("银行");  		//4
		titles.add("开户行");			//5
		titles.add("银行卡号");			//6
		titles.add("提现金额");			//7
		titles.add("日期");			//8
		titles.add("提现状态");			//9
		dataMap.put("titles", titles);

		List<PageData> varList = new ArrayList<PageData>();
		for(WithdrawalsResp st : withdrawlsList){
			PageData vpd = new PageData();
			vpd.put("var1", st.getNickName());		//1
			vpd.put("var2", st.getArea());		//2
			vpd.put("var3", st.getCardholder());		//3
			vpd.put("var4", st.getBankName());		//4
			vpd.put("var5", st.getBranch());		//5
			vpd.put("var6", st.getNumber());		//6
			vpd.put("var7", String.valueOf(st.getMoney()));		//7
			vpd.put("var8", st.getTime());	//8
			vpd.put("var9", st.getState()==1 ? "已转账" : st.getState()==0 ? "未转账" : "");			//9
			varList.add(vpd);
		}

		PageData vpd = new PageData();
		vpd.put("var1", "提现总金额:");		//1
		vpd.put("var2", String.valueOf(withDrawlsTotal1+withDrawlsTotal));		//2
		vpd.put("var3", "已转账:");		//3
		vpd.put("var4", String.valueOf(withDrawlsTotal1));
		vpd.put("var5", "未转账:");		//5
		vpd.put("var6", String.valueOf(withDrawlsTotal));	//6

		PageData emptyRow = new PageData();
		varList.add(emptyRow);
		varList.add(emptyRow);
		varList.add(vpd);

		dataMap.put("varList", varList);

//		mv.addObject("withDrawlsTotal", withDrawlsTotal);
//		mv.addObject("withDrawlsTotal1", withDrawlsTotal1);
//		mv.addObject("Total", withDrawlsTotal1+withDrawlsTotal);


		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
}
