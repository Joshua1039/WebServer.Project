package com.modernwave.web.server.framework.dataset.grid;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.modernwave.web.server.framework.core.BaseController;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.framework.dataset.grid.service.DhtmlxGridService;


@Controller
@RequestMapping("/dhtmlxgrid")
public class DhtmlxGridController extends BaseController{
	
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	DhtmlxGridService dhtmlxGridService;
	
	@RequestMapping("/selectQuery.do")
	public void selectQuery(HttpServletRequest request, String statementId, HttpServletResponse res) throws Exception{
		InputParam param = new InputParam();
		param.setParameter(request);
		List<OutputResult> list = dhtmlxGridService.selectQuery(statementId, param);
		renderListSetXml(res, list);
	}
	
	@RequestMapping("/insertQuery.do")
	public void insertQuery(Map<String,Object> param, String statementId, HttpServletResponse res) throws Exception{
		Object o = dhtmlxGridService.insertQuery(statementId, param);
		renderSimpleXml(res, "retCount", 0);
		log.debug("insert ok ["+o+"]");
	}

	@RequestMapping("/updateQuery.do")
	public void updateQuery(Map<String,Object> param, String statementId, HttpServletResponse res) throws Exception{
		int retCount = dhtmlxGridService.updateQuery(statementId, param);
		renderSimpleXml(res, "retCount", retCount);
		log.debug("update ok ["+retCount+"]");
	}

	@RequestMapping("/deleteQuery.do")
	public void deleteQuery(Map<String,Object> param, String statementId, HttpServletResponse res) throws Exception{
		int retCount = dhtmlxGridService.deleteQuery(statementId, param);
		renderSimpleXml(res, "retCount", retCount);
		log.debug("delete ok ["+retCount+"]");
	}

}
