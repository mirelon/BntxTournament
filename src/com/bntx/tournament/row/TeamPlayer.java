package com.bntx.tournament.row;

import com.bntx.tournament.Globals;

import android.content.ContentValues;

public class TeamPlayer extends Row {
	
	private Long team_id;
	private Long player_id;
	
	public TeamPlayer() {
	}
	/**
	 * Will be used by ArrayAdapter
	 */
	@Override
	public String toString() {
		return getName();
	}

	public ContentValues getContentValues() {
		ContentValues values = super.getContentValues();
		values.put("team_id", getTeamId());
		values.put("player_id", getPlayerId());
		return values;
	}

	public String getTableName() {
		return "teams_players";
	}
	/**
	 * @return the team_id
	 */
	public Long getTeamId() {
		return team_id;
	}
	/**
	 * @param team_id the team_id to set
	 */
	public void setTeamId(Long team_id) {
		this.team_id = team_id;
	}
	/**
	 * @return the player_id
	 */
	public Long getPlayerId() {
		return player_id;
	}
	/**
	 * @param player_id the player_id to set
	 */
	public void setPlayerId(Long player_id) {
		this.player_id = player_id;
	}
	
	public Team getTeam() {
		return Globals.getDb().getTeamById(team_id);
	}
	
	public Player getPlayer() {
		return Globals.getDb().getPlayerById(player_id);
	}
	
	public String getName() {
		return getPlayer().getName();
	}

	public static TeamPlayer getFromListItem(String listItem) {
		return Globals.getDb().getTeamPlayerById(parseIdFromListItem(listItem));
	}
	
}
