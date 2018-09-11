package com.integrate.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.integrate.admin.controller.base.BaseController;
import com.integrate.admin.module.proportion.model.Proportion;
import com.integrate.admin.module.proportion.service.ProportionService;
import com.integrate.admin.util.Const;
import com.integrate.url.UrlCommand;

@Controller
public class ProportionAdminController extends BaseController {

	@Autowired
	private ProportionService proportionService;

	@RequestMapping(value = UrlCommand.proportion)
	public ModelAndView getProportion() {

		ModelAndView mv = new ModelAndView();

		Proportion proportion = proportionService.getProportion();
		mv.setViewName("proportion/proportion");
		mv.addObject("p", proportion);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = UrlCommand.proportion_goto_edit)
	public ModelAndView goAdd(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		String give = request.getParameter("give");
		String fixed = request.getParameter("fixed");
		mv.addObject("fixed", fixed);
		mv.addObject("give", give);
		mv.setViewName("proportion/edit");

		return mv;
	}

	@RequestMapping(value = UrlCommand.proportion_edit)
	public ModelAndView update(HttpServletRequest request) {

		String give = request.getParameter("give");
		String fixed = request.getParameter("fixed");
		proportionService.update(Integer.parseInt(fixed), Integer.parseInt(give));

		ModelAndView mv = this.getModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
