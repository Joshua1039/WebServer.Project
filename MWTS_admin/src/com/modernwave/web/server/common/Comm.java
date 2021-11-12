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
 * 공통 함수
 * @author chanwook
 *
 * 2013. 4. 23.
 */
public class Comm {
	
	/**
	 * 날짜 포맷팅 함수
	 * @param format
	 * java SimpleDateFormat 에 따른 format string
	 * @param date
	 * 포맷팅 할 날짜
	 * @return
	 * @return : String
	 * 포맷팅 후의 날짜 string
	 * @date : 2013. 4. 23.
	 */
	public static String getFormatDate(String format, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 현재날짜 포맷팅 
	 * @param format
	 * java SimpleDateFormat 에 따른 format string
	 * @return
	 * @return : String
	 * 포맷팅 후의 날짜 string
	 * @date : 2013. 4. 23.
	 */
	public static String getFormatDate(String format){
		Calendar cal = Calendar.getInstance();
		return getFormatDate(format, cal.getTime());
	}
	
	/**
	 * 희망자리수 만큼 왼쪽에 0붙이는 함수
	 * @param str
	 * 원래 string
	 * @param len
	 * 0을 포함한 return할 자리수
	 * @return
	 * @return : String
	 * 0붙은 최종 string
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
	 * null일때 빈string
	 * @param str
	 * null check할 string
	 * @return
	 * @return : String
	 * null이면 "" null아닐땐 원래 string return
	 * @date : 2013. 4. 23.
	 */
	public static String nvl(String str){
		return (str == null || StringUtils.equals(str, "null"))?"":str;
	}
	
	/**
	 * null일때 희망 string으로 변환
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
	 * Object가 null일때 빈 string return
	 * @param o
	 * null check할 object
	 * @return
	 * @return : String
	 * @date : 2013. 4. 23.
	 */
	public static String nvl(Object o){
		return (o == null)?"":""+o;
	}
	
	
	/**
	 * 평가 월을 yearMonth로 return해준다.
	 * @param monthCode
	 * 평가 월 yyyy-MM형식
	 * @return
	 * @return : String
	 * yyMM 형식으로 return
	 * ex) getYEarMonth("2013-04") 일경우 "1304"를 리턴 
	 * @date : 2013. 4. 23.
	 */
	public static String getYearMonth(String monthCode){
		if(monthCode == null)
			return null;
		return monthCode.substring(2,7).replace("-","");
	}
	
	/**
	 * 엑셀 export에서 cell값이 소수인지 확인
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
	 * excel export에서 cell값이 소수이면 소수값, 아닐경우 string 값 할당
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

