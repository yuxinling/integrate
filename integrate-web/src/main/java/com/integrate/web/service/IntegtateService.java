package com.integrate.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integrate.common.util.GlobalIDUtil;
import com.integrate.exception.BusinessException;
import com.integrate.web.dao.IntegrateDao;
import com.integrate.web.dao.UserDao;
import com.integrate.web.model.Balance;
import com.integrate.web.model.Recharge;
import com.integrate.web.model.TaskInfo;
import com.integrate.web.task.DateUtil;

@Service
public class IntegtateService {
	private Logger logger = LoggerFactory.getLogger(AreaService.class);
	@Autowired
	private IntegrateDao integrateDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WalletService WalletService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	// 2.9修改积分退还规则

	@Transactional
	public boolean TaskIntegrate(long time) {
		Map<Long,UserCfg> userCfgs = new HashMap<Long, UserCfg>();

		if (time < 1) {
			time = System.currentTimeMillis();
		}
		TaskInfo taskInfo = getTaskInfo(Long.parseLong(sdf.format(time)));
		// 如果 准确返还积分 返回 true
		logger.warn("判断当天是否已成功返回积分***TaskInfo*** taskInfo:[{}]", taskInfo);
		if (taskInfo != null && taskInfo.getState() == 0) {
			return true;
		}
		Date date = new Date(time);

		logger.warn("进入退还积分线程启动***TaskIntegrate*** date:{}", date);

		// 查询 退还天数
		Map<String, Object> proportion = integrateDao.getProportion();
		if (proportion == null) {
			return false;
		}
		// 查询充值
		List<Recharge> rechargeList = integrateDao.getRechargeList();

		if (rechargeList != null && rechargeList.size() > 0) {
			// 循环 退还用户 积分
			for (Recharge r : rechargeList) {

				int fixed = Integer.parseInt(proportion.get("fixed") + "");// 固定返回天数
				int give = Integer.parseInt(proportion.get("give") + "");// 赠送返回天数
				// 隔天 的订单才回返回积分
				if (r.getTime() > DateUtil.getDate0AM(date).getTime()) {
					continue;
				}
				UserCfg cfg = userCfgs.get(r.getUserId());
				if(cfg == null) cfg = this.getUserCfg(r.getUserId());
				if(cfg == null){
					logger.info("订单id:[{}] 用户信息[userId={}]不存在!", r.getId(), r.getUserId());
					continue;
				}else userCfgs.put(r.getUserId(), cfg);
				if(cfg.isFreeze()){
					logger.info("订单id:[{}] 用户[userId={}]已被冻结!", r.getId(), r.getUserId());
					continue;
				}
				
				if(cfg.getGiveDay() > 0) give = cfg.getGiveDay();
				if(cfg.getFixDay() > 0) fixed = cfg.getFixDay();
				
				int scan = r.getIsScan();
				// 计算
				int fixed_refund = 1;// 每天退还的固定积分
				if (r.getFixedIntegrate() > fixed) {
					fixed_refund = r.getFixedIntegrate() / fixed;
				}

				int give_refund = 1;// 每天退还的赠送积分
				if (r.getGiveIntegrate() > give) {
					give_refund = r.getGiveIntegrate() / give;
				}
				// 大于剩余积分 也就是 最后一天 返回剩余的
				if (fixed_refund >= r.getResidualFixedIntegral()) {
					fixed_refund = r.getResidualFixedIntegral();
				}
				if (give_refund >= r.getResidualGiveIntegral()) {
					give_refund = r.getResidualGiveIntegral();
				}
				logger.warn("订单id:[{}],固定返还天数:[{}],赠送返还天数:[{}],固定返还积分:[{}],赠送返还积分:[{}],上次固定剩余:[{}],上次赠送剩余:[{}]", 
						r.getId(),fixed, give, fixed_refund, give_refund, r.getResidualFixedIntegral(), r.getResidualGiveIntegral());

				// 该笔订单不需要再扫描
				if ((r.getResidualGiveIntegral() - fixed_refund) <= 0
						&& (r.getResidualFixedIntegral() - give_refund) <= 0) {
					scan = 1;
				}

				int total = fixed_refund + give_refund; // 每笔订单当天返回数

				// 更新总剩余积分
				boolean balance = integrateDao.updateBalance(fixed_refund, give_refund, r.getUserId());
				if (!balance) {
					logger.warn("更新总剩余积分 updateBalance：{} ", r.getUserId());
					throw new BusinessException("user 更新总剩余积分失败");
				}
				// 更新订单需要退还的剩余积分
				boolean rechange = integrateDao.updateRechange(r.getId(), fixed_refund, give_refund, scan);
				if (!rechange) {
					logger.warn("更新订单剩余积分 updateRechange：{} ", r.getId());
					throw new BusinessException("更新订单剩余积分失败");
				}
				// 用户加积分
				boolean add = userDao.addIntegrate(r.getUserId(), total);
				if (!add) {
					logger.warn("用户加积分 addIntegrate：{},userID:[{}],total:{} ", r.getId(), r.getUserId(), total);
					throw new BusinessException("增加积分失败");
				}
				// 插入流水表
				// long currentTime = System.currentTimeMillis(); // 先生成内部订单
				// long orderId = GlobalIDUtil.createID(currentTime);
				boolean addRecord = WalletService.addRecord(r.getUserId(), r.getTradeNo() + "", r.getId(), 0, total, 3,
						1, time);

				if (!addRecord) {
					logger.warn("插入流水表失败 userid：{} ", r.getUserId());
					throw new BusinessException("插入流水表失败");
				}

			}

		}

		boolean result = integrateDao.addTask(0, "正常退还积分 ", Long.parseLong(sdf.format(time)));
		if (!result) {
			logger.warn("插入t_task 表失败 date:{}, 时间：{} ", date, sdf.format(time));
			throw new BusinessException("插入t_task 表失败");
		}
		logger.warn("正常退还积分了 date:{}  结束 :[{}]", date, result);

		return true;

	}

