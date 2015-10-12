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
	private int riskTypeId;
	private int profTypeId;
	private int departmentId;
	private int rank;
	private String content;
	private String creater;
	private String createDate;
	private String fileName;

	public Risk() {
	}

	public Risk(int riskTypeId, int profTypeId, int departmentId, int rank, String content, String creater,
			String createDate, String fileName) {
		this.riskTypeId = riskTypeId;
		this.profTypeId = profTypeId;
		this.departmentId = departmentId;
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

	public int getRiskTypeId() {
		return riskTypeId;
	}

	public void setRiskTypeId(int riskTypeId) {
		this.riskTypeId = riskTypeId;
	}

	public int getProfTypeId() {
		return profTypeId;
	}

	public void setProfTypeId(int profTypeId) {
		this.profTypeId = profTypeId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
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
