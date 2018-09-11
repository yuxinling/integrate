package com.integrate.web;

import com.integrate.core.http.JettyServer;
import com.integrate.env.ServerPort;

public class WebMain extends JettyServer {

	public static void main(String[] args) {
		int port = ServerPort.WEB;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ignored) {}
		WebMain rechargeMain = new WebMain();
		rechargeMain.setPort(port);
		rechargeMain.addWebappPath("integrate-web/src/main/webapp");
		rechargeMain.setContextPath("/");
		rechargeMain.start();
	}

}
