package com.huazi.project.hse.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.InterfaceData;
import com.huazi.project.hse.entity.Template;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utility {

	private static HseDB db;
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
	
	public synchronized static String handleJsonResponse(String response) {
		String result = null;
		try {
			JSONObject data = new JSONObject(response);
			JSONObject helloResp = data.getJSONObject("helloResponse");
			result = helloResp.getString("return");		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public synchronized static boolean handleJsonArrayResponse(HseDB db, String response, String year) {
		/*
		 * [ {"mainInfo" : [{code } {explication } {value }]} {} {} ...]
		 */
		try {
			JSONArray array = new JSONArray(response);
			
			for(int i=0;i<array.length();i++){
				org.json.JSONObject obj = (org.json.JSONObject)array.get(i);
				
				JSONArray arr = obj.getJSONArray("mainInfo");
				
				for(int j=0;j<arr.length();j++){
					org.json.JSONObject obj1 = (org.json.JSONObject)arr.get(j);
					
					String code = obj1.getString("code");
					String exp = obj1.getString("explication");
					String value = obj1.getString("value");
					
					InterfaceData data = new InterfaceData(code, exp, value, year);
					db.saveInterface(data);
				}
	    	}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private static void saveShareInfo(Context context, int id, String content) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit(); //打开编辑文件开始写入数据
		editor.putInt("id", id);
		editor.putString("content", content);
		editor.commit();
	}
}
