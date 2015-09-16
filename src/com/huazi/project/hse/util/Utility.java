package com.huazi.project.hse.util;

import org.json.JSONObject;

import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.Template;

public class Utility {

	/**
	 * 处理服务端返回的JSON数据
	 * @param hseDB
	 * @param response
	 */
	public synchronized static void handleTemplateResponse(HseDB hseDB, String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			int id = jsonObject.getInt("id");
			String name = jsonObject.getString("name");
			String code = jsonObject.getString("code");
			
			Template template = new Template();
			template.setId(id);
			template.setName(name);
			template.setCode(code);
			hseDB.saveTemplate(template);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
