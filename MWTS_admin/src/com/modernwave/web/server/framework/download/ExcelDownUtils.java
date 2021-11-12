package com.modernwave.web.server.framework.download;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.modernwave.web.server.framework.dataset.OutputResult;

public class ExcelDownUtils extends AbstractJExcelView{
	
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			WritableWorkbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//request.setCharacterEncoding("utf-8");
		String sheetName = ""+model.get("sheetName");
		 WritableSheet sheet = wb.createSheet(sheetName, 0);
		 
		 String fileName = ""+model.get("fileName");
		 log.debug("fileName["+fileName+"]");
		 fileName = new String(fileName.getBytes(), "ISO-8859-1");
		 fileName = fileName+".xls";
		 
		 log.debug("excel download =========fileName["+fileName+"]sheetName["+sheetName+"]");
		 
		 
		 
//		 ArrayList<String> headerNameList = (ArrayList<String>)model.get("headerNameList");
//		 if(headerNameList != null){
//			 log.debug("size["+headerNameList.size()+"]");
//			 for (int i = 0; i < headerNameList.size(); i++) {		//set header
//				 String headerName = headerNameList.get(i);
//				 log.debug("headerName["+headerName+"]");
//				 sheet.addCell(new Label(i, 0, headerName));
//			}
		 
		 
			 List<OutputResult> result = (List<OutputResult>) model.get("excelData");
			 if(result != null && result.size() > 0){
				 OutputResult resultItem = result.get(0);
				Iterator<Entry<String, Object>> iter = resultItem.entrySet().iterator();
				ArrayList<String> columnNameList = new ArrayList<String>();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					String key = entry.getKey();
					columnNameList.add(key);
					//jsonObj.put(key, entry.getValue());
					log.debug("key[" + key + "] value["	+ entry.getValue() + "]");
				}
				
				WritableCellFormat titleFormat = new WritableCellFormat();
//				Colour co = titleFormat.getBackgroundColour();
//				co.VIOLET2.getValue();
				
				//DisplayFormat df = null;
				//df.
				//WritableCell cell = new WritableCell
				
				//DisplayFormat df = new Dis
				//jxl.format.Pattern pattern = new Pattern
				//Colour.SKY_BLUE
				
				//titleFormat.setBackground(new ColourEx(Colour.VIOLET2.getValue(), Colour.VIOLET2.getDescription(),0xd1, 0xe5, 0xFe));
				
				//Colour.
				
				//titleFormat.setBackground();
				//new ColourEx(0xd1e5fe,"zz",0xd1, 0xe5, 0xFe)
				//Colour.getInternalColour(arg0)
				
				//jxl.format.Colour ccc = new RGB(r, g, b);
				//titleFormat.setBorder(Border.ALL, BorderLineStyle.THICK);
				
				//Integer.parseInt(s, 16);
				//Color color = new Color(Integer.parseInt("d1",16), Integer.parseInt("e5",16), Integer.parseInt("fe",16));
				
				
				
				//Colour colour = new Colou
//				titleFormat.setBackground(color);
//				titleFormat.setBorder(b, ls);
				
				//cellFormat.
				
				for (int i = 0; i < columnNameList.size(); i++) {		//set header
					 String headerName = columnNameList.get(i);
//					 log.debug("headerName["+headerName+"]");
					 //CellFormat cellFormat = label.getCellFormat();
					 Label label = new Label(i, 0, headerName, titleFormat);
					 
					 sheet.addCell(label);
				}
				
				//header   d1e5fe
				//cell     e3efff
				//border   a4bed4
				
				 
				 for (int i = 0; i < result.size(); i++) {
					OutputResult item = result.get(i);
					 for (int j = 0; j < columnNameList.size(); j++) {
					 String columnName = columnNameList.get(j);
					 log.debug("column["+columnName+"]value["+item.getString(columnName)+"]");
					 sheet.addCell(new Label(j, i+1 , item.getString(columnName)));	 
					 }
				 }
			 }
				 
		 String userAgent = request.getHeader("User-Agent");
		 response.setCharacterEncoding("utf-8");
	       if (userAgent.indexOf("MSIE 5.5") >= 0) {
	           response.setContentType("doesn/matter");
	           response.setHeader("Content-Disposition","filename=\""+fileName+"\"");
	       } else {
	           response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
	       }
	}
	
	
	class ColourEx extends Colour{

		protected ColourEx(int val, String s, int r, int g, int b) {
			super(val, s, r, g, b);
			// TODO Auto-generated constructor stub
		}
		
	}
	
}
