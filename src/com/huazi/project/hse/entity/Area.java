/**
 * 
 */
package com.huazi.project.hse.entity;

/**
 * @author wfl
 * 部门
 */
public class Area {

	private int id;
	private String alias;
	private String name;
	
	public Area() {}
	public Area(String alias, String name) {
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
