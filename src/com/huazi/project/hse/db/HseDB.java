package com.huazi.project.hse.db;


import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

import com.huazi.project.hse.entity.InterfaceData;
import com.huazi.project.hse.entity.Template;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HseDB {

	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "hse_db";
	
	/**
	 * 数据库版本
	 */
	public static final int DB_VERSION = 1;
	
	private static HseDB hseDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 私有化构造方法
	 * @param context
	 */
	private HseDB(Context context) {
		HseOpenHelper dbHelper = new HseOpenHelper(context,DB_NAME, null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取HseDB的实例
	 * @param context
	 * @return
	 */
	public synchronized static HseDB getInstance(Context context) {
		if (hseDB == null) {
			hseDB = new HseDB(context);
		}
		return hseDB;
	}
	
	/**
	 * 存储Interface表数据
	 */
	public void saveInterface(InterfaceData data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put("code", data.getCode());
			values.put("value", data.getValue());
			values.put("explication", data.getExplication());
			values.put("year", data.getYear());
			db.insert("Interface", null, values);
		}
	}
	
	/**
	 * 读取Interface数据
	 */
	public List<InterfaceData> loadInterface(String year) {
		List<InterfaceData> list = new ArrayList<InterfaceData>();
		Cursor cursor = db.query("Interface", null, "year = ?", 
				new String[] {year}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				InterfaceData data = new InterfaceData();
				data.setId(cursor.getInt(cursor.getColumnIndex("id")));
				data.setCode(cursor.getString(cursor.getColumnIndex("code")));
				data.setValue(cursor.getString(cursor.getColumnIndex("value")));
				data.setExplication(cursor.getString(cursor.getColumnIndex("explication")));
				data.setYear(cursor.getString(cursor.getColumnIndex("year")));
				list.add(data);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	
	public void saveTemplate(Template template) {
		if (template != null) {
			ContentValues values = new ContentValues();
			values.put("template_name", template.getName());
			values.put("template_code", template.getCode());
			db.insert("Template", null, values);
		}
	}
	
	public List<Template> loadTemplates() {
		List<Template> list = new ArrayList<Template>();
		Cursor cursor = db.query("Template", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Template template = new Template();
				template.setId(cursor.getInt(cursor.getColumnIndex("id")));
				template.setName(cursor.getString(cursor.getColumnIndex("template_name")));
				template.setCode(cursor.getString(cursor.getColumnIndex("template_code")));
				list.add(template);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
}
