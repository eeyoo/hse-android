/**
 * 
 */
package com.huazi.project.hse.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author wfl
 * 上传图片
 */
public class UploadFile {

	private String fileName;
	private String fileType;
	private String content;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toJson() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("fileName", getFileName());
			obj.put("fileType", getFileType());
			obj.put("content", getContent());
			
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}
