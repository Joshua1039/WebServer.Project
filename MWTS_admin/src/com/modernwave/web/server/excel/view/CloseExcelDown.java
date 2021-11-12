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
		
	
		//������ ��� ù°�� ����
		HSSFRow headerRow = sheet.createRow(0);								//���� ù��° ���
		headerRow.setHeightInPoints((float) 19.50);							//cell ����
		
		
		List<String> headerList = new ArrayList<String>();					//ù��° ��� ����Ʈ
		headerList.add("�μ�");
		headerList.add("����ID");
		headerList.add("������");
		headerList.add("��������");
		headerList.add("�������");
		for (int i = 1; i <= totalEstiNumCnt; i++) {
			log.debug("topic["+param.getString("TOPIC_"+i));
			headerList.add(param.getString("TOPIC_"+i));									
		}
		//������ ��� ù°�� ����		
		
		
		//������ ��� �ι�°�� ����
		HSSFRow headerRow2 = sheet.createRow(1);							//���� �ι�° ���(����1,����2,����3 
		headerRow2.setHeightInPoints((float) 19.50);						//cell ����
		int tempCnt = 0;
		for (int i = 0; i < totalEstiNumCnt; i++) {							//8�� �����ִ� ������ ������ �������,��� ���� �������,���,������ ����� �������, ���, ������ ��������
			//HSSFCell cell = headerRow2.createCell(i+5);			//�μ�,����ID,������,��������,�������
			for (int j = 0; j < 3; j++) {
				HSSFCell cell = headerRow2.createCell((i*3)+5+j);			//�μ�,����ID,������,��������,�������
				cell.setCellStyle(headerStyle);
				cell.setCellValue("����"+(j+1));
			}
		}
		
		
		//������ ��� �ι�°�� ����
		
		//��� ���� ����
		int tempCellIndex = 0;
		int headerListSize = headerList.size();
		
		for (int i = 0; i < headerListSize; i++) {				//��� ù��° ���� ������ ����
			int cellIndex = i;
			int tempStartIndex = i+tempCellIndex;				//������ ������ cell index
			
			if(i < 5){											//�μ�,����ID,������,��������,������� �� ���Ʒ��� ����
				sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));	
			}else if(i >= 5){			// i == 7���� 7���� cell�� �ݺ��̱� ������ ���� ���� cell index�� +6
				tempCellIndex += 2;
				cellIndex = i+tempCellIndex;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));
			}
			
			HSSFCell cell = headerRow.createCell(tempStartIndex);		//������ ��� �� �Է�
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headerList.get(i));
		}
		
		 List<OutputResult> list = (List<OutputResult>)model.get("estiCloseList");			//������ ������ ����Ʈ 
		 if(list != null && list.size() > 0){
			List<String> keyList = list.get(0).getKeyList();							//�� 85�� �÷�
			int keyListSize = keyList.size();			//������ dept_code�� �Ⱦ��⶧���� -1
			//log.debug("keyListSize["+keyListSize+"]");
			int maxColCnt =  5+(3*totalEstiNumCnt);			//�μ�,����ID,������,��������,������� �ĺ��� 3�� �ݺ� cell�̱� ������ 5 �����ش�.  
			for (int i = 0; i < list.size(); i++) {
				OutputResult dataItem = list.get(i);
				HSSFRow row = sheet.createRow(i+2);		//��� 2���� �غ��� ����
				row.setHeightInPoints((float) 19.50);
				
				for (int j = 1; j < keyListSize; j++) {			//	1�� NO ���������� �� 
					String key = keyList.get(j);
					
					if(j <= maxColCnt){				//���� ������ ������ �ִ� �����ͱ����� cell�� 
						HSSFCell cell = row.createCell(j-1);			//NO�� ���⶧���� -1
						//Object o = dataItem.get(key);
						String data = dataItem.getString(key);
						if(Comm.isDouble(data)){
							cell.setCellValue(Double.parseDouble(data));	
						}else{
							cell.setCellValue(data);
						} 
						
						if(i%2 != 0){							//Ȧ�� , ¦���� ����
							cell.setCellStyle(cellbackStyle);
						}else{
							cell.setCellStyle(cellbackWhiteStyle);
						}
					}
				}
			}
		 }
		 
			 
		for (int i = 0; i < headerList.size(); i++) {				//width ����
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
