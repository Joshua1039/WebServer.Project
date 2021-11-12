package com.modernwave.web.server.controller;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


public class EncryptFactory implements ObjectFactory {
	
	private static final String PROP_URL = "url";
	private static final String PROP_USERNAME = "username";
	private static final String PROP_PASSWORD = "password";
	private static final String PROP_DRIVERCLASSNAME = "driverClassName";
	
	private static final String[] ALL_PROPERTIES = {"url", "username", "password", "driverClassName"};
	
	
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
		
		if ((obj == null) || (!(obj instanceof Reference))) {
			return null;
		}
		Reference ref = (Reference)obj;
		if (!"javax.sql.DataSource".equals(ref.getClassName())) {
			return null;
		}
		Properties properties = new Properties();
			for (int i = 0; i < ALL_PROPERTIES.length; i++) {
			String propertyName = ALL_PROPERTIES[i];
			RefAddr ra = ref.get(propertyName);
			if (ra != null) {
				String propertyValue = ra.getContent().toString();
				properties.setProperty(propertyName, propertyValue);
			}
		}
			return createDataSource(properties);
	}
	
	public static DataSource createDataSource(Properties properties) throws Exception {
		BasicDataSource dataSource = new BasicDataSource();
		String value = null;

		value = properties.getProperty("url");
		if (value != null) {
		dataSource.setUrl(decryptDBCPProperty(value));
		}
		value = properties.getProperty("username");
		if (value != null) {
		dataSource.setUsername(decryptDBCPProperty(value));
		}
		value = properties.getProperty("password");
		if (value != null) {
		dataSource.setPassword(decryptDBCPProperty(value));
		}
	    value = properties.getProperty("driverClassName");
	    if (value != null) {
	      dataSource.setDriverClassName(value);
	    }
	    
		if (dataSource.getInitialSize() > 0) {
		dataSource.getLogWriter();
		}
		return dataSource;
	}
  
	private static Properties getProperties(String propText) throws Exception {
		Properties p = new Properties();
		if (propText != null) {
			p.load(new ByteArrayInputStream(propText.replace(';', '\n').getBytes()));
		}
		return p;
	}
	
	private static String decryptDBCPProperty(String encryptStr) throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException {
		
		//Encrypt KEY 16
		AES256Util aes = new AES256Util("modernwavemodern");
		
		//Local
		//System.out.println("url : " + aes.encrypt("jdbc:mysql://127.0.0.1:3306/adminweb_db"));
		//Rd20dA7Rc9nNZQYB4CqdNEywcRyiOMAXH7pLVR4WHliu4v47YrJ/mdlRKM++vtAP
		
		//System.out.println("url : " + aes.encrypt("jdbc:mysql://127.0.0.1:3306/mctm"));
		//Rd20dA7Rc9nNZQYB4CqdNGOe0lgCBYLIQxV4A0zHVgwsl2NRIdMZB1YZYIbpBn85
		
		//System.out.println("url : " + aes.encrypt("jdbc:mysql://127.0.0.1:3306/acr_v4"));
		//Rd20dA7Rc9nNZQYB4CqdNPlTQuVuJrw0tiwim8tIJD1NbxTOMFg5VLpl1cvCeHyT
		
		//System.out.println("url : " + aes.encrypt("jdbc:mysql://127.0.0.1:3306/dars_db"));
		//Rd20dA7Rc9nNZQYB4CqdNAhtOWBtThCs+x+JCgChcuTzrZ5PDH840gTS8E2nxqQu
		
		//System.out.println("username : " + aes.encrypt("root"));  
		//8HM0Y2pSG9PsP1BKBae2Nw==
		
		//System.out.println("password : " + aes.encrypt("modern3600"));
		//O4/uvbDwPrKDzROF+uNMkg==
		
		//Decrypt
		//System.out.println("encryptStr : " + aes.decrypt(encryptStr));
		
		return aes.decrypt(encryptStr);
	
	}
}