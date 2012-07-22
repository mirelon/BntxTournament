package com.bntx.tournament.row;


import com.bntx.tournament.Globals;

import android.content.ContentValues;
import android.util.Log;

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
	
	public void delete() {
		Globals.getDb().deleteRow(this);
	}
	
	public void save() {
		if(getId() == null) {
			Log.d("Row", "addRow");
			Globals.getDb().addRow(this);
		} else {
			Log.d("Row", "updateRow");
			Globals.getDb().updateRow(this);
		}
	}
	
}
