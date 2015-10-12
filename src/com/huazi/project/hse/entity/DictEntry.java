/**
 * 
 */
package com.huazi.project.hse.entity;

/**
 * @author wfl
 * 全局字典 - 关联类型字典
 */
public class DictEntry {

	private int id;
	private String name;  //名称
	private String type;  //名称对应类型
	public DictEntry() {}
	public DictEntry(String name, String type) {
		this.name = name;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
