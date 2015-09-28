package com.huazi.project.hse.entity;

/**
 * 测试祝Temp项目数据实体
 *
 */
public class InterfaceData {
	
	private int id;
	private String code;					//系统信息编号
	private String value;					//特征值
	private String explication; 			//中文解析
	private String year;
	
	public InterfaceData() {
		
	}
	
	public InterfaceData(String code, String explication, String value, String year) {
		this.code = code;
		this.explication = explication;
		this.value = value;
		this.year = year;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getExplication() {
		return explication;
	}
	public void setExplication(String explication) {
		this.explication = explication;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
