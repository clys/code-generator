package com.clys.codeGenerator.entity;

import com.bj58.supin.plugins.codegenerator.core.util.SqlTypeUtil;
import com.bj58.supin.plugins.codegenerator.core.util.StringUtil;

import java.util.Map;

/**
 * 表字段信息
 */
public class ColumnDefinition {

	private String columnName; // 数据库字段名
	private String type; // 数据库类型
	private boolean isIdentity; // 是否自增
	private boolean isPk; // 是否主键
	private Map<String, Object> original;//原始数据

	/**
	 * 是否是自增主键
	 * 
	 * @return
	 */
	public boolean getIsIdentityPk() {
		return isPk && isIdentity;
	}
	
	/**
	 * 返回java字段名,并且第一个字母大写
	 * 
	 * @return
	 */
	public String getJavaFieldNameUF() {
		return StringUtil.firstToUpper(getJavaFieldName());
	}
	
	public String getJavaFieldName() {
		return StringUtil.underlineToCamelhump(columnName);
	}
	
	/**
	 * 获得基本类型,int,float
	 * @return
	 */
	
	public String getJavaType() {
		String typeLower = type.toLowerCase();
		return SqlTypeUtil.convertToJavaType(typeLower);
	}
	
	/**
	 * 获得装箱类型,Integer,Float
	 * @return
	 */

	public String getJavaTypeBox(){
		String typeLower = type.toLowerCase();
		return SqlTypeUtil.convertToJavaBoxType(typeLower);
	}
	
	public String getMybatisJdbcType() {
		String typeLower = type.toLowerCase();
		return SqlTypeUtil.convertToMyBatisJdbcType(typeLower);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getIsIdentity() {
		return isIdentity;
	}

	public void setIsIdentity(boolean isIdentity) {
		this.isIdentity = isIdentity;
	}

	public boolean getIsPk() {
		return isPk;
	}

	public void setIsPk(boolean isPk) {
		this.isPk = isPk;
	}

	public Map<String, Object> getOriginal() {
		return original;
	}

	public void setOriginal(Map<String, Object> original) {
		this.original = original;
	}
}
