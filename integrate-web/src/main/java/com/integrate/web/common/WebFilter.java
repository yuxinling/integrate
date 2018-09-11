package com.integrate.web.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;


/**
 */
public class WebFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = HttpServletRequest.class.cast(request);
		HttpServletResponse res = HttpServletResponse.class.cast(response);
		String url = req.getRequestURI();
		res.setHeader("url", url);
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}
