package com.integrate.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.integrate.common.util.StringUtil;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.model.BankCardInfo;
import com.integrate.web.model.BankName;
import com.integrate.web.model.Integrate;
import com.integrate.web.model.ProxyInfo;
import com.integrate.web.model.User;
import com.integrate.web.model.UserBaseInfo;
import com.integrate.web.service.UserService;

@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.register_code, method = RequestMethod.POST)
	@ResponseBody
	public void registerCode(HttpServletRequest request, HttpServletResponse response) {
		String mobile = StringUtil.getString(request.getParameter("mobile"));
		if (!StringUtils.isNumeric(mobile)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.generateRegisterCode(mobile);
		Message.writeSuccess(response);

	}

	@RequestMapping(value = UrlCommand.findpwd_code, method = RequestMethod.POST)
	@ResponseBody
	public void findPWDcode(HttpServletRequest request, HttpServletResponse response) {
		String mobile = StringUtil.getString(request.getParameter("mobile"));
		if (!StringUtils.isNumeric(mobile)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.getpwdCode(mobile);
		Message.writeSuccess(response);

	}

	@RequestMapping(value = UrlCommand.update_pwd, method = RequestMethod.POST)
	@ResponseBody
	public void updatePWD(HttpServletRequest request, HttpServletResponse response) {
		String mobile = StringUtil.getString(request.getParameter("mobile"));
		String code = StringUtil.getString(request.getParameter("code"));
		String pwd = StringUtil.getString(request.getParameter("pwd"));
		if (!StringUtils.isNumeric(mobile)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		if (StringUtils.isBlank(pwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}

		boolean updatePwd = userService.updatePwd(code, mobile, pwd);

		Message.writeSuccess(response);

	}

	/**
	 * 修改登录密码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.update_loginpwd, method = RequestMethod.POST)
	@ResponseBody
	public void updateLoginPWD(HttpServletRequest request, HttpServletResponse response) {

		String userId = StringUtil.getString(request.getParameter("userId"));
		String oldpwd = StringUtil.getString(request.getParameter("oldPwd"));
		String newpwd = StringUtil.getString(request.getParameter("newPwd"));
		if (StringUtils.isBlank(oldpwd) || StringUtils.isBlank(newpwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		if (StringUtils.isBlank(userId) || !StringUtils.isNumeric(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.updateLoginPwd(Long.parseLong(userId), oldpwd, newpwd);

		Message.writeSuccess(response);

	}

	/**
	 * 修改交易密码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.update_tradepwd, method = RequestMethod.POST)
	@ResponseBody
	public void updateTradePWD(HttpServletRequest request, HttpServletResponse response) {

		String userId = StringUtil.getString(request.getParameter("userId"));
		String oldpwd = StringUtil.getString(request.getParameter("oldPwd"));
		String newpwd = StringUtil.getString(request.getParameter("newPwd"));
		if (StringUtils.isBlank(oldpwd) || StringUtils.isBlank(newpwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		if (StringUtils.isBlank(userId) || !StringUtils.isNumeric(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.updateTradePwd(Long.parseLong(userId), oldpwd, newpwd);

		Message.writeSuccess(response);

	}

	@RequestMapping(value = UrlCommand.mobile_register, method = RequestMethod.POST)
	@ResponseBody
	public void mobile_register(HttpServletRequest request, HttpServletResponse response) {
		String mobile = StringUtil.getString(request.getParameter("mobile"));
		String regCode = StringUtil.getString(request.getParameter("regCode"));
		int cityCode = StringUtil.getInt(request.getParameter("cityCode"));
		int areaCode = StringUtil.getInt(request.getParameter("areaCode"));
		String nickname = StringUtil.getString(request.getParameter("nickname"));
		String loginPwd = StringUtil.getString(request.getParameter("loginPwd"));
		String tradePwd = StringUtil.getString(request.getParameter("tradePwd"));
		String recommendPerson = request.getParameter("recommendPerson");

		logger.warn("mobile_register mobile:{}, cityCode:{}, areaCode:{}", mobile, cityCode, areaCode);

		if (!StringUtils.isNumeric(mobile)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		if (StringUtils.isBlank(regCode) || StringUtils.isBlank(nickname) || StringUtils.isBlank(loginPwd)
				|| StringUtils.isBlank(tradePwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		User user = userService.registerUserByMobile(mobile, nickname, regCode, cityCode, areaCode, loginPwd, tradePwd,
				recommendPerson);
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, user);
	}

	@RequestMapping(value = UrlCommand.user_login, method = RequestMethod.POST)
	@ResponseBody
	public void UserLogin(HttpServletRequest request, HttpServletResponse response) {

		String mobile = StringUtil.getString(request.getParameter("mobile"));
		String loginPwd = StringUtil.getString(request.getParameter("loginPwd"));
		boolean userLogin = userService.UserLogin(mobile, loginPwd);
		if (!userLogin) {
			Message.writeError(response, SysMsgEnumType.LOGIN_FAIL);
			return;
		}
		UserBaseInfo userBaseInfo = userService.getUserBaseInfo(mobile);

		Message.writeMsg(response, SysMsgEnumType.SUCCESS, userBaseInfo);

	}

	@RequestMapping(value = UrlCommand.base_userInfo, method = RequestMethod.POST)
	@ResponseBody
	public void UserBaseInfo(HttpServletRequest request, HttpServletResponse response) {

		String userId = StringUtil.getString(request.getParameter("userId"));

		if (StringUtils.isBlank(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}

		UserBaseInfo userBaseInfo = userService.getUserBaseInfoById(Long.parseLong(userId));

		Message.writeMsg(response, SysMsgEnumType.SUCCESS, userBaseInfo);

	}

	@RequestMapping(value = UrlCommand.user_integrate, method = RequestMethod.POST)
	@ResponseBody
	public void UserIntegrate(HttpServletRequest request, HttpServletResponse response) {

		String userId = StringUtil.getString(request.getParameter("userId"));

		if (StringUtils.isBlank(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}

		Integrate userIntegrate = userService.getUserIntegrate(Long.parseLong(userId));
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, userIntegrate);

	}

	@RequestMapping(value = UrlCommand.user_bankinfo, method = RequestMethod.POST)
	@ResponseBody
	public void getUserBankInfo(HttpServletRequest request, HttpServletResponse response) {
		String userId = StringUtil.getString(request.getParameter("userId"));

		if (StringUtils.isBlank(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		BankCardInfo bankInfo = userService.getBankInfo(Long.parseLong(userId));
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, bankInfo);
	}

	@RequestMapping(value = UrlCommand.user_bondbank, method = RequestMethod.POST)
	@ResponseBody
	public void bondUserBank(HttpServletRequest request, HttpServletResponse response) {
		String userId = StringUtil.getString(request.getParameter("userId"));
		String cardholder = StringUtil.getString(request.getParameter("cardholder"));
		String number = StringUtil.getString(request.getParameter("number"));
		String branch = StringUtil.getString(request.getParameter("branch"));
		String bankName = StringUtil.getString(request.getParameter("bankName"));
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(number) | StringUtils.isBlank(cardholder)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		boolean addBank = userService.addBank(Long.parseLong(userId), number, cardholder, branch, bankName);
		Message.writeSuccess(response);
	}

	@RequestMapping(value = UrlCommand.update_bankinfo, method = RequestMethod.POST)
	@ResponseBody
	public void updateUserBank(HttpServletRequest request, HttpServletResponse response) {
		String id = StringUtil.getString(request.getParameter("bid"));
		String cardholder = StringUtil.getString(request.getParameter("cardholder"));
		String number = StringUtil.getString(request.getParameter("number"));
		String branch = StringUtil.getString(request.getParameter("branch"));
		String bankName = StringUtil.getString(request.getParameter("bankName"));
		if (StringUtils.isBlank(id) || StringUtils.isBlank(number) | StringUtils.isBlank(cardholder)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.updateBank(Integer.parseInt(id), number, cardholder, branch,bankName);
		Message.writeSuccess(response);
	}

	/***************************************
	 * 2.13,14新增功能
	 ****************************************/

	/**
	 * 根据手机修改 交易密码
	 */

	@RequestMapping(value = UrlCommand.update_trade_pwd_code, method = RequestMethod.POST)
	@ResponseBody
	public void getTradecode(HttpServletRequest request, HttpServletResponse response) {
		String mobile = StringUtil.getString(request.getParameter("mobile"));
		if (!StringUtils.isNumeric(mobile)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.gettradepwdCode(mobile);// 暂时用
		Message.writeSuccess(response);

	}

	/**
	 * 修改交易密码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.update_trade_pwd, method = RequestMethod.POST)
	@ResponseBody
	public void updateTradePWDBymobile(HttpServletRequest request, HttpServletResponse response) {
		String mobile = StringUtil.getString(request.getParameter("mobile"));
		String code = StringUtil.getString(request.getParameter("code"));
		String pwd = StringUtil.getString(request.getParameter("pwd"));
		if (StringUtils.isBlank(mobile) || !StringUtils.isNumeric(mobile)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		if (StringUtils.isBlank(pwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.updateTradeByMobile(code, mobile, pwd);

		Message.writeSuccess(response);

	}

	@RequestMapping(value = UrlCommand.update_user_nickname, method = RequestMethod.POST)
	@ResponseBody
	public void updateUserNickname(HttpServletRequest request, HttpServletResponse response) {
		String userId = StringUtil.getString(request.getParameter("userId"));
		String name = StringUtil.getString(request.getParameter("name"));

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(name)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		userService.updateNickname(name, Long.parseLong(userId));
		Message.writeSuccess(response);
	}

	@RequestMapping(value = UrlCommand.getproxy_list, method = RequestMethod.POST)
	@ResponseBody
	public void getProxyListByareaCode(HttpServletRequest request, HttpServletResponse response) {
		String areaCode = StringUtil.getString(request.getParameter("areaCode"));

		if (StringUtils.isBlank(areaCode) || !StringUtils.isNumeric(areaCode)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		List<ProxyInfo> proxyList = userService.getProxyList(Integer.parseInt(areaCode));
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, proxyList);
	}

	@RequestMapping(value = UrlCommand.bank_list, method = RequestMethod.POST)
	@ResponseBody
	public void getBankList(HttpServletRequest request, HttpServletResponse response) {
		// 1 2 7 中信银行 8 9 10 11 12 13 14 15 16
		List list = new ArrayList<>();
		String [] str={"工商银行","建设银行","农业银行","中国银行","招商银行","交通银行","中信银行","平安银行","华夏银行","民生银行","兴业银行","广发银行","九江银行","中国光大银行","邮政储蓄银行","上海浦东发展银行"};
		for(int i=0;i<str.length;i++){
			BankName b = new BankName();
			b.setBankName(str[i]);
			list.add(b);
			
		}
	
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, list);
	}
}
