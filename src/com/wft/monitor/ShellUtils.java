package com.wft.monitor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * @author admin
 *调用shell脚本 
 
 */
public class ShellUtils {

	private final static Logger log = Logger.getLogger(ShellUtils.class);
	
	
	public static String doCurl(String website){
		return doShell("curl",website,"-k","--connect-timeout","15","--retry","3","--retry-delay","5");
	}
	private static String doShell(String... shell){
		log.info("shell:"+Arrays.toString(shell) );
		ProcessBuilder pb = new ProcessBuilder(shell);
		
		int runningStatus = 0;
		String s = null;
		StringBuilder result = new StringBuilder();
		try {
			Process p = pb.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				log.info(s);
				result.append(s);
			}
			while ((s = stdError.readLine()) != null) {
				log.info(s);
			}
			try {
				runningStatus = p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error( e.getMessage());
			}finally{
		    	if(p!=null)p.destroy();
		    }

		}catch (IOException e) {
			e.printStackTrace();
			log.error( e.getMessage());
	    }
		log.info("runningStatus:"+runningStatus);
		return result.toString();
	}
	
	
}
