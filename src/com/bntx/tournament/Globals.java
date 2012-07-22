package com.bntx.tournament;

import com.bntx.tournament.row.Team;

public class Globals {
	
	private static Globals instance = null;
	
	public static Globals getInstance() {
		if(instance == null) {
			instance = new Globals();
		}
		return instance;
	}
	

	/**
	 * @return the selectedTournament
	 */
	public static Team getSelectedTeam() {
		return getInstance().selectedTeam;
	}

	/**
	 * @param selectedTournament the selectedTournament to set
	 */
	public static void setSelectedTeam(Team selectedTeam) {
		getInstance().selectedTeam = selectedTeam;
	}

	/**
	 * @return the db
	 */
	public static DatabaseHandler getDb() {
		return getInstance().db;
	}


	/**
	 * @param db the db to set
	 */
	public static void setDb(DatabaseHandler db) {
		getInstance().db = db;
	}

	private Team selectedTeam;
	
	private DatabaseHandler db;
	
	
	
}
