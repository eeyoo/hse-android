/**
 * 
 */
package com.huazi.project.hse.entity;

import android.R.integer;

/**
 * @author wfl
 * 全局字典 - 关联类型字典
 */
public class DictEntry {

	private int id;
	private String name;  //名称
	private String type;  //名称对应类型
	private String typeName; //类型名称
	private int serverId; //数据库对应ID
	
	public DictEntry() {}
	public DictEntry(String name, String type, String typeName, int serverId) {
		this.name = name;
		this.type = type;
		this.typeName = typeName;
		this.serverId = serverId;
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
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
