package com.integrate.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.integrate.common.CacheKeys;
import com.integrate.common.util.BankUtils;
import com.integrate.common.util.DateUtil;
import com.integrate.common.util.JsonUtils;
import com.integrate.common.util.R;
import com.integrate.common.util.RandomUtil;
import com.integrate.common.util.TimeConstant;
import com.integrate.core.redis.RedisClient;
import com.integrate.core.sms.SmsService;
import com.integrate.enums.MsgEnumType;
import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;
import com.integrate.web.dao.AreaDao;
import com.integrate.web.dao.UserDao;
import com.integrate.web.model.BankCardInfo;
import com.integrate.web.model.Integrate;
import com.integrate.web.model.ProxyInfo;
import com.integrate.web.model.User;
import com.integrate.web.model.UserBaseInfo;

@Component
public class UserService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${sms.register.signName}")
	private String REGISTER_SIGN_NAME;

	@Value("${sms.register.templateCode}")
	private String REGISTER_TEMPLATE_CODE;

	@Value("${sms.reset.signName}")
	private String RESET_SIGN_NAME;

	@Value("${sms.reset.templateCode}")
	private String RESET_TEMPLATE_CODE;

	@Value("${sms.trade.signName}")
	private String TRADE_SIGN_NAME;

	@Value("${sms.trade.templateCode}")
	private String TRADE_TEMPLATE_CODE;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RedisClient redisClient;

	@Autowired
	private SmsService smsService;

	@Value("${subject_name}")
	private String SUBJECT_NAME;

	@Autowired
	private AreaDao areaDao;

	/**
	 * 生成手机注册验证码
	 * 
	 * @param mobile
	 */
	public void generateRegisterCode(String mobile) {
		// 校验手机号
		boolean mobileExists = userDao.isMobileExists(mobile);
		if (mobileExists) {
			throw new BusinessException(MsgEnumType.REPEAT_REGISTER_MOBILE);
		}

		// 发送短信
		String randomNum = RandomUtil.randomNum(4);
		redisClient.getJedis().setex(CacheKeys.mobile_code + mobile, 10 * TimeConstant.ONE_MINUTE_SECOND, randomNum);
		Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
		map.put("code", randomNum);
		map.put("product", SUBJECT_NAME);

		boolean success = smsService.sendNormalSms(REGISTER_SIGN_NAME, REGISTER_TEMPLATE_CODE, mobile,
				JsonUtils.toJsonString(map));
		if (!success) {
			throw new BusinessException(MsgEnumType.REGISTER_SMS_ERROR);
		}
	}

	/**
	 * 生成找回密码验证码
	 * 
	 * @param mobile
	 */
	public void getpwdCode(String mobile) {
		// 校验手机号
		boolean mobileExists = userDao.isMobileExists(mobile);
		if (!mobileExists) {
			throw new BusinessException(MsgEnumType.MOBILE_NO_REGISTER);
		}

		// 发送短信
		String randomNum = RandomUtil.randomNum(4);
		redisClient.getJedis().setex(CacheKeys.mobile_code + mobile, 10 * TimeConstant.ONE_MINUTE_SECOND, randomNum);
		Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
		map.put("code", randomNum);
		map.put("product", SUBJECT_NAME);

		boolean success = smsService.sendNormalSms(RESET_SIGN_NAME, RESET_TEMPLATE_CODE, mobile,
				JsonUtils.toJsonString(map));
		if (!success) {
			throw new BusinessException("短信发送失败");
		}
	}

	public User registerUserByMobile(String mobile, String nickname, String regCode, int cityCode, int areaCode,
			String loginPwd, String tradePwd, String recommendPerson) {
		if (!StringUtils.isNumeric(mobile)) {
			throw new BusinessException(SysMsgEnumType.PARAM_BLANK);
		}

		// 校验验证码
		String correctCode = redisClient.getJedis().get(CacheKeys.mobile_code + mobile);
		if (!regCode.equals(correctCode)) {
			logger.warn("resgister mobile:{} input error regCode:{}, correctCode:{}", mobile, regCode, correctCode);
			throw new BusinessException(MsgEnumType.INVALID_REGISTER_CODE);
		}

		// 校验手机号
		boolean mobileExists = userDao.isMobileExists(mobile);
		if (mobileExists) {
			throw new BusinessException(MsgEnumType.REPEAT_REGISTER_MOBILE);
		}

		String token = generateToken(mobile);
		Date expireDate = DateUtil.changeDateOfMonth(new Date(), 1);
		// to do
		User user = new User();
		user.setNickname(nickname);
		user.setAreaCode(areaCode);
		user.setCityCode(cityCode);
		user.setMobile(mobile);
		user.setToken(token);
		try {
			long userId = userDao.insertUser(mobile, nickname, regCode, cityCode, areaCode, loginPwd, tradePwd,
					recommendPerson);
			if (userId < 1) {
				throw new BusinessException(SysMsgEnumType.FAIL);
			}
			user.setUserId(userId);
		} catch (Exception e) {
		}

		return user;
	}

	public boolean UserLogin(String mobile, String pwd) {
		return userDao.userLogin(mobile, pwd);
	}

	public boolean updatePwd(String code, String mobile, String pwd) {
		// 校验验证码
		String correctCode = redisClient.getJedis().get(CacheKeys.mobile_code + mobile);
		if (!code.equals(correctCode)) {
			logger.warn("resgister mobile:{} input error regCode:{}, correctCode:{}", mobile, code, correctCode);
			throw new BusinessException(MsgEnumType.INVALID_REGISTER_CODE);
		}

		return userDao.updatePwd(mobile, pwd);
	}

	/**
	 * 更改登录了密码
	 * 
	 * @param userId
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public boolean updateLoginPwd(long userId, String oldpwd, String newpwd) {
		// 校验登录密码
		boolean userloginPWD = userDao.userloginPWD(userId, oldpwd);
		if (!userloginPWD) {
			throw new BusinessException(R.SYS_FAIL, "登录密码错误");
		}
		boolean updateloginPwd = userDao.updateloginPwd(userId, newpwd);

		return updateloginPwd;
	}

	/**
	 * 更改交易密码
	 * 
	 * @param userId
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public boolean updateTradePwd(long userId, String oldpwd, String newpwd) {
		// 校验登录密码
		boolean result = userDao.usertradePWD(userId, oldpwd);
		if (!result) {
			throw new BusinessException(R.SYS_FAIL, "交易密码错误");
		}

		return userDao.updatetradePwd(userId, newpwd);
	}

	public boolean valtradePwd(long userId, String oldpwd) {
		return userDao.usertradePWD(userId, oldpwd);
	}

	public UserBaseInfo getUserBaseInfo(String mobile) {
		UserBaseInfo userBaseInfo = userDao.getUserBaseInfo(mobile);

		logger.warn("userBaseInfo:[{}]", userBaseInfo);
		if (userBaseInfo == null) {
			throw new BusinessException(R.SYS_FAIL, "查询用户信息失败");
		}
		BankCardInfo bankInfo = getBankInfo(userBaseInfo.getUserId());
		if (bankInfo == null) {
			userBaseInfo.setIsBond(0);
		} else {
			userBaseInfo.setIsBond(1);
		}
		Map<String, Object> area = areaDao.getArea(userBaseInfo.getCityCode(), userBaseInfo.getAreaCode());
		logger.warn("area:[{}]", area);
		String token = generateToken(mobile);
		userBaseInfo.setToken(token);
		if (area != null) {
			userBaseInfo.setArea(area.get("city_name") + "-" + area.get("area_name"));
		}
		return userBaseInfo;
	}

	public UserBaseInfo getUserBaseInfoById(long userId) {
		UserBaseInfo userBaseInfo = userDao.getUserBaseInfoById(userId);
		if (userBaseInfo == null) {
			throw new BusinessException(R.SYS_FAIL, "查询用户信息失败");
		}
		Map<String, Object> area = areaDao.getArea(userBaseInfo.getCityCode(), userBaseInfo.getAreaCode());
		logger.warn("area:[{}]", area);
		String token = generateToken(userBaseInfo.getMobile());
		userBaseInfo.setToken(token);
		if (area != null) {
			userBaseInfo.setArea(area.get("city_name") + "-" + area.get("area_name"));
		}
		BankCardInfo bankInfo = getBankInfo(userId);
		if (bankInfo == null) {
			userBaseInfo.setIsBond(0);
		} else {
			userBaseInfo.setIsBond(1);
		}

		return userBaseInfo;
	}

	public Integrate getUserIntegrate(long id) {
		Map<String, Object> map = userDao.getUserIntegrate(id);
		if (map == null) {
			throw new BusinessException(R.SYS_FAIL, "获取积分失败");
		}
		Integrate g = new Integrate();
		g.setIntegrate(Integer.parseInt(map.get("integrate") + ""));
		return g;
	}

	/**
	 * 获取银行卡信息
	 */
	public BankCardInfo getBankInfo(long userId) {

		BankCardInfo bankCardInfo = userDao.getBankCardInfo(userId);

		return bankCardInfo;

	}

	/**
	 * 添加银行卡
	 * 
	 * @param userId
	 * @param number
	 * @return
	 */
	public boolean addBank(long userId, String number, String cardholder, String branch, String bankName) {
		if (StringUtils.isBlank(bankName)) {
			bankName = BankUtils.getNameOfBank(number).split("\\·")[0];
		}

		return userDao.BondBankcard(userId, bankName, number, cardholder, branch);
	}

	/**
	 * 修改银行卡
	 * 
	 * @param userId
	 * @param number
	 * @return
	 */
	public boolean updateBank(int id, String number, String cardholder, String branch, String bankName) {
		if (StringUtils.isBlank(bankName)) {
			bankName = BankUtils.getNameOfBank(number).split("\\·")[0];
		}

		return userDao.updateBankcard(id, bankName, number, cardholder, branch);
	}

	/**
	 * 随机生成token
	 * 
	 * @param mobile
	 * @return
	 */
	private String generateToken(String mobile) {
		String token = null;
		try {
			token = com.integrate.common.util.CryptUtil.md5(mobile + System.currentTimeMillis()).toLowerCase();
		} catch (Exception e) {
			logger.error("generateToken errror mobile:{}", mobile);
			throw new BusinessException(SysMsgEnumType.FAIL);
		}
		return token;
	}

	/************************* 更新交易密码 ***********************************/

	public boolean updateTradeByMobile(String code, String mobile, String tradepwd) {
		// 校验验证码
		String correctCode = redisClient.getJedis().get(CacheKeys.mobile_code + mobile);
		if (!code.equals(correctCode)) {
			logger.warn("resgister mobile:{} input error regCode:{}, correctCode:{}", mobile, code, correctCode);
			throw new BusinessException(MsgEnumType.INVALID_REGISTER_CODE);
		}
		return userDao.updatetradePwdBymobile(mobile, tradepwd);
	}

	public boolean updateNickname(String name, long userId) {
		return userDao.updateNickname(name, userId);
	}

	public List<ProxyInfo> getProxyList(int areaCode) {
		return userDao.getProxyList(areaCode);
	}

	public void gettradepwdCode(String mobile) {
		// 校验手机号
		boolean mobileExists = userDao.isMobileExists(mobile);
		if (!mobileExists) {
			throw new BusinessException(MsgEnumType.MOBILE_NO_REGISTER);
		}

		// 发送短信
		String randomNum = RandomUtil.randomNum(4);
		redisClient.getJedis().setex(CacheKeys.mobile_code + mobile, 10 * TimeConstant.ONE_MINUTE_SECOND, randomNum);
		Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
		map.put("code", randomNum);
		map.put("product", SUBJECT_NAME);

		boolean success = smsService.sendNormalSms(TRADE_SIGN_NAME, TRADE_TEMPLATE_CODE, mobile,
				JsonUtils.toJsonString(map));
		if (!success) {
			throw new BusinessException("短信发送失败");
		}
	}

}
