package com.wft.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/down")
public class DownAction extends BaseAction{
  
	 
		 @RequestMapping(value = "dowCommon")
	    public ResponseEntity<byte[]> dowCommon( HttpServletRequest request, HttpServletResponse response,String fileName) throws IOException {
	    	 
	    	 
	    	return this.downloadFiles(request.getSession().getServletContext().getRealPath("/"), fileName);
	    }
		    
	    @RequestMapping(value = "downFile")
	    public ResponseEntity<byte[]> downFile( HttpServletRequest request) throws IOException {
	    	 
	    	String path = "";
	    	String fileName =  "";
	    	 
	    	 
	    	return this.downloadFiles(path, fileName);
	    }
	    
	    
	    /**
	     * @return 
	     * @throws IOException
	     */
	    private ResponseEntity<byte[]> downloadFiles(String path,String fileName) throws IOException {
	        
	        File destFile = new File(path,fileName);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", fileName);
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile), headers, HttpStatus.OK);
	    }

}
