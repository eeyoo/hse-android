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
	
	public static final String CREATE_YH_RISK = "create table Risk ("
			+ " id integer primary key autoincrement, "
			+ " risk_type text, "
			+ " prof_type text, "
			+ " area_name text, "
			+ " rank integer, "
			+ " creater text, "
			+ " create_date text, "
			+ " file_name text)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_INTERFACE);
		db.execSQL(CREATE_YH_RISK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
}
