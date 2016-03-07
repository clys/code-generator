package com.clys.codeGenerator.utils;

import com.clys.codeGenerator.entity.ColumnDefinition;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

/**
 * Velocity工具类,根据模板内容生成文件
 */
public class VelocityUtil {

	public static void init(String sourcePath){
		//配置velocity的资源加载路径
		Properties velocityPros = new Properties();
		velocityPros.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, sourcePath);
		Velocity.init(velocityPros);
	}

	public static VelocityContext buildVContext(String pack,String table,String entity,List<ColumnDefinition> columns){
		//封装velocity上下文
		VelocityContext context = new VelocityContext();
		context.put("package", pack);
		context.put("table", table);
		context.put("entity", entity);
		context.put("columns", columns);
		return context;
	}

	public static String render(String vm, VelocityContext context) {
		String content = "";

		Template template = null;
		try {
			template = Velocity.getTemplate(vm);
			StringWriter writer = new StringWriter();
			if (template != null)
				template.merge(context, writer);
			writer.flush();
			writer.close();
			content = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

}
