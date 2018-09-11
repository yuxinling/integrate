package com.integrate.web.common;


import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.integrate.enums.SysMsgEnumType;
import com.integrate.exception.BusinessException;


/**
 * 
 */
@ControllerAdvice
public class GlobelExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ BusinessException.class })
	protected void handleBusinessException(BusinessException e, HttpServletResponse response) {

		loggerError(e);
		this.write(e.getErrorCode(), e.getErrorMessage(), response);
	}

	private void write(int code, String errorMsg, HttpServletResponse response) {

		try {
				Message.writeError(response, SysMsgEnumType.FAIL);
			
		} catch (Exception e) {
			logger.warn("response error", e);
		}
	}
	
	private void loggerError(Exception e){
		
        String msg = e.getClass().getSimpleName() + ":" + e.getMessage();
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            String className = stackTraceElement.getClassName();
            if(className.startsWith("com.xingtu")) {
                msg += ("\n\t at " + stackTraceElement.toString());
            }
        }
        logger.error(msg);
	}

}
