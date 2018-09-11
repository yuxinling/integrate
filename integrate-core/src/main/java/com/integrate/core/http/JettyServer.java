package com.integrate.core.http;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.integrate.env.Env;

import org.apache.commons.lang3.Validate;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

public class JettyServer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private int port = -1;
    private String contextPath = null;
    private ServletHandler servletHandler = null;
    private List<String> webappPaths = Lists.newArrayList();

    public JettyServer(){
        webappPaths.add("lib/webapp");
        webappPaths.add("src/main/webapp");
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * 用在修正idea下的当前目录
     */
    public void addWebappPath(String webappPath) {
        webappPaths.add(webappPath);
    }

    public void setServletHandler(ServletHandler servletHandler) {
        this.servletHandler = servletHandler;
    }

    public void start() {
        Validate.isTrue(port>0);

        Server server = new Server();

        if(!Env.isProd()){
       	 server.setThreadPool(new ExecutorThreadPool(Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat(Server.class.getName() + ".threadPool").build())));

        }else{
          	 server.setThreadPool(new ExecutorThreadPool(Executors.newFixedThreadPool(200,new ThreadFactoryBuilder().setNameFormat(Server.class.getName() + ".threadPool").build())));

        }

        SelectChannelConnector connector = new SelectChannelConnector();

        String address = null;

        connector.setPort(port);
        server.addConnector(connector);

        if(contextPath != null) {
            server.setHandler(new WebAppContext(getWebappPath(), contextPath));
        } else if(servletHandler != null) {
            server.setHandler(servletHandler);
        } else {
            Validate.isTrue(false);
        }

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.error("", e);
            System.exit(-1);
        }

        logger.warn("jetty start at {}:{}", address, port);
    }

    private String getWebappPath() {
        for(String webappPath: webappPaths) {
            if(new File(webappPath, "WEB-INF/web.xml").exists()) {
                logger.warn("find {}", webappPath);
                return webappPath;
            }
        }
        throw new IllegalStateException("not find any webappPath");
    }
}
