package com.bntx.tournament.row;

import android.content.ContentValues;

public class Row {

	public static final String KEY_ID = "_id";
	private Long id;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if(getId() != null) {
			// if adding new row, id is not available, so we will save it without id.
			values.put(KEY_ID, getId());
		}
		return values;
	}
	
	public String getTableName() {
		return "";
	}
	
	public String getIdWithName() {
		return this.getId() + ": " + this.toString();
	}
	
	public static Long parseIdFromListItem(String listItem) {
		return Long.parseLong(listItem.split(":")[0]);
	}
	
}
