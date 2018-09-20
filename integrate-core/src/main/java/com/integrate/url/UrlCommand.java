package com.integrate.url;

/**
 * json url 请求
 * 
 *
 */
public interface UrlCommand {

	/*************************** 前端接口 ******************************/

	String update_data="/user/updatedata";
	
	String app_version = "/user/appversion";

	/**
	 * 用户中心
	 */
	String user_login = "/user/login";

	String register_code = "/user/registerCode";

	String findpwd_code = "/user/findpwd";

	String mobile_register = "/user/mobileRegister";

	String city_arealist = "/city/areaList";

	String base_userInfo = "/user/myBaseInfo";

	String user_integrate = "/user/userIntegrate";

	String update_pwd = "/user/updatepwd";

	String update_loginpwd = "/user/updateLoginPwd";

	String update_tradepwd = "/user/updateTradePwd";

	String user_bankinfo = "/user/userBankInfo";

	String user_bondbank = "/user/bondBankcard";

	String update_bankinfo = "/user/updateBankcard";
	
	//2.13 -2.14 
	String update_trade_pwd_code="/user/updateTradepwdCode";
	
	String update_trade_pwd="/user/updatetradepwdbymobile";
	
	String update_user_nickname="/user/updateUserNickName";
	
	String getproxy_list="/user/proxyList";
	String bank_list="/user/bankinfoList";

	/**
	 * 提现
	 */
	String user_withdrawals = "/user/withdrawals";
	String user_withdrawals_total = "/user/withdrawalsTotal";

	// 兑换
	String user_exchange = "/user/exchange";
	String exchange_goods = "/user/exchangeGoods";

	// 充值
	String user_recharge = "/user/recharge";
	
	String user_rechargeV2="/user/recharge2";
	
	String recharge_goods = "/user/rechargGoods";

	String user_order = "/user/userOrders";

	String user_order_total = "/user/userOrdersTotal";
	
	String user_recorde = "/user/userRecorde";

	String area_recharge = "/user/areaRecharge";

	String unionpay_tn = "/unionpay/getTn";

	String unionpay_notify = "/unionpay/notify";

	String unionpay_query = "/unionpay/query";
	
	String news_images = "/news/images";
	String article_titles = "/articles/titles";
	String article_detail = "/articles/{id}";
	String product = "/products";
	String product_search = "/products/search";
	String product_detail = "/products/{id}";
	
	

	/*************************** admin后台URL bg开头 ******************************/

	String rechrage_exchange = "/bg/exchangeList";
	String rechrage = "/bg/rechargeList";
	String rechrage_oplogs = "/bg/rechargeOplogs";

	String human_recharge_goto_edit="bg/humanRechargeToEdit";
	String human_recharge_edit="bg/humanRechargeEdit";
	
	
	String proportion="bg/proportionList";
	String proportion_goto_edit="bg/proportionToEdit";
	String proportion_edit="bg/proportionEdit";
	
	String app="bg/appList";
	String app_goto_edit="bg/appToEdit";
	String app_edit="bg/appEdit";
	String image_new="bg/imageNews";
	String image_upload="bg/images/upload";
	String article_new="bg/articleNews";
	String article_new_goto_edit="bg/articleNewToEdit";
	String article_new_goto_view="bg/articleNewToView";
	String article_new_edit="bg/articleNewEdit";
	String article_new_delete="bg/articleNewDelete";
	String product_list = "bg/productList";
	String product_goto_edit = "bg/productToEdit";
	
	
	String user_list ="bg/userList";
	String withdrawls="bg/withdrawlsList";
	String withdrawlsEdit="bg/withdrawlsEdit";
	String withdrawls_excel ="bg/withdrawlsExcel";
	
	String city_list="bg/cityList";
	String area_list="bg/areaList";
	String proxy_list="bg/proxyList";
	
	String user_edit="bg/edit";
	String user_to_edit="bg/toedit";
	
	String user_isfreeze_edit="bg/isfreezeEdit";
	String user_freeze_all="bg/freezeAll";
	String user_un_freeze_all="bg/unFreezeAll";
	String user_isfreeze_to_edit="bg/isfreezeToedit";
	
	String rechrage_isfreeze="bg/rechrageIsfreeze";
	String rechrage_edit="bg/rechrageEdit";
	String rechrage_to_edit="bg/rechrageToEdit";
	String exchange_to_edit="bg/exchangeToEdit";
	String exchange_edit="bg/exchangeEdit";
	
	String user_to_record="bg/usertorecord";
	String user_record="bg/userrecord";
	
	//统计
	String statistics_user="bg/statisticsUser";
	String statistics_user_excel="bg/statisticsUserExcel";
	String statistics_rechrage="bg/statisticsRechrage";
	String statistics_rechrage_excel="bg/statisticsRechrageExcel";

	String statistics_withdrawls="bg/statisticswithdrawls";
	String statistics_withdrawls_excel="bg/statisticswithdrawlsExcel";
	/*************************** admin后台URL bg开头 ******************************/

}