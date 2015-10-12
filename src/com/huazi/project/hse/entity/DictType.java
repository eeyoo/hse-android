/**
 * 
 */
package com.huazi.project.hse.entity;

/**
 * 全局字典 - 类型定义
 * @author wfl
 *
 */
public class DictType {

	private int id;
	private String alias; //类型代号
	private String name;  //类型名称
	
	public DictType() {}
	public DictType(String alias, String name) {
		this.alias = alias;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
