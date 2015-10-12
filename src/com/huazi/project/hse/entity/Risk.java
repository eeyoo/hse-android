/**
 * 
 */
package com.huazi.project.hse.entity;

/**
 * @author wfl
 *
 */
public class Risk {

	private int id;
	private String riskType;
	private String profType;
	private String department;
	private int rank;
	private String content;
	private String creater;
	private String createDate;
	private String fileName;
	
	public Risk() {}
	
	public Risk(String riskType, String profType, String department, int rank, String content, 
			String creater, String createDate, String fileName) {
		this.riskType = riskType;
		this.profType = profType;
		this.department = department;
		this.rank = rank;
		this.content = content;
		this.creater = creater;
		this.createDate = createDate;
		this.fileName = fileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getProfType() {
		return profType;
	}

	public void setProfType(String profType) {
		this.profType = profType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
