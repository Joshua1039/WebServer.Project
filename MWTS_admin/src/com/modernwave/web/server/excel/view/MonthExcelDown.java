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
		
		
		InputParam param = (InputParam)model.get("headerParam");			//������ ���� ����Ʈ�� ������, ���� ����� 
		
		 //if(headerList != null && headerList.size() > 0){
	
		//������ ��� ù°�� ����
		HSSFRow headerRow = sheet.createRow(0);								//���� ù��° ���
		headerRow.setHeightInPoints((float) 19.50);							//cell ����
		
		
		List<String> headerList = new ArrayList<String>();					//ù��° ��� ����Ʈ
		headerList.add("��(��)");
		headerList.add("����ID");
		headerList.add("������");
		headerList.add("��å");
		headerList.add(param.getString("YEAR_MONTH_1"));					//������
		headerList.add(param.getString("YEAR_MONTH_2"));					//����
		headerList.add(param.getString("YEAR_MONTH_3"));					//�����
		
		int topicCnt = 0;													//������ ������ �� ����  (ex 4ȸ�������򰡸� ������ 4)
		int contentCnt = 0;
		for (int i = 1; i <= 10; i++) {										//�� 10�� ��
			String tempCotentName = param.getString("CONTENT_NAME"+i);			//�򰡳��뺰 ����ǥ�� 
			if(!StringUtils.isEmpty(tempCotentName)){							
				headerList.add(tempCotentName);									
				contentCnt++;													
			}
		}
		for (int i = 1; i <= 10; i++) {										//�� 10�� ��
			String tempTopic = param.getString("TOPIC"+i);
			if(!StringUtils.isEmpty(tempTopic)){							//������ �ִ°͸�
				headerList.add(tempTopic);									//ù��° ��� ����Ʈ�� �����ش�.
				topicCnt++;													//������ ������ count +1
			}
		}
		
		headerList.add("������հ�");											//�ǵڿ� ���� ��հ�
		//������ ��� ù°�� ����		
		
		
		//������ ��� �ι�°�� ����
		HSSFRow headerRow2 = sheet.createRow(1);							//���� �ι�° ���(�������,���,������ �� ������ �������� ǥ�õǴ� �ι�° ����  cell 
		headerRow2.setHeightInPoints((float) 19.50);						//cell ����
		int tempCnt = 0;
		for (int i = 0; i < (topicCnt*7)+8+contentCnt; i++) {							//8�� �����ִ� ������ ������ �������,��� ���� �������,���,������ ����� �������, ���, ������ ��������
			String tempCellValue = "";
			if(i == 0 || i == 2 || i == 5){									//
				tempCellValue = "�������"; 
			}
			else if(i == 1 || i == 3 || i == 6){
				tempCellValue = "���"; 
			}
			else if(i == 4 || i == 7){
				tempCellValue = "������";
			}
			else if(i >= 8+contentCnt){												//cell 8���� �������� 7���� �ݺ��Ǵ� cell
				if(i == 8+contentCnt +(7*(tempCnt)) || i == 10+contentCnt + (7*(tempCnt)) || i == 12+contentCnt +(7*(tempCnt))){		//8,10,12 �� ���� cell
					tempCellValue = "����";
				}
				else if(i == 9+contentCnt +(7*(tempCnt)) || i == 11+contentCnt + (7*(tempCnt)) || i == 13+contentCnt + (7*(tempCnt))){	//9,11,13�� ���� cell
					tempCellValue = "����";
				}
				else if(i == 14+contentCnt +(7*(tempCnt))){				//14�� Ȯ������ cell  7���� �ݺ��� ������ cell
					tempCellValue = "Ȯ������";
					tempCnt++;									//������ �ݺ�Ƚ�� +1
				}
			}
			if(!StringUtils.isEmpty(tempCellValue)){
				HSSFCell cell = headerRow2.createCell(i+4);			//��,����,������,��å cell4���� �ں��� �����ϱ⶧����
				cell.setCellStyle(headerStyle);
				cell.setCellValue(tempCellValue);
			}
		}
		//������ ��� �ι�°�� ����
		
		
		//��� ���� ����
		int tempCellIndex = 0;
		int headerListSize = headerList.size();
		
		for (int i = 0; i < headerListSize; i++) {				//��� ù��° ���� ������ ����
			int cellIndex = i;
			int tempStartIndex = i+tempCellIndex;				//������ ������ cell index
			
			if(i < 4){											//��,����,������,��å �� ���Ʒ��� ����
				sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));	
			}
			else if(i == 4){										//i == 4 �������� �������,��� �ΰ��� �����ϱ⶧���� ���� ����cell index�� +1�� ���ش�.   
				tempCellIndex += 1;									
				cellIndex = i+tempCellIndex;						
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));				
			}else if(i == 5 || i == 6){								//i == 5 ����, i == 6 ������� �������, ���, ������ �������� �����ؾߵǱ⶧���� ���� ���� cell index�� +2�� �����ش�
				tempCellIndex += 2;
				cellIndex = i+tempCellIndex;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));
			}else if(i == (headerListSize-1)){			//������ ���� ���
				tempCellIndex += 6;
				sheet.addMergedRegion(new CellRangeAddress(0, 1, tempStartIndex, tempStartIndex));
			}else if(i >= 7+contentCnt){			// i == 7���� 7���� cell�� �ݺ��̱� ������ ���� ���� cell index�� +6
				tempCellIndex += 6;
				cellIndex = i+tempCellIndex;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, tempStartIndex, cellIndex));
			}
			
			HSSFCell cell = headerRow.createCell(tempStartIndex);		//������ ��� �� �Է�
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headerList.get(i));
		}
		
		
		for (int i = 0; i < contentCnt; i++) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 12+i, 12+i));
		}
		
		
		 
		 
		 List<OutputResult> list = (List<OutputResult>)model.get("excelData");			//������ ������ ����Ʈ 
		 if(list != null && list.size() > 0){
			List<String> keyList = list.get(0).getKeyList();							//�� 85�� �÷�
			list.get(0).printMap();
			int keyListSize = keyList.size()-1;			//������ dept_code�� �Ⱦ��⶧���� -1
			//log.debug("keyListSize["+keyListSize+"]");
			int maxColCnt =  12+10+(7*topicCnt);			//NO,��,����,������,��å, ������(2), ����(3), �����(3) ���뺰ǥ��(�ִ�10) �ĺ��� 7�� �ݺ� cell�̱� ������ 12 + contentCnt
			
			
			for (int i = 0; i < list.size(); i++) {
				OutputResult dataItem = list.get(i);
				HSSFRow row = sheet.createRow(i+2);		//��� 2���� �غ��� ����
				row.setHeightInPoints((float) 19.50);
				int createCellIndex = 0;	
				for (int j = 1; j < keyListSize; j++) {			//��� �÷��� ���� -1(dept_code)	1�� NO ���������� �� 
					String key = keyList.get(j);
					
					if(j <= maxColCnt){				//���� ������ ������ �ִ� �����ͱ����� cell��
						if(j > 12+contentCnt && j <= 22){			//�� ���뺰 ������ ���°�쿡 ȭ�鿡�� �������� �� ����
						}else{
							HSSFCell cell = row.createCell(createCellIndex++);
						//	log.debug("createCellIndex["+createCellIndex+"]");
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
					}else if( j == (keyListSize-1)){					//������հ�
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
			//4, 6, 9, 11+contentCnt����,+1+3+5+6*topicCnt   

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
			 
		for (int i = 0; i < headerList.size(); i++) {				//width ����
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
