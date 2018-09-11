package com.integrate.admin.controller.withdrawls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.entity.Page;
import com.integrate.admin.module.city.model.AreaInfo;
import com.integrate.admin.module.city.model.CityInfo;
import com.integrate.admin.module.city.service.CityService;
import com.integrate.admin.module.withdrawals.model.WithdrawalsResp;
import com.integrate.admin.module.withdrawals.service.WithdrawalService;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.ObjectExcelView;
import com.integrate.admin.util.PageData;
import com.integrate.url.UrlCommand;

@Controller
public class WithdrawlsAdminController extends BaseController{

	@Autowired
	private WithdrawalService withdrawalService;
	@Autowired
	private CityService cityService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Logger logger = LoggerFactory.getLogger(getClass());
	String menuUrl = UrlCommand.withdrawls; // 菜单地址(权限用)

	@RequestMapping(value = UrlCommand.withdrawls)
	public ModelAndView exchangelist(Page page, HttpServletRequest request) throws ParseException {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		long begin = 0;
		long end = System.currentTimeMillis();

		String citycode = request.getParameter("cityCode");
		String areacode = request.getParameter("areaCode");
		String provinceCode = request.getParameter("provinceCode");
		
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
		
		if (StringUtils.isNumeric(areacode)) {
			pd.put("areacode", areacode);
		}
		if (StringUtils.isNumeric(citycode)) {
			pd.put("citycode", citycode);
		}

		pd.put("begin", begin);
		pd.put("end", end);
		page.setPd(pd);
		List<WithdrawalsResp> withdrawlsList = withdrawalService.getWithdrawlsList(page);
		logger.warn(" size:[{}]", withdrawlsList.size());
		mv.setViewName("withdrawls/withdrawls");
		if (beginTime == null||StringUtils.isBlank(beginTime)) {
			beginTime="";
		}
		if (endTime == null||StringUtils.isBlank(endTime)) {
			endTime="";
		}
		
		
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
		
		
		
		mv.addObject("begin", beginTime);
		mv.addObject("end", endTime);
		mv.addObject("varList", withdrawlsList);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限

		return mv;
	}

	@RequestMapping(value = UrlCommand.withdrawlsEdit)
	public ModelAndView update(HttpServletRequest request) {

		String id = request.getParameter("id");
		withdrawalService.update(id);
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	
	
	/*
	 * 导出会员信息到excel
	 * 
	 * @return
	 */
	@RequestMapping(value =UrlCommand.withdrawls_excel )
	public ModelAndView exportExcel(Page page,HttpServletRequest request) throws ParseException {
		ModelAndView mv;
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

	

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();

		titles.add("姓名"); // 1
		titles.add("手机"); // 2
		titles.add("区域"); // 3
		titles.add("银行"); // 4
		titles.add("银行卡号"); // 4
		titles.add("金额"); // 4
		titles.add("日期"); // 4
		dataMap.put("titles", titles);

		
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < withdrawlsList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", withdrawlsList.get(i).getNickName()+""); // 1
			vpd.put("var2", withdrawlsList.get(i).getMobile()+""); // 2
			vpd.put("var3", withdrawlsList.get(i).getArea()+""); // 3
			vpd.put("var4", withdrawlsList.get(i).getBankName()+""); // 4
			vpd.put("var5", withdrawlsList.get(i).getNumber()+""); // 4
			vpd.put("var6", withdrawlsList.get(i).getMoney()+""); // 4
			vpd.put("var7", withdrawlsList.get(i).getTime()+""); // 4
			varList.add(vpd);
		}

		dataMap.put("varList", varList);

		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
}
