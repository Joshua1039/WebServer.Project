package com.modernwave.web.server.excel.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.modernwave.web.server.common.Comm;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;

public class IndivisualExcelDown extends AbstractExcelView{

	private Logger log = Logger.getLogger(this.getClass());
	
	static public void main(String[] a) throws IOException{
		
		                    
	}
//
	
	
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
		setBorder(cellbackStyle, HSSFColor.YELLOW.index, HSSFColor.BLUE.index);
		
		HSSFCellStyle cellbackWhiteStyle = wb.createCellStyle();
		setBorder(cellbackWhiteStyle, HSSFColor.WHITE.index, HSSFColor.BLUE.index);
		
		
		HSSFCellStyle headerGreyStyle = wb.createCellStyle();
		setBorder(headerGreyStyle, HSSFColor.GREY_25_PERCENT.index, HSSFColor.GREY_40_PERCENT.index);
		
		HSSFCellStyle headerGreenStyle = wb.createCellStyle();
		setBorder(headerGreenStyle, HSSFColor.GREEN.index, HSSFColor.GREY_40_PERCENT.index);
		
		
//		HSSFCellStyle timeStyle = wb.createCellStyle();
//		setBorder(timeStyle, HSSFColor.WHITE.index, HSSFColor.BLUE.index);
		
//		CellStyle cs = wb.createCellStyle();
//		cs.set
		HSSFPalette palette = wb.getCustomPalette();			//�������� ����
		palette.setColorAtIndex(HSSFColor.RED.index, (byte) Integer.parseInt("d1",16), (byte) Integer.parseInt("e5",16), (byte) Integer.parseInt("fe",16));		//������ �ٸ������� ��ü
		palette.setColorAtIndex(HSSFColor.YELLOW.index, (byte) Integer.parseInt("e3",16), (byte) Integer.parseInt("ef",16), (byte) Integer.parseInt("ef",16));	//�ʷϻ��� �ٸ������� ��ü
		palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) Integer.parseInt("a4",16), (byte) Integer.parseInt("be",16), (byte) Integer.parseInt("d4",16));	//�Ķ����� �ٸ������� ��ü
		palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) Integer.parseInt("d6",16), (byte) Integer.parseInt("f1",16), (byte) Integer.parseInt("ba",16));	//GREEN�� �ٸ������� ��ü 
																													
		
