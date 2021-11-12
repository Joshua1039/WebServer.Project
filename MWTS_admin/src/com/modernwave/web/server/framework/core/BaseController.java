package com.modernwave.web.server.framework.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.modernwave.web.server.common.Comm;
import com.modernwave.web.server.framework.dataset.IOData;
import com.modernwave.web.server.framework.dataset.JSONResult;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.model.UserVo;

public class BaseController {
	
	private Logger log = Logger.getLogger(this.getClass());	

	public void setRequestAttribute(String name, Object value) {
		RequestContextHolder.currentRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
	}
	
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	public static final String SESSION_USER = "SESSION_USER";
	
	public UserVo getUser(){
		return (UserVo)getRequestAttribute(SESSION_USER);
	}
	
	public String getUserId(){
		UserVo userVo = getUser();
		return (userVo == null)?null:userVo.getUserId();
	}

	public String getUserName(){
		UserVo userVo = getUser();
		return (userVo == null)?null:userVo.getUserName();
	}
	
	public String getUserGrade(){
		UserVo userVo = getUser();
		return (userVo == null)?null:userVo.getGradeCode();
	}
	
	public String getUserTheme(){
		UserVo userVo = getUser();
		return (userVo == null)?null:userVo.getUserTheme();
	}	
	
	public String getListenYn(){
		UserVo userVo = getUser();
		return (userVo == null)?null:userVo.getListenYn();
	}
	
	public String getSaveYn(){
		UserVo userVo = getUser();
		return (userVo == null)?null:userVo.getSaveYn();
	}
	
