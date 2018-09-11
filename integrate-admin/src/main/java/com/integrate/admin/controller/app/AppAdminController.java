package com.integrate.admin.controller.app;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.module.app.model.AppInfo;
import com.integrate.admin.module.app.service.AppService;

import com.integrate.admin.util.Const;
import com.integrate.core.qiniu.QiniuService;
import com.integrate.enums.QiniuBucket;
import com.integrate.url.UrlCommand;

@Controller
public class AppAdminController extends BaseController {

	@Autowired
	private QiniuService qiniuService;
	@Autowired
	private AppService appService;

	@RequestMapping(value = UrlCommand.app)
	public ModelAndView getProportion() {

		ModelAndView mv = new ModelAndView();

		AppInfo appinfo = appService.getAppinfo();
		mv.setViewName("app/app");
		mv.addObject("p", appinfo);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 
	 */
	@RequestMapping(value = UrlCommand.app_goto_edit)
	public ModelAndView goAdd(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		String version = request.getParameter("version");

		mv.addObject("version", version);
		mv.setViewName("app/edit");

		return mv;
	}

	@RequestMapping(value = UrlCommand.app_edit)
	public ModelAndView update(HttpServletRequest request,
			@RequestParam(value = "tp", required = false) MultipartFile file) {

		String version = request.getParameter("version");
		String url = null;
		if (file != null) {
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			DiskFileItem fi = (DiskFileItem) cf.getFileItem();
			File f = fi.getStoreLocation();
			String resourceKey = qiniuService.uploadFile(f, QiniuBucket.HEAD_IMG.getBucket());
			String title = file.getOriginalFilename();
			if(resourceKey!=null){
				url = QiniuBucket.HEAD_IMG.getDomain() + resourceKey + "?v=1" + "&attname=" + title;
			}
			

		}

		appService.update(url, version);
		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
