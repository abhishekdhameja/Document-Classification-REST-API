package com.amd.documentclassification.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResultDownloadController {

	@RequestMapping(value = "/result/{file_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFile(
	    @PathVariable("file_name") String fileName, HttpServletResponse response) {
	    try {
	      // get your file as InputStream
	    File file=new File(System.getProperty("user.dir")+File.separator+fileName);
	      InputStream is = new FileInputStream(file);
	      // copy it to response's OutputStream
	      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
	      response.flushBuffer();
	    } catch (IOException ex) {
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}

}
