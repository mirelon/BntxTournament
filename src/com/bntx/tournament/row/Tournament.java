package com.bntx.tournament.row;

import android.content.ContentValues;

public class Tournament extends Row {
	
	
	private String name;
	
	public Tournament() {
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
		return "tournaments";
	}
	
}
