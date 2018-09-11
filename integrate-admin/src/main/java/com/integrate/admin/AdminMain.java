package com.integrate.admin;

import com.integrate.core.http.JettyServer;
import com.integrate.env.ServerPort;

public class AdminMain extends JettyServer {

	public static void main(String[] args) {
		int port = ServerPort.ADMIN;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ignored) {}
		AdminMain logicMain = new AdminMain();
		logicMain.setPort(port);
		logicMain.addWebappPath("integrate-admin/src/main/webapp");
		logicMain.setContextPath("/tegadmin");
		logicMain.start();
	}
}
