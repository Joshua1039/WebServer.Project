package com.modernwave.web.server.framework.dataset;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.modernwave.web.server.framework.core.BaseController;

public class JSONResult extends JSONObject{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public JSONObject put(String arg0, Object arg1){
		try {
			if(arg1 == null)
				arg1 = "";
			return super.put(arg0, arg1);	
		} catch (JSONException e) {
			log.error("json result put error", e);
			return new JSONObject();			
		}

	}
	
	@Override
	public String getString(
			String arg0) {
		try {
			return super.getString(arg0);
		} catch (JSONException e) {
			log.error("json result getString error", e);
			return BaseController.JSON_ERROR_STRING;
		}
	}
}
