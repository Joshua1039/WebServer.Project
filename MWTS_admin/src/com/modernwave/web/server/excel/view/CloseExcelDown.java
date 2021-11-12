package com.modernwave.web.server.excel.view;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.modernwave.web.server.common.Comm;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;

public class CloseExcelDown extends AbstractExcelView{

	private Logger log = Logger.getLogger(this.getClass());
	
	public void setBorder(HSSFCellStyle style, short forgroundColor, short borderColor){
		if(style == null)
			return;
		style.setFillForegroundColor(forgroundColor);
		//style.setW
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		style.setTopBorderColor(borderColor);
		style.setBottomBorderColor(borderColor);
		style.setLeftBorderColor(borderColor);
		style.setRightBorderColor(borderColor);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	}
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
		String sheetName = ""+model.get("sheetName");
		HSSFSheet sheet = wb.createSheet(sheetName);
		
		
		HSSFCellStyle headerStyle = wb.createCellStyle();
		setBorder(headerStyle, HSSFColor.RED.index, HSSFColor.BLUE.index);
		
		HSSFCellStyle cellbackStyle = wb.createCellStyle();
		setBorder(cellbackStyle, HSSFColor.GREEN.index, HSSFColor.BLUE.index);
		
		HSSFCellStyle cellbackWhiteStyle = wb.createCellStyle();
		setBorder(cellbackWhiteStyle, HSSFColor.WHITE.index, HSSFColor.BLUE.index);
		
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.RED.index, (byte) Integer.parseInt("d1",16), (byte) Integer.parseInt("e5",16), (byte) Integer.parseInt("fe",16));
		palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) Integer.parseInt("e3",16), (byte) Integer.parseInt("ef",16), (byte) Integer.parseInt("ef",16));
		palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) Integer.parseInt("a4",16), (byte) Integer.parseInt("be",16), (byte) Integer.parseInt("d4",16));
		
		InputParam param = (InputParam)model.get("estiCloseListOutput");			// 
		int totalEstiNumCnt = param.getInt("TOTAL_ESTI_NUM_CNT");
		
	
		//엑셀의 헤더 첫째행 시작
		HSSFRow headerRow = sheet.createRow(0);								//엑셀 첫번째 헤더
		headerRow.setHeightInPoints((float) 19.50);							//cell 높이
		
		
		List<String> headerList = new ArrayList<String>();					//첫번째 헤더 리스트
		headerList.add("부서");
		headerList.add("상담원ID");
		headerList.add("상담원명");
		headerList.add("마감구분");
		headerList.add("평균점수");
		for (int i = 1; i <= totalEstiNumCnt; i++) {
			log.debug("topic["+param.getString("TOPIC_"+i));
			headerList.add(param.getString("TOPIC_"+i));									
		}
		//엑셀의 헤더 첫째행 종료		
		
		
		//엑셀의 헤더 두번째행 시작
		HSSFRow headerRow2 = sheet.createRow(1);							//엑셀 두번째 헤더(점수1,점수2,점수3 
		headerRow2.setHeightInPoints((float) 19.50);						//cell 높이
		int tempCnt = 0;
		for (int i = 0; i < totalEstiNumCnt; i++) {							//8을 더해주는 이유는 전전월 평균점수,등수 전월 평균점수,등수,전월비교 현재월 평균점수, 등수, 전월비교 값때문에
			//HSSFCell cell = headerRow2.createCell(i+5);			//부서,상담원ID,상담원명,마감구분,평균점수
			for (int j = 0; j < 3; j++) {
				HSSFCell cell = headerRow2.createCell((i*3)+5+j);			//부서,상담원ID,상담원명,마감구분,평균점수
				cell.setCellStyle(headerStyle);
				cell.setCellValue("점수"+(j+1));
			}
		}
		
		
		//엑셀의 헤더 두번째행 종료
		
		//헤더 머지 시작
		int tempCellIndex = 0;
		int headerListSize = headerList.size();
		
		for (int i = 0; i < headerListSize; i++) {				//헤더 첫번째 행의 데이터 기준
			int cellIndex = i;
			int tempStartIndex = i+tempCellIndex;				//머지를 시작할 cell index
			
			if(i < 5){											//부서,상담원ID,상담원명,마감구분,평균점수 는 위아래로 머지
				sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));	
			}else if(i >= 5){			// i == 7부터 7개의 cell씩 반복이기 때문에 머지 종료 cell index는 +6
				tempCellIndex += 2;
				cellIndex = i+tempCellIndex;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));
			}
			
			HSSFCell cell = headerRow.createCell(tempStartIndex);		//머지후 헤더 값 입력
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headerList.get(i));
		}
		
		 List<OutputResult> list = (List<OutputResult>)model.get("estiCloseList");			//엑셀의 데이터 리스트 
		 if(list != null && list.size() > 0){
			List<String> keyList = list.get(0).getKeyList();							//총 85개 컬럼
			int keyListSize = keyList.size();			//마지막 dept_code는 안쓴기때문에 -1
			//log.debug("keyListSize["+keyListSize+"]");
			int maxColCnt =  5+(3*totalEstiNumCnt);			//부서,상담원ID,상담원명,마감구분,평균점수 후부터 3씩 반복 cell이기 때문에 5 더해준다.  
			for (int i = 0; i < list.size(); i++) {
				OutputResult dataItem = list.get(i);
				HSSFRow row = sheet.createRow(i+2);		//헤더 2행의 밑부터 시작
				row.setHeightInPoints((float) 19.50);
				
				for (int j = 1; j < keyListSize; j++) {			//	1은 NO 엑셀에서는 뺌 
					String key = keyList.get(j);
					
					if(j <= maxColCnt){				//실제 차수별 주제가 있는 데이터까지만 cell에 
						HSSFCell cell = row.createCell(j-1);			//NO는 빼기때문에 -1
						//Object o = dataItem.get(key);
						String data = dataItem.getString(key);
						if(Comm.isDouble(data)){
							cell.setCellValue(Double.parseDouble(data));	
						}else{
							cell.setCellValue(data);
						} 
						
						if(i%2 != 0){							//홀수 , 짝수별 색깔
							cell.setCellStyle(cellbackStyle);
						}else{
							cell.setCellStyle(cellbackWhiteStyle);
						}
					}
				}
			}
		 }
		 
			 
		for (int i = 0; i < headerList.size(); i++) {				//width 조절
			if(i != 2 && i != 3 && i != 4){
				sheet.autoSizeColumn(i);
				int width = sheet.getColumnWidth(i);
				sheet.setColumnWidth(i, width + 500);
			}
		}
		 
		String fileName = ""+model.get("fileName");
		log.debug("fileName["+fileName+"]");
		fileName = new String(fileName.getBytes(), "ISO-8859-1");
		fileName = fileName+".xls";
			
		log.debug("poi excel download =========fileName["+fileName+"]sheetName["+sheetName+"]");
		 
		String userAgent = request.getHeader("User-Agent");
		response.setCharacterEncoding("utf-8");
	    if (userAgent.indexOf("MSIE 5.5") >= 0) {
	    	response.setContentType("doesn/matter");
	    	response.setHeader("Content-Disposition","filename=\""+fileName+"\"");
	    } else {
	    	response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
	    }
		 //row.cr
	}

}
