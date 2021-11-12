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
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.modernwave.web.server.common.Comm;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;

public class MonthExcelDown extends AbstractExcelView{

	private Logger log = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) {
		System.out.println(CellReference.convertNumToColString(30));
//		HSSFRow row = 
//		System.out.println(CellUtil.translateUnicodeValues(30));
	}
	
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
		 //WritableSheet sheet = wb.createSheet(sheetName);
		//header   d1e5fe
		//cell     e3efff
		//border   a4bed4
		
		//header setting
//		List<String> headerList = (ArrayList<String>)model.get("headerList");
		
		
		
		HSSFCellStyle headerStyle = wb.createCellStyle();
		setBorder(headerStyle, HSSFColor.RED.index, HSSFColor.BLUE.index);
		
		HSSFCellStyle cellbackStyle = wb.createCellStyle();
		setBorder(cellbackStyle, HSSFColor.GREEN.index, HSSFColor.BLUE.index);
		
		HSSFCellStyle cellbackWhiteStyle = wb.createCellStyle();
		setBorder(cellbackWhiteStyle, HSSFColor.WHITE.index, HSSFColor.BLUE.index);
		
		
//		HSSFCellStyle timeStyle = wb.createCellStyle();
//		setBorder(timeStyle, HSSFColor.WHITE.index, HSSFColor.BLUE.index);
		
