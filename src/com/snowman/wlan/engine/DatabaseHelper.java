package com.snowman.wlan.engine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "user.db";
	private static final int VERSION = 1;
	private static final String CREATE_TABLE_USER = "CREATE TABLE user(lac INTEGER, ci INTEGER, isTD INTEGER, ssid TEXT, mac TEXT);";
    
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