	/**
	 * 查询失败的退还记录 用于排错后 线程启动自动退还
	 * 
	 * @return
	 */

	public TaskInfo getTaskInfo(long time) {
		TaskInfo taskInfo = integrateDao.getTaskInfo(time);
		return taskInfo;
	}

	/**
	 * 每天自动退还积分开始 一个出错全部回滚 每天记录当天退还情况 发现当天退还失败 state=1 存入task表
	 * 
	 * @return
	 */

	/**
	 * 修复之前退还失败记录 更新task表中state=0 为当天退还成功
	 * 
	 * @param time
	 *            用于出错恢复 传0 默认系统时间
	 * @return
	 */

	@Transactional
	public boolean TaskIntegrate11(long time) {
		Map<Long,UserCfg> userCfgs = new HashMap<Long, UserCfg>();
		if (time < 1) {
			time = System.currentTimeMillis();
		}
		TaskInfo taskInfo = getTaskInfo(Long.parseLong(sdf.format(time)));
		// 如果 准确返还积分 返回 true
		if (taskInfo != null && taskInfo.getState() == 0) {
			return true;
		}

		Date date = new Date(time);

		logger.warn("进入退还积分线程启动TaskIntegrate date:{}", date);
		List<Balance> balance = integrateDao.getBalance();
		if (balance == null) {
			return false;
		}
		logger.warn("查询balance size:{}", balance.size());
		Map<String, Object> proportion = integrateDao.getProportion();
		if (proportion == null) {
			return false;
		} 
		for (Balance b : balance) {
			// fixed,give 计算每天需要退换的积分
			int f = Integer.parseInt(proportion.get("fixed") + "");// 固定返回天数
			int g = Integer.parseInt(proportion.get("give") + "");// 赠送返回天数
			// 开启退还积分

			long createTime = b.getCreateTime();// 获取第一次充值日期 // 只有 d+1 隔天
												// 的数据才退还积分
			if (createTime > DateUtil.getDate0AM(date).getTime()) {
				logger.warn("当天充值 不退还积分 createTime：{},用户 userid:[{}] ", createTime, b.getUserId());
				continue;
			}

			if (b.getGive() == 0 && b.getFixed() == 0) {
				continue;
			} 
			
			UserCfg cfg = userCfgs.get(b.getUserId());
			if(cfg == null) cfg = this.getUserCfg(b.getUserId());
			if(cfg == null){
				logger.info("balance-id:[{}] 用户信息[userId={}]不存在!", b.getId(), b.getUserId());
				continue;
			}else userCfgs.put(b.getUserId(), cfg);
			if(cfg.getGiveDay() > 0) g = cfg.getGiveDay();
			if(cfg.getFixDay() > 0) f = cfg.getFixDay();
			// 计算
			int fixed = (int) (b.getFixed() / f);// 固定返回的积分
			if (b.getFixed() > 0 && b.getFixed() < f) {
				fixed = 1;
			}
			int give = (int) (b.getGive() / g);// 赠送返回的积分

			if (b.getGive() > 0 && b.getGive() < g) {
				give = 1;
			}
			int fixed1 = b.getFixed() - fixed;// 剩余的积分
			int give1 = b.getGive() - give;// 剩余的积分

			// logger.warn("剩余的积分: " + fixed1 + " 剩余的积分:" + give1); // 获取退还总总积分
			int total = fixed + give;
			boolean updateBalance = integrateDao.updateBalance(fixed1, give1, b.getUserId());
			if (!updateBalance) {
				logger.warn("修复退还积分失败 b.getUserId()：{} ", b.getUserId());
				throw new BusinessException("balance 修复退还积分失败");
			} // 用户加积分
			boolean add = userDao.addIntegrate(b.getUserId(), total);
			//
			if (!add) {
				logger.warn("user 用户加积分失败 b.getUserId()：{} ", b.getUserId());
				throw new BusinessException("user 用户加积分失败");
			} // 流水表 // 插入流水表
			long currentTime = System.currentTimeMillis(); // 先生成内部订单
			long orderId = GlobalIDUtil.createID(currentTime);
			boolean addRecord = WalletService.addRecord(b.getUserId(), orderId + "", 0, 0, total, 3, 1, time);

			if (!addRecord) {
				logger.warn("插入流水表失败 userid：{} ", b.getUserId());
				throw new BusinessException("插入流水表失败");
			}
		}

		boolean result = integrateDao.addTask(0, "正常退还积分 ", Long.parseLong(sdf.format(time)));

		if (!result) {
			logger.warn("插入t_task 表失败 date:{}, 时间：{} ", date, sdf.format(time));
			throw new BusinessException("插入t_task 表失败");
		}
		logger.warn("正常退还积分了 date:{}  结束 :[{}]", date, result);
		return result;
	}

