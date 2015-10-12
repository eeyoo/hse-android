package com.huazi.project.hse.db;

import java.util.ArrayList;
import java.util.List;

import com.huazi.project.hse.entity.Area;
import com.huazi.project.hse.entity.DictEntry;
import com.huazi.project.hse.entity.DictType;
import com.huazi.project.hse.entity.InterfaceData;
import com.huazi.project.hse.entity.Risk;
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
	 * 存储类型字典数据
	 */
	public void saveDictType(DictType data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put("alias", data.getAlias());
			values.put("name", data.getName());
			db.insert("DictType", null, values);
		}
	}
	
	/**
	 * 读取类型字典数据
	 */
	public List<DictType> loadDictType() {
		List<DictType> list = new ArrayList<DictType>();
		Cursor cursor = db.query("DictType", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				DictType dictType = new DictType();
				dictType.setAlias(cursor.getString(cursor.getColumnIndex("alias")));
				dictType.setName(cursor.getString(cursor.getColumnIndex("name")));
				list.add(dictType);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 存储全局字典数据
	 */
	public void saveDictEntry(DictEntry data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put("name", data.getName());
			values.put("type", data.getType());
			db.insert("DictEntry", null, values);
		}
	}
	
	/**
	 * 读取全局字典数据并转为对象
	 */
	public List<DictEntry> loadDictEntry() {
		List<DictEntry> list = new ArrayList<DictEntry>();
		Cursor cursor = db.query("DictEntry", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				DictEntry dictEntry = new DictEntry();
				dictEntry.setName(cursor.getString(cursor.getColumnIndex("name")));
				dictEntry.setType(cursor.getString(cursor.getColumnIndex("type")));
				list.add(dictEntry);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 读取全局字典数据并转为对象
	 */
	public List<DictEntry> loadDictEntryByType(String type) {
		List<DictEntry> list = new ArrayList<DictEntry>();
		Cursor cursor = db.query("DictEntry", null, "type = ?", new String[] {type}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				DictEntry dictEntry = new DictEntry();
				dictEntry.setName(cursor.getString(cursor.getColumnIndex("name")));
				dictEntry.setType(cursor.getString(cursor.getColumnIndex("type")));
				list.add(dictEntry);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 存储隐患数据
	 */
	public void saveRisk(Risk risk) {
		if (risk != null) {
			ContentValues values = new ContentValues();
			values.put("risk_type_id", risk.getRiskTypeId());
			values.put("prof_type_id", risk.getProfTypeId());
			values.put("area_name_id", risk.getDepartmentId());
			values.put("rank", risk.getRank());
			values.put("content", risk.getContent());
			values.put("creater", risk.getCreater());
			values.put("create_date", risk.getCreateDate());
			values.put("file_name", risk.getFileName());
			db.insert("Risk", null, values);
		}
	}
	
	/**
	 * 读取隐患数据
	 */
	public List<Risk> loadRisk() {
		List<Risk> list = new ArrayList<Risk>();
		Cursor cursor = db.query("Risk", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Risk risk = new Risk();
				risk.setRiskTypeId(cursor.getInt(cursor.getColumnIndex("risk_type_id")));
				risk.setProfTypeId(cursor.getInt(cursor.getColumnIndex("prof_type_d")));
				risk.setDepartmentId(cursor.getInt(cursor.getColumnIndex("department_id")));
				risk.setRank(cursor.getInt(cursor.getColumnIndex("rank")));
				risk.setContent(cursor.getString(cursor.getColumnIndex("content")));
				risk.setCreater(cursor.getString(cursor.getColumnIndex("creater")));
				risk.setCreateDate(cursor.getString(cursor.getColumnIndex("create_date")));
				risk.setFileName(cursor.getString(cursor.getColumnIndex("file_name")));
				list.add(risk);
			} while (cursor.moveToNext());			
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 存储部门数据
	 */
	public void saveArea(Area data) {
		if (data != null) {
			ContentValues values = new ContentValues();
			values.put("alias", data.getAlias());
			values.put("name", data.getName());
			db.insert("Area", null, values);
		}
	}
	
	/**
	 * 读取部门数据
	 */
	public List<Area> loadArea() {
		List<Area> list = new ArrayList<Area>();
		Cursor cursor = db.query("Area", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Area area = new Area();
				area.setAlias(cursor.getString(cursor.getColumnIndex("alias")));
				area.setName(cursor.getString(cursor.getColumnIndex("name")));
				list.add(area);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
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
