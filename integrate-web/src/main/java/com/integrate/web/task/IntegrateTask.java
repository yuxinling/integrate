package com.integrate.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.integrate.web.service.AreaService;
import com.integrate.web.service.IntegtateService;

@Component
public class IntegrateTask {
	private Logger logger = LoggerFactory.getLogger(AreaService.class);
	@Autowired
	private IntegtateService integtateService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下） cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23)
	 * *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT) 积分退还 凌晨 开始退还
	 * 每天凌晨00:15分开始返回积分  @Scheduled(cron = "0 14 16 * * *")
	 */

	//@Scheduled(cron = "0 0/3 * * * *")
	@Scheduled(cron = "0 15 0 * * *")
	public void InvalidReservation() {
		logger.warn("启动了退还积分线程  时间用系统的 传0");
		integtateService.TaskIntegrate(0);
	}

	/**
	 * 每小时检查一次积分退还失败记录 开启线程 退还之前的所有失败的 1h 轮循 每天的 6-23 点
	 *	@Scheduled(cron = "0 * 16 * * ?")
	 */
	@Scheduled(cron = "0 15 */1 * * *")
	public void InvalidTask() {
		long systime = System.currentTimeMillis();
		Date date = new Date(systime);
		// 每天的 6-23 点
		if (systime < DateUtil.getDate6AM(date).getTime() || systime > DateUtil.getDate23AM(date).getTime()) {
			logger.warn("InvalidTask not ready");
			return;
		}

		logger.warn("InvalidTask start date:{}", date);
		integtateService.TaskIntegrate(systime);
		logger.warn("InvalidTask end date:{}", date);
	}
	
}
