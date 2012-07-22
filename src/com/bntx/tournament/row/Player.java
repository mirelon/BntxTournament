package com.bntx.tournament.row;

import com.bntx.tournament.Globals;

import android.content.ContentValues;

public class Player extends Row {
	
	
	private String name;
	
	public Player() {
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Will be used by ArrayAdapter
	 */
	@Override
	public String toString() {
		return name;
	}

	public ContentValues getContentValues() {
		ContentValues values = super.getContentValues();
		values.put("name", getName());
		return values;
	}

	public String getTableName() {
		return "players";
	}

	public static Player getFromListItem(String listItem) {
		return Globals.getDb().getPlayerById(parseIdFromListItem(listItem));
	}
	
}
