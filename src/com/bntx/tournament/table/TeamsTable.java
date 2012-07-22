package com.bntx.tournament.table;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TeamsTable extends Table {

	public static String table_name = "teams";
	
	public static String getColumnsDefinitions() {
		return "name TEXT";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + table_name + "(" + KEY_ID + " INTEGER PRIMARY KEY," + getColumnsDefinitions() + ")");
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + table_name);
		onCreate(db);
	}
}