	private UserCfg getUserCfg(long userId){
		Map<String, Object> map = userDao.getFreezeAndDays(userId);
		if(map != null){
			
			boolean isFreeze = Integer.parseInt(map.get("is_freeze_all") + "") != 0 ? true : false;
			int fixDay = Integer.parseInt(map.get("fix_day") + "");
			int giveDay = Integer.parseInt(map.get("give_day") + "");
			return new UserCfg(isFreeze, fixDay, giveDay);
		}
		
		return null;
	}
	
	
	class UserCfg {
		private boolean isFreeze;
		private int giveDay;
		private int fixDay;
		
		public UserCfg() {
			super();
		}
		public UserCfg(boolean isFreeze, int fixDay, int giveDay) {
			super();
			this.isFreeze = isFreeze;
			this.fixDay = fixDay;
			this.giveDay = giveDay;
		}
		public boolean isFreeze() {
			return isFreeze;
		}
		public void setFreeze(boolean isFreeze) {
			this.isFreeze = isFreeze;
		}
		public int getGiveDay() {
			return giveDay;
		}
		public void setGiveDay(int giveDay) {
			this.giveDay = giveDay;
		}
		public int getFixDay() {
			return fixDay;
		}
		public void setFixDay(int fixDay) {
			this.fixDay = fixDay;
		}
	}

}
