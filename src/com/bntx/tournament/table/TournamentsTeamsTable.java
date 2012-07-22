package com.bntx.tournament.table;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TournamentsTeamsTable extends Table {

	public static String table_name = "tournaments_teams";
	
	public static String getColumnsDefinitions() {
		return "tournament_id INTEGER, team_id INTEGER";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + table_name + "(" + KEY_ID + " INTEGER PRIMARY KEY," + getColumnsDefinitions() + ")");
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + table_name);
		onCreate(db);
	}
}
