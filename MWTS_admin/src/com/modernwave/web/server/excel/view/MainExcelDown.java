package com.modernwave.web.server.excel.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellType;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.modernwave.web.server.framework.dataset.OutputResult;

public class MainExcelDown extends AbstractExcelView{

	private Logger log = Logger.getLogger(this.getClass());
	
	static public void main(String[] a) throws IOException{
		 HSSFWorkbook wb = new HSSFWorkbook();
		    HSSFSheet sheet = wb.createSheet();
		    HSSFRow row = sheet.createRow(0);
		    HSSFCell cell = row.createCell(0);
		    Calendar cal = Calendar.getInstance();
		    cell.setCellValue("00:00:01");
		    
		    HSSFRow row1 = sheet.createRow(1);
		    HSSFCell cell1 = row1.createCell(0);
		    Calendar cal1 = Calendar.getInstance();
		    
		    HSSFCellStyle cellStyle = wb.createCellStyle();
		    
		    
		    
		    HSSFDataFormat df = wb.createDataFormat();
		    cellStyle.setDataFormat(df.getFormat("h:mm:ss"));
		    
		    
		    Calendar cal4 = Calendar.getInstance();
		    cal4.set(1900, 0, 1, 0, 0, 33);
		    
		    System.out.println(cal4.getTimeInMillis());

		    //cell1.setCellValue(new Date(cal4.getTimeInMillis()));
		    cell1.setCellType(Cell.CELL_TYPE_FORMULA);
		    cell1.setCellFormula("TIME(0,15,00)");
		    cell1.setCellStyle(cellStyle);
		    
		    //CellUtil.translateUnicodeValues(cell1);
		    //cell1
		    
		    
		    
		    //System.out.println(cell1.getDateCellValue());
		    //System.out.println(row1.getCell(0).get);
		    
		    //System.out.println(cell1.getCellV());
		    
		    
		    //cell1.setCellType(Cell.CELLTYPE);
//		    HSSFCell cell = row.createCell(0);
//		    cell.setCellValue("Default Palette");
//
//		    //apply some colors from the standard palette,
//		    // as in the previous examples.
//		    //we'll use red text on a lime background
//
//		    HSSFCellStyle style = wb.createCellStyle();
//		    style.setFillForegroundColor(HSSFColor.LIME.index);
//		    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		    HSSFFont font = wb.createFont();
//		    font.setColor(HSSFColor.RED.index);
//		    style.setFont(font);
//
//		    cell.setCellStyle(style);
		    
		    

		    //save with the default palette
		    FileOutputStream out = new FileOutputStream("D:\\download\\default_palette"+cal.getTimeInMillis()+".xls");
		    wb.write(out);
		    out.close();

		    //now, let's replace RED and LIME in the palette
		    // with a more attractive combination
		    // (lovingly borrowed from freebsd.org)
		    
//		    cell.setCellValue("Modified Palette");
//
//		    //creating a custom palette for the workbook
//		    HSSFPalette palette = wb.getCustomPalette();
//
//		    //replacing the standard red with freebsd.org red
//		    palette.setColorAtIndex(HSSFColor.RED.index,
//		            (byte) 21,  //RGB red (0-255)
//		            (byte) 56,    //RGB green
//		            (byte) 66     //RGB blue
//		    );
//		    //replacing lime with freebsd.org gold
//		    palette.setColorAtIndex(HSSFColor.LIME.index, (byte) Integer.parseInt("d1",16), (byte) Integer.parseInt("e5",16), (byte) Integer.parseInt("fe",16));
//
//		    //save with the modified palette
//		    // note that wherever we have previously used RED or LIME, the
//		    // new colors magically appear
//		    out = new FileOutputStream("D:\\download\\modified_palette.xls");
//		    wb.write(out);
//		    out.close();
		                    
	}
//
//	@Override
//	protected void buildExcelDocument(Map<String, Object> map,
//			HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		
//		HSSFSheet newWb = wb.createSheet("aaaa");
//		
//		XSS
//		
//		HSSFCellStyle cellStyle = wb.createCellStyle();
//		cellStyle.setUserStyleName(styleName)
//		cellStyle.setFillBackgroundColor();
//		//ewWb.createRow(rownum)
//	}
//
	
//	final public static byte HEADER_BG_R = (byte) Integer.parseInt("d1",16);
//	final public static byte HEADER_BG_G = (byte) Integer.parseInt("e5",16);
//	final public static byte HEADER_BG_B = (byte) Integer.parseInt("fe",16);
//
//	final public static byte BG_R = (byte) Integer.parseInt("d1",16);
//	final public static byte BG_G = (byte) Integer.parseInt("e5",16);
//	final public static byte BG_B = (byte) Integer.parseInt("fe",16);
	
	
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
		List<String> headerList = (ArrayList<String>)model.get("headerList");
		
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
		
