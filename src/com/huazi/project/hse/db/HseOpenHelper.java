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

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(CREATE_TEMPLATE); //创建Template表
		db.execSQL(CREATE_INTERFACE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
}
