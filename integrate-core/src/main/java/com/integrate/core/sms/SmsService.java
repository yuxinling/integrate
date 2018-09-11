package com.integrate.core.sms;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 阿里大于短信发送服务
 *
 */
@Component
public class SmsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${sms.url}")
    private String url;
	
	@Value("${sms.appkey}")
    private String appkey;
	
	@Value("${sms.secret}")
    private String secret;

	protected TaobaoClient client;
	
	@PostConstruct
	public void init() {
		client = new DefaultTaobaoClient(url, appkey, secret);
	}

	/**
	 * 发送短信
	 * @param signName	短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
	 * @param templateCode	短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。
	 * @param recPhoneNum	短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
	 * @param smsJsonParams	短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
     * @return
     */
	public boolean sendNormalSms(String signName, String templateCode, String recPhoneNum, String smsJsonParams) {
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName(signName);
		req.setSmsTemplateCode(templateCode);
		req.setRecNum(recPhoneNum);
		req.setSmsParamString(smsJsonParams);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			if ("0".equals(rsp.getResult().getErrCode()) && rsp.getResult().getSuccess()) {
				return true;
			}

			logger.error("error while sendNormalSms signName:{}, templateCode:{}, recPhoneNum:{}, smsJsonParams:{}, rsp:{}",
					signName, templateCode, recPhoneNum, smsJsonParams, JSON.toJSONString(rsp));
		} catch (ApiException e) {
			logger.error("error while sendNormalSms recPhoneNum:{}", recPhoneNum, e);
		}

		return false;
	}
	

}
