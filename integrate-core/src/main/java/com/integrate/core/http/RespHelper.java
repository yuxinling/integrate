package com.integrate.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrate.common.util.JsonUtils;
import com.integrate.common.util.XstreamUtils;

public class RespHelper {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public static void writeText(HttpServletResponse res, int code) {
		res.setContentType("text/plain; charset=UTF-8");
		try {
			PrintWriter writer = res.getWriter();
			writer.write(code);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	public static void writeText(HttpServletResponse res, String message) {
		res.setContentType("text/plain; charset=UTF-8");
		try {
			PrintWriter writer = res.getWriter();
			writer.write(message);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	public static void writeXml(HttpServletResponse res, Object obj) {
		res.setContentType("text/xml; charset=UTF-8");
		try {
			PrintWriter writer = res.getWriter();
			writer.write(XstreamUtils.objectToXml(obj));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	public static void writeJson(HttpServletResponse res, Object obj) {
		res.setContentType("application/json; charset=utf-8");
		try {
			String message = JsonUtils.toJsonString(obj);

			PrintWriter writer = res.getWriter();
			writer.write(message);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	

	public static void writeJson(HttpServletResponse res, String message) {
		res.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter writer = res.getWriter();
			writer.write(message);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	public static void writeError(HttpServletResponse res) {
		res.setContentType("text/plain; charset=utf-8");
		try {
			PrintWriter writer = res.getWriter();
			writer.write("fail");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	public static void writeFile(HttpServletResponse res,InputStream in) {
		ServletOutputStream out = null;
		if(in ==null){
			writeError(res);
			return ;
		}
        try {
            int len = 0;  
            out =res.getOutputStream();
            byte buf[] = new byte[1024];//缓存作用  
            while( (len = in.read(buf)) > 0 ) 
            {  
                out.write(buf, 0, len);
            }  
        } catch (IOException e) {
			logger.error("", e);
		}finally{  
            if(in!=null)  
            {  
              try{                
                  in.close();  
              }catch(IOException e){  
                 e.printStackTrace();  
               }  
            }  
            if(out!=null)  
            {  
               try{  
                   out.close();  
               }catch(IOException e){  
                   e.printStackTrace();  
                }  
             }  
         }  
     }  
	
}