//		CellStyle cs = wb.createCellStyle();
//		cs.set
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.RED.index, (byte) Integer.parseInt("d1",16), (byte) Integer.parseInt("e5",16), (byte) Integer.parseInt("fe",16));
		palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) Integer.parseInt("e3",16), (byte) Integer.parseInt("ef",16), (byte) Integer.parseInt("ef",16));
		palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) Integer.parseInt("a4",16), (byte) Integer.parseInt("be",16), (byte) Integer.parseInt("d4",16));
		
		
		InputParam param = (InputParam)model.get("headerParam");			//차수별 주제 리스트와 전전월, 전월 현재월 
		
		 //if(headerList != null && headerList.size() > 0){
	
		//엑셀의 헤더 첫째행 시작
		HSSFRow headerRow = sheet.createRow(0);								//엑셀 첫번째 헤더
		headerRow.setHeightInPoints((float) 19.50);							//cell 높이
		
		
		List<String> headerList = new ArrayList<String>();					//첫번째 헤더 리스트
		headerList.add("조(팀)");
		headerList.add("상담원ID");
		headerList.add("상담원명");
		headerList.add("직책");
		headerList.add(param.getString("YEAR_MONTH_1"));					//전전월
		headerList.add(param.getString("YEAR_MONTH_2"));					//전월
		headerList.add(param.getString("YEAR_MONTH_3"));					//현재월
		
		int topicCnt = 0;													//차수별 주제의 총 갯수  (ex 4회차까지평가를 했으면 4)
		int contentCnt = 0;
		for (int i = 1; i <= 10; i++) {										//총 10차 중
			String tempCotentName = param.getString("CONTENT_NAME"+i);			//평가내용별 점수표시 
			if(!StringUtils.isEmpty(tempCotentName)){							
				headerList.add(tempCotentName);									
				contentCnt++;													
			}
		}
		for (int i = 1; i <= 10; i++) {										//총 10차 중
			String tempTopic = param.getString("TOPIC"+i);
			if(!StringUtils.isEmpty(tempTopic)){							//주제가 있는것만
				headerList.add(tempTopic);									//첫번째 헤더 리스트에 더해준다.
				topicCnt++;													//주제가 있으면 count +1
			}
		}
		
		headerList.add("조별평균값");											//맨뒤에 조별 평균값
		//엑셀의 헤더 첫째행 종료		
		
		
		//엑셀의 헤더 두번째행 시작
		HSSFRow headerRow2 = sheet.createRow(1);							//엑셀 두번째 헤더(평균점수,등수,전월비교 등 머지한 엑셀에서 표시되는 두번째 행의  cell 
		headerRow2.setHeightInPoints((float) 19.50);						//cell 높이
		int tempCnt = 0;
		for (int i = 0; i < (topicCnt*7)+8+contentCnt; i++) {							//8을 더해주는 이유는 전전월 평균점수,등수 전월 평균점수,등수,전월비교 현재월 평균점수, 등수, 전월비교 값때문에
			String tempCellValue = "";
			if(i == 0 || i == 2 || i == 5){									//
				tempCellValue = "평균점수"; 
			}
			else if(i == 1 || i == 3 || i == 6){
				tempCellValue = "등수"; 
			}
			else if(i == 4 || i == 7){
				tempCellValue = "전월비교";
			}
			else if(i >= 8+contentCnt){												//cell 8부터 주제별로 7개씩 반복되는 cell
				if(i == 8+contentCnt +(7*(tempCnt)) || i == 10+contentCnt + (7*(tempCnt)) || i == 12+contentCnt +(7*(tempCnt))){		//8,10,12 는 점수 cell
					tempCellValue = "점수";
				}
				else if(i == 9+contentCnt +(7*(tempCnt)) || i == 11+contentCnt + (7*(tempCnt)) || i == 13+contentCnt + (7*(tempCnt))){	//9,11,13은 평가자 cell
					tempCellValue = "평가자";
				}
				else if(i == 14+contentCnt +(7*(tempCnt))){				//14는 확정점수 cell  7개씩 반복의 마지막 cell
					tempCellValue = "확정점수";
					tempCnt++;									//차수별 반복횟수 +1
				}
			}
			if(!StringUtils.isEmpty(tempCellValue)){
				HSSFCell cell = headerRow2.createCell(i+4);			//조,상담원,상담원명,직책 cell4개의 뒤부터 시작하기때문에
				cell.setCellStyle(headerStyle);
				cell.setCellValue(tempCellValue);
			}
		}
		//엑셀의 헤더 두번째행 종료
		
		
		//헤더 머지 시작
		int tempCellIndex = 0;
		int headerListSize = headerList.size();
		
		for (int i = 0; i < headerListSize; i++) {				//헤더 첫번째 행의 데이터 기준
			int cellIndex = i;
			int tempStartIndex = i+tempCellIndex;				//머지를 시작할 cell index
			
			if(i < 4){											//조,상담원,상담원명,직책 은 위아래로 머지
				sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));	
			}
			else if(i == 4){										//i == 4 전전월은 평균점수,등수 두개만 머지하기때문에 머지 종료cell index는 +1만 해준다.   
				tempCellIndex += 1;									
				cellIndex = i+tempCellIndex;						
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));				
			}else if(i == 5 || i == 6){								//i == 5 전월, i == 6 현재월은 평균점수, 등수, 전월비교 세가지를 머지해야되기때문에 머지 종료 cell index는 +2를 더해준다
				tempCellIndex += 2;
				cellIndex = i+tempCellIndex;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));
			}else if(i == (headerListSize-1)){			//마지막 조별 평균
				tempCellIndex += 6;
				sheet.addMergedRegion(new CellRangeAddress(0, 1, tempStartIndex, tempStartIndex));
			}else if(i >= 7+contentCnt){			// i == 7부터 7개의 cell씩 반복이기 때문에 머지 종료 cell index는 +6
				tempCellIndex += 6;
				cellIndex = i+tempCellIndex;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));
			}
			
			HSSFCell cell = headerRow.createCell(tempStartIndex);		//머지후 헤더 값 입력
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headerList.get(i));
		}
		
		
		for (int i = 0; i < contentCnt; i++) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 12+i, 12+i));
		}
		
		
		 
		 
		 List<OutputResult> list = (List<OutputResult>)model.get("excelData");			//엑셀의 데이터 리스트 
		 if(list != null && list.size() > 0){
			List<String> keyList = list.get(0).getKeyList();							//총 85개 컬럼
			list.get(0).printMap();
			int keyListSize = keyList.size()-1;			//마지막 dept_code는 안쓴기때문에 -1
			//log.debug("keyListSize["+keyListSize+"]");
			int maxColCnt =  12+10+(7*topicCnt);			//NO,조,상담원,상담원명,직책, 전전월(2), 전월(3), 현재월(3) 내용별표시(최대10) 후부터 7씩 반복 cell이기 때문에 12 + contentCnt
			
			
			for (int i = 0; i < list.size(); i++) {
				OutputResult dataItem = list.get(i);
				HSSFRow row = sheet.createRow(i+2);		//헤더 2행의 밑부터 시작
				row.setHeightInPoints((float) 19.50);
				int createCellIndex = 0;	
				for (int j = 1; j < keyListSize; j++) {			//모든 컬럼의 갯수 -1(dept_code)	1은 NO 엑셀에서는 뺌 
					String key = keyList.get(j);
					
					if(j <= maxColCnt){				//실제 차수별 주제가 있는 데이터까지만 cell에
						if(j > 12+contentCnt && j <= 22){			//평가 내용별 점수가 없는경우에 화면에서 없어져야 할 영역
						}else{
							HSSFCell cell = row.createCell(createCellIndex++);
						//	log.debug("createCellIndex["+createCellIndex+"]");
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
					}else if( j == (keyListSize-1)){					//조별평균값
						HSSFCell cell = row.createCell(createCellIndex);
						String data = dataItem.getString(key);
						if(Comm.isDouble(data)){
							cell.setCellValue(Double.parseDouble(data));	
						}else{
							cell.setCellValue(data);
						}
						if(i%2 != 0){
							cell.setCellStyle(cellbackStyle);
						}else{
							cell.setCellStyle(cellbackWhiteStyle);
						}
						
						
					}
				}
			}
			//4, 6, 9, 11+contentCnt까지,+1+3+5+6*topicCnt   

			//int totalCellCount = 3+contentCnt+(topicCnt*4)+1;
			
			
			int cells = sheet.getRow(2).getPhysicalNumberOfCells();
			int listSize = list.size()+2;
			HSSFRow row = sheet.createRow(listSize);
			row.setHeightInPoints((float) 19.50);
			for (int i = 0; i < cells; i++) {
				HSSFCell cell = row.createCell(i);
				if(listSize%2 != 0){
					cell.setCellStyle(cellbackStyle);
				}else{
					cell.setCellStyle(cellbackWhiteStyle);
				}
				if(i >= 4){
					String colStr = CellReference.convertNumToColString(i);
					String referenceArr = colStr+"3:"+colStr+listSize;
					cell.setCellType(Cell.CELL_TYPE_FORMULA);
					cell.setCellFormula("IF(COUNT("+referenceArr+") <>0,ROUND(AVERAGE("+referenceArr+"),2),\"\")");
				}
			}
			
			
			
		//	
			
			
			
			
		 }
		 
		 //if(headerList != null && headerList.size() > 0){
			 
		for (int i = 0; i < headerList.size(); i++) {				//width 조절
			//sheet.autoSizeColumn(i);
			int width = sheet.getColumnWidth(i);
			sheet.setColumnWidth(i, width + 500);
		}
		
//		for (int i = 0; i < 10; i++) {
//			String tempTopic = param.getString("TPOIC"+(i+1));
//			log.debug("tempTopic["+tempTopic+"]");
//			if(StringUtils.isEmpty(tempTopic)){
//				int lastRow = sheet.getLastRowNum();
//				for (int j = 2; j < lastRow; j++) {
//					HSSFRow row = sheet.getRow(j);
//					sheet.getRow(j).removeCell(row.getCell(12+(i*7)));
//					sheet.getRow(j).removeCell(row.getCell(13+(i*7)));
//					sheet.getRow(j).removeCell(row.getCell(14+(i*7)));
//					sheet.getRow(j).removeCell(row.getCell(15+(i*7)));
//					sheet.getRow(j).removeCell(row.getCell(16+(i*7)));
//					sheet.getRow(j).removeCell(row.getCell(17+(i*7)));
//					sheet.getRow(j).removeCell(row.getCell(18+(i*7)));
//				}
//			}
//		}

		 
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
