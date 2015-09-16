package com.huazi.project.hse.db;


import java.util.ArrayList;
import java.util.List;

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
