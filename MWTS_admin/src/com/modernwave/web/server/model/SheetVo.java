package com.modernwave.web.server.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SheetVo {

	CommonsMultipartFile excelFile;

	public CommonsMultipartFile getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(CommonsMultipartFile excelFile) {
		this.excelFile = excelFile;
	}

	
}
