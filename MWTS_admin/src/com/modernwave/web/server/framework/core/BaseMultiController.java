package com.modernwave.web.server.framework.core;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.modernwave.web.server.framework.dataset.IOData;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;


/**
 * controller에서 parameter를 map에 할당해주고 출력해준다
 * @author chanwook
 *
 */
public class BaseMultiController  extends MultiActionController{

	/**
	 * log
	 */
	private Logger log = Logger.getLogger(BaseMultiController.class);
	
	/**
	 * 생성자
	 */
	public BaseMultiController(){
	}
	
	/**
	 * request parameter가 들어있는 map
	 */
	
	public InputParam inputParam = null;
	
	/**
	 * parameterMethodResolver로 controller에 정의된 다른 메소드를 호출해준다
	 * url 요청시 /url?method=methodName
	 * 위와같이 요청하면  controller안에있는 methodName을 실행한다
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		inputParam = new InputParam();
		//request.setCharacterEncoding("euc-kr");
		//request.setCharacterEncoding("utf-8");
		//log.debug("encoding["+request.getCharacterEncoding()+"]");
		inputParam.setParameter(request);										//parameter print
		//printParam(request);			ghy
		ParameterMethodNameResolver paramMethodNameResolver = new ParameterMethodNameResolver();	
		paramMethodNameResolver.setParamName("method");									//parameter중 method를 찾아내어 값을 추출
		paramMethodNameResolver.setDefaultMethodName("execute");						//method가 없을때 기본으로 실행될 method name
		String methodName = paramMethodNameResolver.getHandlerMethodName(request);
		ModelAndView mav = null;
		//try{
			mav = invokeNamedMethod(methodName, request, response);						//추출한 method name으로 해당 method를 호출
			//일반처리
			return mav;
//		}catch(DataSetException e){				//gridController를 상속받은 모든 exception
//			log.error("dataSet Exception 발생",e);
//			//error page로 gogo
//			request.setAttribute("errorViewMsg", e.getErrViewMsg());
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/xml");
//			PrintWriter out = response.getWriter();
//			out.println("<data>");
//			out.println("<action type='my_error' sid='id' tid='id'>Details</action>");
//			out.println("</data>");
//			return null;
//		}catch(Exception e){
//			log.error("기타 Exception 발생",e);
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/xml");
//			PrintWriter out = response.getWriter();
//			StringBuilder sb = new StringBuilder (); 
//			sb.append("<data>");
//			sb.append("<action type='my_error' sid='some' tid='some'>Details</action>");
//			sb.append("</data>");
//			log.debug(sb.toString());
//			out.println(sb.toString());
//			return null;
//		}
	}

	String resultColumns = "";
	public String getResultColumns() {
		return resultColumns;
	}

	public void setResultColumns(String resultColumns) {
		this.resultColumns = resultColumns;
	}
	
	String keyColumns = "";
	
	public String getKeyColumns() {
		return keyColumns;
	}

	public void setKeyColumns(String keyColumns) {
		this.keyColumns = keyColumns;
	}

	String seqColName = "";
	 
	
	public String getSeqColName() {
		return seqColName;
	}

	public void setSeqColName(String seqColName) {
		this.seqColName = seqColName;
	}
	
	public void renderSimpleJson(HttpServletResponse res, String key, Object o) throws IOException, JSONException {
		OutputResult outResult = new OutputResult();
		outResult.put(key, o);
		renderSimpleJson(res,outResult);
	}
	
	public void renderSimpleJson(HttpServletResponse res, OutputResult outResult) throws IOException, JSONException {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();
		JSONObject jsonObj = new JSONObject();
		Iterator<Entry<String, Object>> iter = outResult.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			jsonObj.put(key, entry.getValue());
			log.debug("key[" + key + "] value["	+ entry.getValue() + "]");
		}
		log.debug(jsonObj.toString());
		out.println(jsonObj.toString());
	}
	

	public void renderSimpleXml(HttpServletResponse res, String key, Object o) throws IOException {
		OutputResult outResult = new OutputResult();
		outResult.put(key, o);
		renderSimpleXml(res,outResult);
	}
	
	public void renderSimpleXml(HttpServletResponse res, OutputResult outResult) throws IOException {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Object>> iter = outResult.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			sb.append("<"+key+"><![CDATA[");
			sb.append(entry.getValue());
			sb.append("]]></"+key+">");
			//log.debug("key[" + key + "] value["	+ entry.getValue() + "]");
		}
		//log.debug(sb.toString());
		out.println(sb.toString());
	}
	
	private String setRowID(IOData record){
		String keyColumn[] = keyColumns.split("\\|");
		String rowKey = "";
		for (String key : keyColumn) {
			try{
			rowKey += record.get(key).toString().trim() + "|";
			}catch(NullPointerException e){
				log.error("잘못된 keyColumn 입력값입니다. - current input key["+key+"]",e);
				throw e;
			}
		}
		//log.debug("value["+rowKey+"]");
		rowKey = rowKey.substring(0,rowKey.length()-1);
		return rowKey;
	}
	
	public void renderListSetXml(HttpServletResponse res,
			List<OutputResult> list) throws Exception {
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
		int listSize = list.size();
	//	log.debug("list size[" + listSize + "]");
		StringBuilder sb = new StringBuilder(); 
		if(listSize <= 0){
		//	log.debug("조회된 리스트가 없을때");
			sb.append("<rows total_count='0' pos='0'></rows>");
		}else{
			sb.append("<rows total_count='" + listSize + "' pos='0'>\n");
			//	log.debug("resultColumns["+resultColumns+"]keyColumns["+keyColumns+"]seqColName["+seqColName+"]");
				for (int i = 0; i < listSize; i++) {
					IOData record = list.get(i);
					if(keyColumns.length() <= 0)
						sb.append("<row id='" + (i + 1) + "'>\n\t");
					else{
						sb.append("<row id='" + setRowID(record) + "'>\n\t");
					}
					
					if(seqColName.length() > 0){
					      sb.append("<cell>"+(i+1)+"</cell>");
					     }
					

					if((resultColumns.length() <=0)){		//가져올 컬럼이 없을때
						Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
						while (iter.hasNext()) {
							Entry<String, Object> entry = iter.next();
							sb.append("<cell><![CDATA[").append(entry.getValue()).append("]]></cell>");
							//log.debug("xml ============ key[" + entry.getKey() + "] value["+ entry.getValue() + "]");
						}
					}else{									//가져올 컬럼이 있을때
						String[] resultColumn = resultColumns.split("\\|");
						for (String stringKey : resultColumn) {
							try{
								sb.append("<cell><![CDATA[").append(record.get(stringKey)).append("]]></cell>");
							//	log.debug("key[" + stringKey + "] value["+ record.get(stringKey) + "]");
							}catch(NullPointerException e){
								log.error("잘못된 resultColumn 입력값입니다. 맵핑된 xml을 확인해주세요- current input key["+stringKey+"]",e);
								throw e;
							}
						}
					}
					sb.append("\n</row>\n");
				}
				sb.append("</rows>");
		}
		log.debug(sb.toString());
		out.println(sb.toString());
		out.flush();
		out.close();
	}
	
	public void renderListSetJson(HttpServletResponse res,
			List<OutputResult> list) throws Exception {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonRow = new JSONArray();
		int listSize = list.size();
		log.debug("resultColumns["+resultColumns+"]keyColumns["+keyColumns+"]seqColName["+seqColName+"]");
		for (int i = 0; i < listSize; i++) {
			IOData record = list.get(i);
			JSONObject jsonRowObj = new JSONObject(); 
			jsonRow.put(i, jsonRowObj);
			if(keyColumns.length() <= 0)
				jsonRowObj.put("id", i+1);
			else{
				jsonRowObj.put("id", setRowID(record));
			}
			JSONArray rowData = new JSONArray();
			if(seqColName.length() > 0){
				rowData.put(i+1);
			     }
			
			if((resultColumns.length() <=0)){		//가져올 컬럼이 없을때
				Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					rowData.put(entry.getValue());
				}
			}else{									//가져올 컬럼이 있을때
				String[] resultColumn = resultColumns.split("\\|");
				for (String stringKey : resultColumn) {
					rowData.put(record.get(stringKey));
				}
			}
			jsonRowObj.put("data", rowData);
		}
		jsonObj.put("rows", jsonRow);
		log.debug(jsonObj.toString());
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}
	
	public void renderTouchListSetJson(HttpServletResponse res, List<OutputResult> list) throws Exception {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();
		JSONArray jsonRow = new JSONArray();
		int listSize = list.size();
		log.debug("resultColumns["+resultColumns+"]keyColumns["+keyColumns+"]seqColName["+seqColName+"]");
		for (int i = 0; i < listSize; i++) {
			IOData record = list.get(i);
			JSONObject jsonRowObj = new JSONObject(); 
			jsonRow.put(i, jsonRowObj);
			if(keyColumns.length() <= 0)
				jsonRowObj.put("id", i+1);
			else{
				jsonRowObj.put("id", setRowID(record));
			}
			
			if(seqColName.length() > 0){
				jsonRowObj.put("seq", i+1);
			}
			
			if((resultColumns.length() <=0)){		//가져올 컬럼이 없을때
				Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					//log.debug("touchlist ============ key[" + entry.getKey() + "] value["+ entry.getValue() + "]");
					jsonRowObj.put(entry.getKey(), entry.getValue());
				}
			}else{									//가져올 컬럼이 있을때
				String[] resultColumn = resultColumns.split("\\|");
				for (String stringKey : resultColumn) {
					jsonRowObj.put(stringKey,record.get(stringKey));
				}
			}
		}
		log.debug(jsonRow.toString());
		out.println(jsonRow.toString());
		out.flush();
		out.close();
	}
	
	
	public void renderComboListSetXml(HttpServletResponse res,
			List<OutputResult> list, String keyColumn, String valueColumn,String firstString) throws Exception {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
		int listSize = list.size();
		//log.debug("list size[" + listSize + "]");
		StringBuilder sb = new StringBuilder();
		sb.append("<complete>");
		if((firstString != null) && (firstString.length() > 0))
			sb.append("<option>").append(firstString).append("</option>");
		
		for (int i = 0; i < listSize; i++) {
			IOData record = list.get(i);
			sb.append("<option value=\"").append(record.get(keyColumn)).append("\">").append(record.get(valueColumn)).append("</option>");
		}
		sb.append("</complete>");
		//log.debug(sb.toString());
		out.println(sb.toString());
		out.flush();
		out.close();
	}
	
	static public void main(String a[]) throws Exception{
		BaseMultiController aa = new BaseMultiController ();
		
		
		ArrayList<OutputResult> list = new ArrayList<OutputResult>();
		for (int i = 0; i < 3; i++) {
			OutputResult output = new OutputResult();
			output.put("AAA", "1111");
			output.put("bbb", "22222");
			output.put("ccc", "333");
			output.put("ddd", "4444");
			output.put("eee", "555");
			output.put("fff", "666");
			output.put("ggg", "77777");
			output.put("hhh", "88");
			list.add(output);
		}
		int listSize = list.size();
		
		long start = System.currentTimeMillis();
		System.out.println("start " +start);
//		aa.renderTouchListSetJson(list);
//		aa.renderListSetJson(list);
		long end = System.currentTimeMillis();
		System.out.println("end " +end);
		System.out.println("차이 " +(end -start));
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((end -start));
		System.out.println(cal.getTime());
		
		JSONArray jsonRow = new JSONArray();
		for (int i = 0; i < 3; i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", i+1);
			obj.put("Package","acx100-source" + i);
			obj.put("Version","20080210-1.1"+i);
			obj.put("Maintainer","Stefano Canepa <sc@linux.it>" + i);
			jsonRow.put(obj);	
		}
		
		
		System.out.println(jsonRow.toString());
		
		
	}
	
}

