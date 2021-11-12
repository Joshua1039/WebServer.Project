package com.modernwave.web.server.framework.core;


import java.util.HashMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseDao_DARS extends SqlMapClientDaoSupport {

	@Resource(name = "dataSource_DARS")
	public void setBaseSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	@Autowired
	@Resource(name="sqlMapClient_DARS")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient)  {
		super.setSqlMapClient(sqlMapClient);
	}

	public Object getList(String statementID) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.queryForList(statementID);
	}

	public Object getList(String statementID, Object param) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.queryForList(statementID, param);
	}

	@SuppressWarnings("unchecked")
	public HashMap getObjectQuery(String statementID) {
		return (HashMap) getObject(statementID);
	}

	@SuppressWarnings("unchecked")
	public HashMap getObjectQuery(String statementID, Object param) {
		return (HashMap) getObject(statementID, param);
	}

	public Object getObject(String statementID, Object param) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.queryForObject(statementID, param);
	}

	public Object getObject(String statementID) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.queryForObject(statementID);
	}

	public Object insertQuery(String statementID, Object param) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.insert(statementID, param);
	}
	
	public int updateQuery(String statementID) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.update(statementID);
	}

	public int updateQuery(String statementID, Object param) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.update(statementID, param);
	}
	
	public int deleteQuery(String statementID) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.delete(statementID);
	}

	public int deleteQuery(String statementID, Object param) {
		SqlMapClientTemplate sqlMapClient = getSqlMapClientTemplate();
		return sqlMapClient.delete(statementID, param);
	}
}