		 if(headerList != null && headerList.size() > 0){
			 
			 HSSFRow headerRow = sheet.createRow(0);
			 headerRow.setHeightInPoints((float) 19.50);
			 for (int i = 0; i < headerList.size(); i++) {
				 HSSFCell cell = headerRow.createCell(i);
				 cell.setCellStyle(headerStyle);
				 cell.setCellValue(headerList.get(i));
			}
		 }
		 
		 List<OutputResult> list = (List<OutputResult>)model.get("excelData");
		 if(list != null && list.size() > 0){
			List<String> keyList = list.get(0).getKeyList();
			HSSFDataFormat df = wb.createDataFormat();
		    
		    
			for (int i = 0; i < list.size(); i++) {
				OutputResult dataItem = list.get(i);
				HSSFRow row = sheet.createRow(i+1);
				row.setHeightInPoints((float) 19.50);
				for (int j = 0; j < keyList.size(); j++) {
					String key = keyList.get(j);
					HSSFCell cell = row.createCell(j);
					
					
					if("REC_DURATION".equals(key)){
						String tempDuration = dataItem.getString(key);
						tempDuration = tempDuration.replaceAll(":", "");
					    cell.setCellType(Cell.CELL_TYPE_FORMULA);
					    cell.setCellFormula("TIME("+tempDuration.substring(0,2)+","+tempDuration.substring(3,4)+","+tempDuration.substring(5,6)+")");
						cell.setCellValue(dataItem.getString(key));
						
						if(i%2 != 0){
							cellbackStyle.setDataFormat(df.getFormat("h:mm:ss"));
							cell.setCellStyle(cellbackStyle);
						}else{
							cellbackWhiteStyle.setDataFormat(df.getFormat("h:mm:ss"));
							cell.setCellStyle(cellbackWhiteStyle);
						}
					}else{
						cell.setCellValue(dataItem.getString(key));
						if(i%2 != 0){
							cell.setCellStyle(cellbackStyle);
						}else{
							cell.setCellStyle(cellbackWhiteStyle);
						}
					}
					
//					if("REC_DURATION".equals(key)){
//						//log.debug("date["+dataItem.getString(key)+"]date["+sdf.parse(dataItem.getString(key))+"]");
//						HSSFCellStyle dateStyle = wb.createCellStyle();
//						
//						//dateStyle.setDataFormat(wb.createDataFormat().getFormat("h:mm:ss"));
//////						String hhmmss = dataItem.getString(key);
////						int hh = Integer.parseInt(hhmmss.substring(0,2));
////						hh = hh+12;
////						String time = hhmmss.substring(2);
//						Calendar cal = Calendar.getInstance();
//						String hhmmss = dataItem.getString(key);
//						
//						//cal.setTime(sdf.parse(dataItem.getString(key))-);
//						//Date date = 
//						cell.setCellValue();
//						cell.setCellStyle(dateStyle); 
//						//cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//					}else{
//						
//						cell.setCellValue(dataItem.getString(key));
//					}
				}
			}
			
		 }
		 
		 if(headerList != null && headerList.size() > 0){
			 
			for (int i = 0; i < headerList.size(); i++) {
				//log.debug("saaaaaafadadfasdfasdf" + i);
				sheet.autoSizeColumn(i);
				int width = sheet.getColumnWidth(i);
				sheet.setColumnWidth(i, width + 500);
				//log.debug("column["+i+"]width["+width+"]afterWidth["+sheet.getColumnWidth(i)+"]");
				//sheet.autoSizeColumn(i);
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
