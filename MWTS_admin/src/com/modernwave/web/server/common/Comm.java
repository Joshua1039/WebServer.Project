package com.modernwave.web.server.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ���� �Լ�
 * @author chanwook
 *
 * 2013. 4. 23.
 */
public class Comm {
	
	/**
	 * ��¥ ������ �Լ�
	 * @param format
	 * java SimpleDateFormat �� ���� format string
	 * @param date
	 * ������ �� ��¥
	 * @return
	 * @return : String
	 * ������ ���� ��¥ string
	 * @date : 2013. 4. 23.
	 */
	public static String getFormatDate(String format, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * ���糯¥ ������ 
	 * @param format
	 * java SimpleDateFormat �� ���� format string
	 * @return
	 * @return : String
	 * ������ ���� ��¥ string
	 * @date : 2013. 4. 23.
	 */
	public static String getFormatDate(String format){
		Calendar cal = Calendar.getInstance();
		return getFormatDate(format, cal.getTime());
	}
	
	/**
	 * ����ڸ��� ��ŭ ���ʿ� 0���̴� �Լ�
	 * @param str
	 * ���� string
	 * @param len
	 * 0�� ������ return�� �ڸ���
	 * @return
	 * @return : String
	 * 0���� ���� string
	 * ex) addLeftNum("34",7)
	 * -> return "0000034"
	 * @date : 2013. 4. 23.
	 */
	public static String addLeftNum(String str, int len){
		if(str == null)
			return str;
		str = "00000000000000000000000000000000000000000000000"+str;
		return str.substring(str.length() -len);
	}
	
	/**
	 * null�϶� ��string
	 * @param str
	 * null check�� string
	 * @return
	 * @return : String
	 * null�̸� "" null�ƴҶ� ���� string return
	 * @date : 2013. 4. 23.
	 */
	public static String nvl(String str){
		return (str == null || StringUtils.equals(str, "null"))?"":str;
	}
	
	/**
	 * null�϶� ��� string���� ��ȯ
	 * @param str
	 * @param nvl
	 * @return
	 * @return : String
	 * @date : 2013. 4. 23.
	 */
	public static String nvl(String str, String nvl){
		return (str == null || StringUtils.equals(str, "null"))?nvl:str;
	}
	
	/**
	 * Object�� null�϶� �� string return
	 * @param o
	 * null check�� object
	 * @return
	 * @return : String
	 * @date : 2013. 4. 23.
	 */
	public static String nvl(Object o){
		return (o == null)?"":""+o;
	}
	
	
	/**
	 * �� ���� yearMonth�� return���ش�.
	 * @param monthCode
	 * �� �� yyyy-MM����
	 * @return
	 * @return : String
	 * yyMM �������� return
	 * ex) getYEarMonth("2013-04") �ϰ�� "1304"�� ���� 
	 * @date : 2013. 4. 23.
	 */
	public static String getYearMonth(String monthCode){
		if(monthCode == null)
			return null;
		return monthCode.substring(2,7).replace("-","");
	}
	
	/**
	 * ���� export���� cell���� �Ҽ����� Ȯ��
	 * @param str
	 * @return
	 * @return : boolean
	 * @date : 2013. 4. 23.
	 */
	static public boolean isDouble(String str){
		if(StringUtils.isEmpty(str))
			return false;
		str = str.replace(".", "");
		if(StringUtils.isNumeric(str))
			return true;
		else
			return false;
	}
	
	/**
	 * excel export���� cell���� �Ҽ��̸� �Ҽ���, �ƴҰ�� string �� �Ҵ�
	 * @param cell
	 * @param value
	 * @return : void
	 * @date : 2013. 4. 23.
	 */
	static public void setCellValue(HSSFCell cell, String value){
		if(cell == null)
			return;
		if(isDouble(value)){
			cell.setCellValue(Double.parseDouble(value));
		}else{
			cell.setCellValue(value);
		}
	}
}