	public static String getServerIp(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Autowired
	private MessageSource messageSource;	
	
	public String getMessage(String messgeCode){
		return messageSource.getMessage(messgeCode, null, "", Locale.getDefault());
	}
	
	public static final String RET_CODE = "RET";
	public static final String RET_MSG = "RET_MSG";
	public static final String RET_SUCC = "0";
	public static final String RET_ERROR = "-999";
	
	JSONObject retJson;
	
	public String getJsonResult(String retCode, String retMsg){
		JSONResult retJson = new JSONResult();
		return getJsonResult(retCode, retMsg, retJson);
	}
	
	public String getJsonResult(String retCode, JSONResult retJson){
		return getJsonResult(retCode, "", retJson);
	}
	
	public String getJsonResult(String retCode, String retMsg, JSONResult retJson){
		retJson.put(RET_CODE, retCode);
		retJson.put(RET_MSG, retMsg);
		return retJson.toString();
	}
	
	public ModelAndView jsonResult(ModelMap model){
		//model.addAttribute(RET_CODE, RET_SUCC);
		return new ModelAndView("jsonView", model);
	}
	
	
	@ExceptionHandler(Throwable.class)
	public ModelAndView throwableHandler(Throwable e){
		log.error("==============================================throwable" , e);
		//e.printStackTrace();
		ModelMap model = new ModelMap();
		model.addAttribute(RET_MSG, e.getMessage());
		model.addAttribute(RET_CODE, RET_ERROR);
		return new ModelAndView("/error/error", model);
	}
	/**
	 * Controller占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 처占쏙옙
	 * @param e
	 * @param response
	 * @return
	 */
	@ExceptionHandler(Exception.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)		//controller 占쏙옙占쏙옙 占쏙옙占쏙옙 처占쏙옙占쏙옙
	public ModelAndView exceptionHandler(Exception e){
		log.error("==============================================Exception" , e);
		ModelMap model = new ModelMap();
		model.addAttribute(RET_MSG, e.getMessage());
		model.addAttribute(RET_CODE, RET_ERROR);
		// return jsonResult(model);
		return new ModelAndView("/error/error", model);
	}
	
	/**
	 * Controller占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 처占쏙옙
	 * @param e
	 * @param response
	 * @return
	 */
	@ExceptionHandler(BaseAjaxException.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)		//controller 占쏙옙占쏙옙 占쏙옙占쏙옙 처占쏙옙占쏙옙
	public ModelAndView ajaxExceptionHandler(Exception e){
		log.error("==============================================BaseAjaxException" , e);
		ModelMap model = new ModelMap();
		model.addAttribute(RET_MSG, e.getMessage());
		model.addAttribute(RET_CODE, RET_ERROR);
		return jsonResult(model);
	}
	
	@ExceptionHandler(BaseDhtmlxException.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)		//controller 占쏙옙占쏙옙 占쏙옙占쏙옙 처占쏙옙占쏙옙
	public ModelAndView dhtmlxExceptionHandler(Exception e){
		log.error("==============================================dhtmlxBaseException" , e);
		ModelMap model = new ModelMap();
		model.addAttribute(RET_MSG, e.getMessage());
		model.addAttribute(RET_CODE, RET_ERROR);
		
		
		return jsonResult(model);
	}
	
	public static final String JSON_ERROR_STRING = "{\"+RET_CODE+\":\"+RET_ERROR+\",\"+RET_MSG+\":\"\"}";
	

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
				log.error("占쌩몌옙占쏙옙 keyColumn 占쌉력곤옙占쌉니댐옙. - current input key["+key+"]",e);
				throw e;
			}
		}
		//log.debug("value["+rowKey+"]");
		rowKey = rowKey.substring(0,rowKey.length()-1);
		return rowKey;
	}
	
	public void renderListSetXml(HttpServletResponse res,
			List<OutputResult> list) throws BaseDhtmlxException{
		try{
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
	//	log.debug("list size[" + listSize + "]");
		StringBuilder sb = new StringBuilder(); 
		if(list == null || list.size() <= 0){
		//	log.debug("占쏙옙회占쏙옙 占쏙옙占쏙옙트占쏙옙 占쏙옙占쏙옙占쏙옙");
			sb.append("<rows total_count='0' pos='0'></rows>");
		}else{
			int listSize = list.size();
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
					

					if((resultColumns.length() <=0)){		//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
						Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
						while (iter.hasNext()) {
							Entry<String, Object> entry = iter.next();
							sb.append("<cell><![CDATA[").append(Comm.nvl(entry.getValue())).append("]]></cell>");
							//log.debug("xml ============ key[" + entry.getKey() + "] value["+ entry.getValue() + "]");
						}
					}else{									//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
						String[] resultColumn = resultColumns.split("\\|");
						for (String stringKey : resultColumn) {
							try{
								sb.append("<cell><![CDATA[").append(Comm.nvl(record.get(stringKey))).append("]]></cell>");
							//	log.debug("key[" + stringKey + "] value["+ record.get(stringKey) + "]");
							}catch(NullPointerException e){
								log.error("占쌩몌옙占쏙옙 resultColumn 占쌉력곤옙占쌉니댐옙. 占쏙옙占싸듸옙 xml占쏙옙 확占쏙옙占쏙옙占쌍쇽옙占쏙옙- current input key["+stringKey+"]",e);
								throw e;
							}
						}
					}
					sb.append("\n</row>\n");
				}
				sb.append("</rows>");
		}
		//log.debug(sb.toString());
		out.println(sb.toString());
		out.flush();
		out.close();
		}catch(Exception e){
			throw new BaseDhtmlxException(e);
		}
	}
	
	
	public void renderListSetDEV(HttpServletResponse res,
			List<OutputResult> list) throws BaseDhtmlxException{
		try{
		String startgb = "";
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
	//	log.debug("list size[" + listSize + "]");
		StringBuilder sb = new StringBuilder(); 
		if(list == null || list.size() <= 0){
		//	log.debug("占쏙옙회占쏙옙 占쏙옙占쏙옙트占쏙옙 占쏙옙占쏙옙占쏙옙");
			sb.append("{").append("\"totalCount").append("\"").append(":0,").append("\"").append("items").append("\"").append(":[]}");
		}else{
			int listSize = list.size();
			//sb.append("{").append("\"totalCount").append("\":" + 1 + ",");
			
			//	log.debug("resultColumns["+resultColumns+"]keyColumns["+keyColumns+"]seqColName["+seqColName+"]");
			//sb.append("\"items\":[");
			sb.append("[");
			
			 	for (int i = 0; i < listSize; i++) {
					IOData record = list.get(i);
					
					if (i == 0){
						sb.append("{");
					} else {
						sb.append(",{");
					}
					
					
					if((resultColumns.length() <=0)){		//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
						Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
						startgb = "Y"; 
								
						while (iter.hasNext()) {
							if (startgb != "Y") {								
								sb.append(",");
							}
							Entry<String, Object> entry = iter.next();
							sb.append("\"").append(entry.getKey()).append("\":").append("\"").append(Comm.nvl(entry.getValue())).append("\"");
							
							startgb = ""; 
						}
					}else{									//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
						String[] resultColumn = resultColumns.split("\\|");
						for (String stringKey : resultColumn) {
							try{
								sb.append("<cell><![CDATA[").append(Comm.nvl(record.get(stringKey))).append("]]></cell>");
							//	log.debug("key[" + stringKey + "] value["+ record.get(stringKey) + "]");
							}catch(NullPointerException e){
								log.error("占쌩몌옙占쏙옙 resultColumn 占쌉력곤옙占쌉니댐옙. 占쏙옙占싸듸옙 xml占쏙옙 확占쏙옙占쏙옙占쌍쇽옙占쏙옙- current input key["+stringKey+"]",e);
								throw e;
							}
						}
						
						
					}
					sb.append("}");
					
				}
			 	sb.append("]");
			//sb.append("}");
		}
		log.debug(sb.toString());
		out.println(sb.toString());
		out.flush();
		out.close();
		}catch(Exception e){
			throw new BaseDhtmlxException(e);
		}
	}
	
	
	public void renderListSetDEVLookup(HttpServletResponse res,
			List<OutputResult> list) throws BaseDhtmlxException{
		try{
		String startgb = "";
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/xml");
		PrintWriter out = res.getWriter();
	//	log.debug("list size[" + listSize + "]");
		StringBuilder sb = new StringBuilder(); 
		if(list == null || list.size() <= 0){
		//	log.debug("占쏙옙회占쏙옙 占쏙옙占쏙옙트占쏙옙 占쏙옙占쏙옙占쏙옙");
			sb.append("{").append("\"totalCount").append("\"").append(":0,").append("\"").append("items").append("\"").append(":[]}");
		}else{
			int listSize = list.size();
			//sb.append("{").append("\"totalCount").append("\":" + 1 + ",");
			
			//	log.debug("resultColumns["+resultColumns+"]keyColumns["+keyColumns+"]seqColName["+seqColName+"]");
			//sb.append("\"items\":[");
			sb.append("[");
			
			 	for (int i = 0; i < listSize; i++) {
					IOData record = list.get(i);
					
					if (i == 0){
						sb.append("{");
					} else {
						sb.append(",{");
					}
					
					
					if((resultColumns.length() <=0)){		//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
						Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
						startgb = "Y"; 
								
						while (iter.hasNext()) {
							if (startgb != "Y") {								
								sb.append(",");
								Entry<String, Object> entry = iter.next();
								sb.append("\"").append(entry.getKey()).append("\":").append("\"").append(Comm.nvl(entry.getValue())).append("\"");
								
							} else {
								Entry<String, Object> entry = iter.next();
								sb.append("\"").append(entry.getKey()).append("\":").append("\"").append(Comm.nvl(entry.getValue())).append("\"");
								
								//sb.append("\"").append(entry.getKey()).append("\":").append(Comm.nvl(entry.getValue())).append("");
							}
							
							startgb = ""; 
						}
					}else{									//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
						String[] resultColumn = resultColumns.split("\\|");
						for (String stringKey : resultColumn) {
							try{
								sb.append("<cell><![CDATA[").append(Comm.nvl(record.get(stringKey))).append("]]></cell>");
							//	log.debug("key[" + stringKey + "] value["+ record.get(stringKey) + "]");
							}catch(NullPointerException e){
								log.error("占쌩몌옙占쏙옙 resultColumn 占쌉력곤옙占쌉니댐옙. 占쏙옙占싸듸옙 xml占쏙옙 확占쏙옙占쏙옙占쌍쇽옙占쏙옙- current input key["+stringKey+"]",e);
								throw e;
							}
						}
						
						
					}
					sb.append("}");
					
				}
			 	sb.append("]");
			//sb.append("}");
		}
		log.debug(sb.toString());
		out.println(sb.toString());
		out.flush();
		out.close();
		}catch(Exception e){
			throw new BaseDhtmlxException(e);
		}
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
			
			if((resultColumns.length() <=0)){		//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
				Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					rowData.put(entry.getValue());
				}
			}else{									//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
				String[] resultColumn = resultColumns.split("\\|");
				for (String stringKey : resultColumn) {
					rowData.put(record.get(stringKey));
				}
			}
			jsonRowObj.put("data", rowData);
		}
		jsonObj.put("rows", jsonRow);
		//log.debug(jsonObj.toString());
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
			
			if((resultColumns.length() <=0)){		//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
				Iterator<Entry<String, Object>> iter = record.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					//log.debug("touchlist ============ key[" + entry.getKey() + "] value["+ entry.getValue() + "]");
					jsonRowObj.put(entry.getKey(), entry.getValue());
				}
			}else{									//占쏙옙占쏙옙占쏙옙 占시뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙
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
	
	
//	public Object nvl(Object o){
//		return (o == null)?"":o;
//	}
}
