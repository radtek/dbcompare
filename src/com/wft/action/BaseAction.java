package com.wft.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

/**
 * @author admin
 * base
 */
@Controller
public class BaseAction {

	protected final static Logger log = Logger.getLogger(BaseAction.class);
	
	 protected ExecutorService threadExec =  Executors.newFixedThreadPool(50);
	 
	 protected final String line = System.getProperty("line.separator");
	  
	  protected String getRemortIP(HttpServletRequest request) { 
    	  if (request.getHeader("x-forwarded-for") == null) { 
    	   return request.getRemoteAddr(); 
    	  } 
    	  return request.getHeader("x-forwarded-for"); 
    }
	
	 @PreDestroy
	 public void destoryResource() {
			threadExec.shutdown(); // Disable new tasks from being submitted
			try {
				// Wait a while for existing tasks to terminate
				if (!threadExec.awaitTermination(60, TimeUnit.SECONDS)) {
					threadExec.shutdownNow(); // Cancel currently executing tasks
					// Wait a while for tasks to respond to being cancelled
					if (!threadExec.awaitTermination(60, TimeUnit.SECONDS)) {
						 
					}
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				threadExec.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}
		}
}
