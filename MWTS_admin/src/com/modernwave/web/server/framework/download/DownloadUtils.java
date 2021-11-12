package com.modernwave.web.server.framework.download;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadUtils extends AbstractView{
	
	public DownloadUtils() {
		// 받드시 octet-stream으로 설정해야함
		super.setContentType("application/octet-stream");
	}
	

	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		File file = (File) model.get("downloadUtils");
		System.out.println("file.getName()"+file.getName());
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		response.setHeader("Content-Transfer-Encoding", "binary");
		String fileName = java.net.URLEncoder.encode(file.getName(), "utf-8");
		fileName = fileName.replaceAll("\\+", " ");
		fileName = fileName.replaceAll("%28", "(");
		fileName = fileName.replaceAll("%29", ")");
		System.out.println("fileName["+fileName+"]");
		response.setHeader("Content-Disposition", "attachment;fileName=\""	+fileName + "\";");
		//System.out.println(response.getHeader("Content-Disposition"));
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} catch (java.io.IOException ioe) {
			System.out.println("파일이 존재하지 않습니다.");
			ioe.printStackTrace();
		} finally {
			if (fis != null)
				fis.close();
		}
		out.flush();
	}
}
