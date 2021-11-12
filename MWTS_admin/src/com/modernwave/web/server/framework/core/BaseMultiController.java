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
 * controller���� parameter�� map�� �Ҵ����ְ� ������ش�
 * @author chanwook
 *
 */
public class BaseMultiController  extends MultiActionController{

	/**
	 * log
	 */
	private Logger log = Logger.getLogger(BaseMultiController.class);
	
	/**
	 * ������
	 */
	public BaseMultiController(){
	}
	
	/**
	 * request parameter�� ����ִ� map
	 */
	
	public InputParam inputParam = null;
	
	/**
	 * parameterMethodResolver�� controller�� ���ǵ� �ٸ� �޼ҵ带 ȣ�����ش�
	 * url ��û�� /url?method=methodName
	 * ���Ͱ��� ��û�ϸ�  controller�ȿ��ִ� methodName�� �����Ѵ�
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
		paramMethodNameResolver.setParamName("method");									//parameter�� method�� ã�Ƴ��� ���� ����
		paramMethodNameResolver.setDefaultMethodName("execute");						//method�� ������ �⺻���� ����� method name
		String methodName = paramMethodNameResolver.getHandlerMethodName(request);
		ModelAndView mav = null;
		//try{
			mav = invokeNamedMethod(methodName, request, response);						//������ method name���� �ش� method�� ȣ��
			//�Ϲ�ó��
			return mav;
//		}catch(DataSetException e){				//gridController�� ��ӹ��� ��� exception
//			log.error("dataSet Exception �߻�",e);
//			//error page�� gogo
//			request.setAttribute("errorViewMsg", e.getErrViewMsg());
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/xml");
//			PrintWriter out = response.getWriter();
//			out.println("<data>");
//			out.println("<action type='my_error' sid='id' tid='id'>Details</action>");
//			out.println("</data>");
//			return null;
//		}catch(Exception e){
//			log.error("��Ÿ Exception �߻�",e);
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
				log.error("�߸��� keyColumn �Է°��Դϴ�. - current input key["+key+"]",e);
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
		//	log.debug("��ȸ�� ����Ʈ�� ������");
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
					

					if((resultColumns.length() <=0)){		//������ �÷��� ������
						Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
						while (iter.hasNext()) {
							Entry<String, Object> entry = iter.next();
							sb.append("<cell><![CDATA[").append(entry.getValue()).append("]]></cell>");
							//log.debug("xml ============ key[" + entry.getKey() + "] value["+ entry.getValue() + "]");
						}
					}else{									//������ �÷��� ������
						String[] resultColumn = resultColumns.split("\\|");
						for (String stringKey : resultColumn) {
							try{
								sb.append("<cell><![CDATA[").append(record.get(stringKey)).append("]]></cell>");
							//	log.debug("key[" + stringKey + "] value["+ record.get(stringKey) + "]");
							}catch(NullPointerException e){
								log.error("�߸��� resultColumn �Է°��Դϴ�. ���ε� xml�� Ȯ�����ּ���- current input key["+stringKey+"]",e);
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
			
			if((resultColumns.length() <=0)){		//������ �÷��� ������
				Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					rowData.put(entry.getValue());
				}
			}else{									//������ �÷��� ������
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
			
			if((resultColumns.length() <=0)){		//������ �÷��� ������
				Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					//log.debug("touchlist ============ key[" + entry.getKey() + "] value["+ entry.getValue() + "]");
					jsonRowObj.put(entry.getKey(), entry.getValue());
				}
			}else{									//������ �÷��� ������
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
		System.out.println("���� " +(end -start));
		
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

