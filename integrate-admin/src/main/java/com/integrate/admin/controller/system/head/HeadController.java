package com.integrate.admin.controller.system.head;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.service.system.user.AdminUserService;
import com.integrate.admin.util.AppUtil;
import com.integrate.admin.util.Const;
import com.integrate.admin.util.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/head")
public class HeadController extends BaseController {

	@Resource(name="adminUserService")
	private AdminUserService userService;

	/**
	 * 获取头部信息
	 */
	@RequestMapping(value="/getUname")
	@ResponseBody
	public Object getList() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute(Const.SESSION_userpds);
			
			if(null == pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
				pd.put("USERNAME", USERNAME);
				pds = userService.findByUId(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			
			pdList.add(pds);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 保存皮肤
	 */
	@RequestMapping(value="/setSKIN")
	public void setSKIN(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			
			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();//获取当前登录者loginname
			pd.put("USERNAME", USERNAME);
			userService.setSKIN(pd);
			session.removeAttribute(Const.SESSION_userpds);
			session.removeAttribute(Const.SESSION_USERROL);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	


}
