package com.bntx.tournament.row;

import java.util.List;

import com.bntx.tournament.Globals;

import android.content.ContentValues;
import android.database.Cursor;

public class Team extends Row {
	
	
	private String name;
	
	public Team() {
	}

	public static Team getById(Long id) {
		return Globals.getDb().getTeamById(id);
	}
	
	public Team(Cursor cursor) {
		setId(Integer.parseInt(cursor.getString(0)));
		setName(cursor.getString(1));
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
	
	public List<TeamPlayer> getPlayers() {
		return Globals.getDb().getPlayersForTeam(this);
	}
	
}
