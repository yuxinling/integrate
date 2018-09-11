package com.integrate.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.integrate.common.util.StringUtil;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.url.UrlCommand;
import com.integrate.web.common.Message;
import com.integrate.web.model.Goods;
import com.integrate.web.model.RechargeInfoResp;
import com.integrate.web.model.RechargeResp;
import com.integrate.web.model.RecordResp;
import com.integrate.web.service.UserService;
import com.integrate.web.service.WalletService;

@Controller
public class WalletController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WalletService walletService;

	@Autowired
	private UserService userService;

	Cache<String, String> reqCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS).build();

	/**
	 * 提现
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.user_withdrawals, method = RequestMethod.POST)
	@ResponseBody
	public void withdrawals(HttpServletRequest request, HttpServletResponse response) {
		String money = StringUtil.getString(request.getParameter("money"));
		String userId = StringUtil.getString(request.getParameter("userId"));
		String tradePwd = StringUtil.getString(request.getParameter("tradePwd"));
		String platform = StringUtil.getString(request.getParameter("platform"));

		String ifPresent = reqCache.getIfPresent(userId);
		if (StringUtils.isNotBlank(ifPresent)) {
			logger.warn("withdrawals 产生并发了 userId:{}, money:{}, platform:{}", userId, money, platform);
		} else {
			reqCache.put(userId, "1");
		}
		logger.warn("withdrawals userId:{}, money:{}, platform:{}", userId, money, platform);

		if (!StringUtils.isNumeric(money) || StringUtils.isBlank(userId) || StringUtils.isBlank(tradePwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		boolean valtradePwd = userService.valtradePwd(Long.parseLong(userId), tradePwd);
		if (!valtradePwd) {
			Message.writeError(response, SysMsgEnumType.TRADEPWD_FAIL);
			return;
		}
		if(walletService.isFreezeAll(Long.parseLong(userId))){
			Message.writeError(response, SysMsgEnumType.FREEZE_FAIL);
			return;
		}
		int imoney = Integer.parseInt(money);
		//validate 用户提现改为只能做1000元的整数倍提现，最少1000元起
		if(imoney < 100 || imoney % 100 != 0){
			Message.writeError(response, SysMsgEnumType.WITHDRAW_FAIL);
			return;
		}

		if (imoney > walletService.userintegrate(Long.parseLong(userId))) {
			Message.writeError(response, SysMsgEnumType.INTEGRATE_FAIL);
			return;
		}
		boolean add = walletService.add(Long.parseLong(userId), imoney, tradePwd);
		if (!add) {
			Message.writeError(response, SysMsgEnumType.TRADE_FAIL);
			return;
		}
		Message.writeSuccess(response);

	}

	/**
	 * 兑换
	 */

	@RequestMapping(value = UrlCommand.user_exchange, method = RequestMethod.POST)
	@ResponseBody
	public void exchange(HttpServletRequest request, HttpServletResponse response) {
		String integrate = StringUtil.getString(request.getParameter("integrate"));
		String goodsId = StringUtil.getString(request.getParameter("goodsId"));
		String tradePwd = StringUtil.getString(request.getParameter("tradePwd"));
		String userId = StringUtil.getString(request.getParameter("userId"));
		
		if (!StringUtils.isNumeric(integrate) || StringUtils.isBlank(goodsId) || StringUtils.isBlank(tradePwd)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		boolean valtradePwd = userService.valtradePwd(Long.parseLong(userId), tradePwd);
		if (!valtradePwd) {
			Message.writeError(response, SysMsgEnumType.TRADEPWD_FAIL);
			return;
		}
		if(walletService.isFreezeAll(Long.parseLong(userId))){
			Message.writeError(response, SysMsgEnumType.FREEZE_FAIL);
			return;
		}
		if (Integer.parseInt(integrate) > walletService.userintegrate(Long.parseLong(userId))) {
			Message.writeError(response, SysMsgEnumType.INTEGRATE_FAIL);
			return;
		}
		boolean result = walletService.addRechrageRecorde(0, 0, Long.parseLong(userId), Integer.parseInt(integrate), 1);
		if (!result) {
			Message.writeError(response, SysMsgEnumType.TRADE_FAIL);
			return;
		}
		Message.writeSuccess(response);

	}

	@RequestMapping(value = UrlCommand.exchange_goods, method = RequestMethod.POST)
	@ResponseBody
	public void exchangeGoods(HttpServletRequest request, HttpServletResponse response) {
		List<Goods> list = new ArrayList<>();
		//螺旋藻大米、陈氏金谷大米、陈氏银谷大米、陈氏香谷大米、赣南山茶油、赣北菜籽油、狗牯脑绿茶、庐山云雾、婺源绿茶、河口红茶、义门陈白酒
		list.add(new Goods(1,"螺旋藻大米"));
		list.add(new Goods(2,"陈氏金谷大米"));
		list.add(new Goods(3,"陈氏银谷大米"));
		list.add(new Goods(4,"陈氏香谷大米"));
		list.add(new Goods(5,"赣南山茶油"));
		list.add(new Goods(6,"赣北菜籽油"));
		list.add(new Goods(7,"狗牯脑绿茶"));
		list.add(new Goods(8,"庐山云雾"));
		list.add(new Goods(9,"婺源绿茶"));
		list.add(new Goods(10,"河口红茶"));
		list.add(new Goods(11,"义门陈白酒"));
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, list);
	}

	

	/**
	 * 充值
	 */

	@RequestMapping(value = UrlCommand.user_recharge, method = RequestMethod.POST)
	@ResponseBody
	public void recharge(HttpServletRequest request, HttpServletResponse response) {
		String money = StringUtil.getString(request.getParameter("money"));
		String userId = StringUtil.getString(request.getParameter("userId"));
		String cityCode = StringUtil.getString(request.getParameter("cityCode"));
		String areaCode = StringUtil.getString(request.getParameter("areaCode"));
		String goodsId = StringUtil.getString(request.getParameter("goodsId"));
		if (!StringUtils.isNumeric(money) || !StringUtils.isNumeric(userId) || !StringUtils.isNumeric(cityCode)
				|| !StringUtils.isNumeric(areaCode)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		if(walletService.isFreezeAll(Long.parseLong(userId))){
			Message.writeError(response, SysMsgEnumType.FREEZE_FAIL);
			return;
		}
		walletService.addRechrageRecorde(Integer.parseInt(cityCode), Integer.parseInt(areaCode), Long.parseLong(userId),
				Integer.parseInt(money), 0);
		Message.writeSuccess(response);

	}
	
	@RequestMapping(value = UrlCommand.recharge_goods, method = RequestMethod.POST)
	@ResponseBody
	public void rechargeGoods(HttpServletRequest request, HttpServletResponse response) {
		List<Goods> list = new ArrayList<>();
		Goods goods = new Goods();
		goods.setGoodsId(1);
		goods.setGoods("大米");
		list.add(goods);
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, list);

	}

	/**
	 * 用户订单 充值和兑换
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.user_order, method = RequestMethod.POST)
	@ResponseBody
	public void userOrder(HttpServletRequest request, HttpServletResponse response) {
		String type = StringUtil.getString(request.getParameter("type"));
		String userId = StringUtil.getString(request.getParameter("userId"));
		String lastId = StringUtil.getString(request.getParameter("lastId"));
		String pageSize = StringUtil.getString(request.getParameter("pageSize"));
		if (!StringUtils.isNumeric(type) || !StringUtils.isNumeric(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		 List<RechargeResp> userOrderList = walletService.getUserOrderList(Long.parseLong(userId), Integer.parseInt(type), Long.parseLong(lastId),
				Integer.parseInt(pageSize));

		Message.writeMsg(response, SysMsgEnumType.SUCCESS, userOrderList);
	}

	/**
	 * 积分记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.user_recorde, method = RequestMethod.POST)
	@ResponseBody
	public void userrecord(HttpServletRequest request, HttpServletResponse response) {

		String userId = StringUtil.getString(request.getParameter("userId"));
		String lastId = StringUtil.getString(request.getParameter("lastId"));
		String pageSize = StringUtil.getString(request.getParameter("pageSize"));
		if (!StringUtils.isNumeric(pageSize) || !StringUtils.isNumeric(userId)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}

		List<RecordResp> userRecordList = walletService.getUserRecordList(Long.parseLong(userId),
				Long.parseLong(lastId), Integer.parseInt(pageSize));
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, userRecordList);
	}

	/**
	 * 区域订单 充值和兑换
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = UrlCommand.area_recharge, method = RequestMethod.POST)
	@ResponseBody
	public void AreaOrder(HttpServletRequest request, HttpServletResponse response) {
		String userId = StringUtil.getString(request.getParameter("userId"));
		String lastId = StringUtil.getString(request.getParameter("lastId"));
		String pageSize = StringUtil.getString(request.getParameter("pageSize"));
		if (!StringUtils.isNumeric(pageSize)) {
			Message.writeError(response, SysMsgEnumType.PARAM_LACK);
			return;
		}
		Map<String, Object> getareaOrderList = walletService.getareaOrderList(Long.parseLong(userId),
				Long.parseLong(lastId), Integer.parseInt(pageSize));

		Message.writeMsg(response, SysMsgEnumType.SUCCESS, getareaOrderList);
	}
	
	
	@RequestMapping(value = UrlCommand.user_order_total, method = RequestMethod.POST)
	@ResponseBody
	public void Ordertotal(HttpServletRequest request, HttpServletResponse response) {
		String userId = StringUtil.getString(request.getParameter("userId"));
		
		RechargeInfoResp rechragetotal = walletService.getRechragetotal(Long.parseLong(userId));
		logger.warn("total:{}",rechragetotal.getTotal());

		Message.writeMsg(response, SysMsgEnumType.SUCCESS, rechragetotal);
	}
	
	/**
	 * 提现总金额
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = UrlCommand.user_withdrawals_total, method = RequestMethod.POST)
	public void withdrawalsTotal(HttpServletRequest request, HttpServletResponse response) {
		String userId = StringUtil.getString(request.getParameter("userId"));
		RechargeInfoResp total = walletService.getWithdrawalsTotal(Long.parseLong(userId));
		logger.warn("total:{}",total.getTotal());
		Message.writeMsg(response, SysMsgEnumType.SUCCESS, total);
	}
}