//		 if(headerList != null && headerList.size() > 0){
//			 
		 ArrayList<String> infoHeaderList = new ArrayList<String>();
		 infoHeaderList.add("���");
		 infoHeaderList.add("��(��)");
		 infoHeaderList.add("�̸�(��å)");
		 infoHeaderList.add("����");
		 infoHeaderList.add("����(����/��ü)");
		 infoHeaderList.add("���/��ü");
		 infoHeaderList.add("���/��å");
		 infoHeaderList.add("�� Sheet");
		 infoHeaderList.add("����");
		 
		 int currRowCnt = 0;
		 //System.out.println("currRowCnt["+currRowCnt+"]");
		 HSSFRow infoHederRow = sheet.createRow(currRowCnt++);
		// System.out.println("currRowCnt["+currRowCnt+"]");
		 infoHederRow.setHeightInPoints((float) 19.50);
		 for (int i = 0; i < infoHeaderList.size(); i++) {		//header ����
			 HSSFCell cell = infoHederRow.createCell(i);
			 Comm.setCellValue(cell, infoHeaderList.get(i));
			 cell.setCellStyle(headerGreyStyle);
		 }
		 
		 InputParam agentInfo = (InputParam)model.get("agentInfo");
		 HSSFRow infoBodyRow = sheet.createRow(currRowCnt++);
		 //System.out.println("currRowCnt2["+currRowCnt+"]");
		 infoBodyRow.setHeightInPoints((float) 19.50);
		 String agentName = "";
		 String tempSheetName = "";
		 if(agentInfo != null){
			 agentName = agentInfo.getString("AGENT_NAME");
			 tempSheetName = agentInfo.getString("SHEET_NAME");
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 0, agentInfo.getString("RET_AGENT_ID"));
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 1, agentInfo.getString("DEPT_NAME"));
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 2, agentName);
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 3, agentInfo.getString("INNER_PHONE_NUM"));
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 4, agentInfo.getString("AGENT_POINT") + " / " + agentInfo.getString("TOTAL_POINT"));
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 5, agentInfo.getString("TOTAL_AGENT_RANK") + " / " + agentInfo.getString("TOTAL_AGENT_COUNT"));
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 6, agentInfo.getString("LEVEL_AGENT_RANK") + " / " + agentInfo.getString("LEVEL_AGENT_COUNT"));
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 7, tempSheetName);
			 setTempCell(infoBodyRow, cellbackWhiteStyle, 8, agentInfo.getString("CAPTAIN_QA_NAME"));
		 }
		 
		 currRowCnt++;			//���� ����
		 
		 HSSFRow callListHeaderRow = sheet.createRow(currRowCnt++);
		 //System.out.println("currRowCnt3["+currRowCnt+"]");
		 callListHeaderRow.setHeightInPoints((float) 19.50);
		 
		 ArrayList<String> callListHeaderList = new ArrayList<String>();
		 callListHeaderList.add("��ȭ����");
		 callListHeaderList.add("�� ��");
		 callListHeaderList.add("����");
		 callListHeaderList.add("���");
		 callListHeaderList.add("����");
		 callListHeaderList.add("���(������)");
		 for (int i = 0; i < callListHeaderList.size(); i++) {		//header ����
			 HSSFCell cell = callListHeaderRow.createCell(i);
			 cell.setCellValue(callListHeaderList.get(i));
			 cell.setCellStyle(headerStyle);
		}
		 
		 List<OutputResult> callList  = (List<OutputResult>)model.get("callList");
		 if(callList != null && callList.size() > 0){
			 
			 List<String> keyList = callList.get(0).getKeyList();
			 
				for (int i = 0; i < callList.size(); i++) {
					HSSFRow row = sheet.createRow(currRowCnt++);
					//System.out.println("currRowCnt4["+currRowCnt+"]");
					row.setHeightInPoints((float) 19.50);
					OutputResult dataItem = callList.get(i);
					
					for (int j = 0; j < keyList.size(); j++) {
						String key = keyList.get(j);
						HSSFCell cell = row.createCell(j);
						 Comm.setCellValue(cell, dataItem.getString(key));
						if(i%2 != 0){
							cell.setCellStyle(cellbackStyle);
						}else{
							cell.setCellStyle(cellbackWhiteStyle);
						}
					}
				}
		 }
		 
		 currRowCnt++;			//���� ����
		 
		 List<OutputResult> statisticsList = (List<OutputResult>)model.get("statisticsList");
		 
		 if(statisticsList != null && statisticsList.size() > 0){
		 //sb.append("<table class=\"cts_table\">\n");	
			for (int i = 0; i < statisticsList.size(); i++) {
				HSSFRow row = sheet.createRow(currRowCnt++);
				//System.out.println("currRowCnt5["+currRowCnt+"]");
				row.setHeightInPoints((float) 19.50);
				
				String colName = "ESTI_YEAR_MONTH_";
				//sb.append("<tr>\n");
				OutputResult item = statisticsList.get(i);
				HSSFCell cell = row.createCell(0);
				switch(i){
				case 0:		//�򰡿�
					cell.setCellStyle(headerGreenStyle);
					cell.setCellValue("�򰡿�");
					break;
				case 1:		//sheet ���
					cell.setCellStyle(cellbackWhiteStyle);
					cell.setCellValue(tempSheetName+" ���");
					break;
				case 2:		//�� ���
					cell.setCellStyle(cellbackWhiteStyle);
					cell.setCellValue("�� ���");
					break;
				case 3:		//��å ��� 
					cell.setCellStyle(cellbackWhiteStyle);
					cell.setCellValue("��å ���");
					break;
				case 4:		//�̸�
					cell.setCellStyle(cellbackWhiteStyle);
					cell.setCellValue(agentName);
					break;
				case 5:		//���/��ü
					cell.setCellStyle(cellbackWhiteStyle);
					cell.setCellValue("���/��ü");
					break;
				case 6:		//���� %
					cell.setCellStyle(cellbackWhiteStyle);
					cell.setCellValue("����%");
					break;
				}
				for (int j = 1; j <= 12; j++) {
					HSSFCell cell2 = row.createCell(j);
					if(i == 0){							//�ʷϻ� cell
						cell2.setCellStyle(headerGreenStyle);
						//cell2.setCellValue(item.getString(colName+j));
						Comm.setCellValue(cell2, item.getString(colName+j));
					}else{
						cell2.setCellStyle(cellbackWhiteStyle);
						cell2.setCellValue(item.getString(colName+j));
						Comm.setCellValue(cell2, item.getString(colName+j));
//						 Comm.setCellValue(cell, dataItem.getString(key));
						//sb.append("<td class=\"center\">"+item.getString(colName+j)+"</td>\n");
//						if(i > 0 && j == 12 && i <= 4){		//���
//							arr[i-1] = item.getString(colName+j); 
//						}
					}
				}
		 
			}
		 }
		 
		for (int i = 0; i < 13; i++) {
			//log.debug("saaaaaafadadfasdfasdf" + i);
			sheet.autoSizeColumn(i);
			int width = sheet.getColumnWidth(i);
			sheet.setColumnWidth(i, width + 500);
			//log.debug("column["+i+"]width["+width+"]afterWidth["+sheet.getColumnWidth(i)+"]");
			//sheet.autoSizeColumn(i);
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
	}
	
	private void setTempCell(HSSFRow row, HSSFCellStyle cellStyle, int cellIndex, String value){
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyle);
		Comm.setCellValue(cell, value);
		//cell.setCellValue(value);      //agentInfo.getString("RET_AGENT_ID")
	}
	

}
