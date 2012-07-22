package com.bntx.tournament.row;

import android.content.ContentValues;

public class TournamentTeam extends Row {
	

	private Long tournament_id;
	private Long team_id;
	private String name;
	
	public TournamentTeam() {
	}
	
	/**
	 * @return tournament_id
	 */
	public Long getTournamentId() {
		return tournament_id;
	}
	/**
	 * @param tournament_id
	 */
	public void setTournamentId(Long tournament_id) {
		this.tournament_id = tournament_id;
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
		values.put("tournament_id", getTournamentId());
		values.put("team_id", getTeamId());
		return values;
	}

	public String getTableName() {
		return "tournaments_teams";
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
	
}
