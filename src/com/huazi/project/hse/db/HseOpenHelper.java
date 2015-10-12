package com.huazi.project.hse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HseOpenHelper extends SQLiteOpenHelper{

	public HseOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**
	 * Template表创建SQL语句
	 */
	public static final String CREATE_TEMPLATE = "create table Template ("
				+ " id integer primary key autoincrement, "
				+ " name text, "
				+ " code text)";
	
	/**
	 * InterfaceData表创建SQL语句
	 */
	public static final String CREATE_INTERFACE = "create table Interface ("
				+ " id integer primary key autoincrement, "
				+ " code text, "
				+ " value text, "
				+ " explication text, "
				+ " year text)";
	
	/**
	 * Risk 隐患表
	 */
	public static final String CREATE_YH_RISK = "create table Risk ("
			+ " id integer primary key autoincrement, "
			+ " risk_type_id integer, "
			+ " prof_type_id integer, "
			+ " department_id integer, "
			+ " rank integer, "
			+ " content text, "
			+ " creater text, "
			+ " create_date text, "
			+ " file_name text)";
	
	/**
	 * Area 部门表
	 */
	public static final String CREATE_YH_AREA = "create table Area ("
			+ " id integer primary key autoincrement, "
			+ " alias text, "
			+ " name text) ";
	
	/**
	 * 全局字典
	 */
	public static final String CREATE_GF_DICTTYPE = "create table DictType ("
			+ " id integer primary key autoincrement, "
			+ " alias text, "
			+ " name text) ";
	
	/**
	 * 全局字典
	 */
	public static final String CREATE_GF_DICTENTRY = "create table DictEntry ("
			+ " id integer primary key autoincrement,"
			+ " name text, "
			+ " type text)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_INTERFACE);
		db.execSQL(CREATE_YH_RISK);
		db.execSQL(CREATE_GF_DICTTYPE);
		db.execSQL(CREATE_GF_DICTENTRY);
		db.execSQL(CREATE_YH_AREA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
}
