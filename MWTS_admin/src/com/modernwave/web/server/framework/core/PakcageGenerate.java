package com.modernwave.web.server.framework.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PakcageGenerate {
	
	
	static final String rootPackage = "com.modernwave.web.server";
	static final String rootPath = "C:\\project\\daewook\\qc\\workspace\\kdbsecqc\\src\\com\\kdbqc\\web\\server\\";
	
	static public void main(String[] a) throws IOException{
		
//		generateController("Test", "test");
//		generateService("Login");
//		generateServiceImpl("Test");
		//generateDaoImpl("Code");
		//generateDao("Code");
		//generateSqlsMap("Code");
		generateAll("AgentCall");
	}
	
	static public void generateAll(String name) throws IOException{
		generateSqlMap(name);
		generateDao(name);
		generateDaoImpl(name);
		generateService(name);
		generateServiceImpl(name);
		generateController(name);
	}
	
	static public void generateController(String name) throws IOException{
		String className = name+"Controller";
		File controllerFile = new File(rootPath+"controller\\"+className+".java");
//		FileOutputStream controller = new FileOutputStream(controllerFile);
		System.out.println("controller 생성 파일 경로 ["+controllerFile.getPath());
		
		if(controllerFile.exists()){
			System.out.println("이미 존재하는 파일 입니다.");
			return;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(controllerFile));
		String actionPath = name.substring(0,1).toLowerCase();
		actionPath += name.substring(1);
		String serviceName = name+"Service";
		String serviceVarName = serviceName.substring(0,1).toLowerCase();
		serviceVarName += serviceName.substring(1);
		
		StringBuilder sb = new StringBuilder();
		sb.append("package "+rootPackage+".controller;\n");

		sb.append("import org.apache.log4j.Logger;\n");		//default package
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import com.modernwave.web.server.framework.core.BaseController;\n");		//default package
		sb.append("\n");
		sb.append("import org.springframework.stereotype.Controller;\n");		//default package
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");		//default package
		sb.append("\n");
		sb.append("import "+rootPackage+".service."+serviceName+";\n");
		sb.append("\n");
		sb.append("@Controller\n");	
		sb.append("@RequestMapping(\"/"+actionPath+"\")\n");		
		sb.append("public class "+className+" extends BaseController{\n");
		sb.append("\n");
		sb.append("\tprivate Logger log = Logger.getLogger(this.getClass());\n");
		sb.append("\n");
		sb.append("\t@Autowired\n");
		sb.append("\t"+serviceName+" "+serviceVarName+";\n");
		sb.append("\n");
		sb.append("}");
		out.write(sb.toString());
		out.close();
	}
	
	static public void generateService(String name) throws IOException{
		String className = name+"Service";
		File serviceFile = new File(rootPath+"service\\"+className+".java");
//		FileOutputStream controller = new FileOutputStream(controllerFile);
		System.out.println("service 생성 파일 경로 ["+serviceFile.getPath());
		
		if(serviceFile.exists()){
			System.out.println("이미 존재하는 파일 입니다.");
			return;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(serviceFile));
		
		StringBuilder sb = new StringBuilder();
		sb.append("package "+rootPackage+".service;\n");
		sb.append("\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;\n");		//default package
		sb.append("\n");
		sb.append("@Transactional\n");		//default package
		sb.append("\n");
		sb.append("public interface "+className+"{\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("}");
		out.write(sb.toString());
		out.close();
	}
	
	static public void generateServiceImpl(String name) throws IOException{
		String className = name+"ServiceImpl";
		File serviceImplFile = new File(rootPath+"service\\impl\\"+className+".java");
//		FileOutputStream controller = new FileOutputStream(controllerFile);
		System.out.println("service impl 생성 파일 경로 ["+serviceImplFile.getPath());
		
		if(serviceImplFile.exists()){
			System.out.println("이미 존재하는 파일 입니다.");
			return;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(serviceImplFile));
		
		String daoName = name+"Dao";
		String daoVarName = daoName.substring(0,1).toLowerCase();
		daoVarName += daoName.substring(1);
		
		StringBuilder sb = new StringBuilder();
		sb.append("package "+rootPackage+".service.impl;\n");
		sb.append("\n");
		sb.append("import org.apache.log4j.Logger;\n");		
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");		
		sb.append("import org.springframework.stereotype.Service;\n");		
		sb.append("\n");
		sb.append("import com.modernwave.web.server.framework.core.BaseService;\n");		
		sb.append("import "+rootPackage+".service."+name+"Service;\n");
		sb.append("import "+rootPackage+".dao."+daoName+";\n");
		sb.append("\n");
		sb.append("@Service\n");		//default package
		sb.append("public class "+className+" extends BaseService implements "+name+"Service{\n");
		sb.append("\n");
		sb.append("\tLogger log = Logger.getLogger(this.getClass());\n");
		sb.append("\n");
		sb.append("\t@Autowired\n");
		sb.append("\t"+daoName+" "+daoVarName+";\n");
		sb.append("\n");
		sb.append("}");
		out.write(sb.toString());
		out.close();
	}
	
	static public void generateDao(String name) throws IOException{
		String className = name+"Dao";
		File daoFile = new File(rootPath+"dao\\"+className+".java");
//		FileOutputStream controller = new FileOutputStream(controllerFile);
		System.out.println("dao 생성 파일 경로 ["+daoFile.getPath());
		
		if(daoFile.exists()){
			System.out.println("이미 존재하는 파일 입니다.");
			return;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(daoFile));
		
		StringBuilder sb = new StringBuilder();
		sb.append("package "+rootPackage+".dao;\n");
		sb.append("\n");
		sb.append("public interface "+className+"{\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("}");
		out.write(sb.toString());
		out.close();
	}
	
	static public void generateDaoImpl(String name) throws IOException{
		String className = name+"DaoImpl";
		File daoImplFile = new File(rootPath+"Dao\\impl\\"+className+".java");
//		FileOutputStream controller = new FileOutputStream(controllerFile);
		System.out.println("dao impl 생성 파일 경로 ["+daoImplFile.getPath());
		
		if(daoImplFile.exists()){
			System.out.println("이미 존재하는 파일 입니다.");
			return;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(daoImplFile));
		
		StringBuilder sb = new StringBuilder();
		sb.append("package "+rootPackage+".dao.impl;\n");
		sb.append("\n");
		sb.append("import org.apache.log4j.Logger;\n");		
		sb.append("import org.springframework.stereotype.Repository;\n");		
		sb.append("\n");
		sb.append("import com.modernwave.web.server.framework.core.BaseDao;\n");		
		sb.append("import "+rootPackage+".dao."+name+"Dao;\n");
		sb.append("\n");
		sb.append("@Repository\n");		//default package
		sb.append("public class "+className+" extends BaseDao implements "+name+"Dao{\n");
		sb.append("\n");
		sb.append("\tLogger log = Logger.getLogger(this.getClass());\n");
		sb.append("\tprivate String namespace = \""+name+".\"; \n");
		sb.append("\n");
		sb.append("}");
		out.write(sb.toString());
		out.close();
	}
	
	
	static public void generateSqlMap(String name) throws IOException{
		String xmlName = name+"SqlMap";
		File xmlFile = new File(rootPath+"Dao\\impl\\maps\\"+xmlName+".xml");
//		FileOutputStream controller = new FileOutputStream(controllerFile);
		System.out.println("xml 생성 파일 경로 ["+xmlFile.getPath());
		
		if(xmlFile.exists()){
			System.out.println("이미 존재하는 파일 입니다.");
			return;
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(xmlFile));
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		sb.append("<!DOCTYPE sqlMap  PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\"  \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n");		
		sb.append("<sqlMap namespace=\""+name+"\">\n");		
		sb.append("\n");
		sb.append("\n");
		sb.append("</sqlMap>");
		out.write(sb.toString());
		out.close();
	}
	
	
}
