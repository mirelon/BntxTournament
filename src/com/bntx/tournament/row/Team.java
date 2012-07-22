package com.bntx.tournament.row;

import com.bntx.tournament.Globals;

import android.content.ContentValues;

public class Team extends Row {
	
	
	private String name;
	
	public Team() {
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
		return "teams";
	}

	public static Team getFromListItem(String listItem) {
		return Globals.getDb().getTeamById(parseIdFromListItem(listItem));
	}
	
}
