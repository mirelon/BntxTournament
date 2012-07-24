package com.bntx.tournament;

import com.bntx.tournament.row.Match;
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

	/**
	 * @return the selectedMatch
	 */
	public static Match getSelectedMatch() {
		return getInstance().selectedMatch;
	}


	/**
	 * @param selectedMatch the selectedMatch to set
	 */
	public static void setSelectedMatch(Match selectedMatch) {
		getInstance().selectedMatch = selectedMatch;
	}

	/**
	 * @return the isAddingTeam1ForMatch
	 */
	public static boolean isAddingTeam1ForMatch() {
		return getInstance().isAddingTeam1ForMatch;
	}


	/**
	 * @param isAddingTeam1ForMatch the isAddingTeam1ForMatch to set
	 */
	public static void setAddingTeam1ForMatch(boolean isAddingTeam1ForMatch) {
		getInstance().isAddingTeam1ForMatch = isAddingTeam1ForMatch;
	}


	/**
	 * @return the isAddingTeam1ForMatch
	 */
	public static boolean isAddingTeam2ForMatch() {
		return getInstance().isAddingTeam2ForMatch;
	}


	/**
	 * @param isAddingTeam1ForMatch the isAddingTeam1ForMatch to set
	 */
	public static void setAddingTeam2ForMatch(boolean isAddingTeam2ForMatch) {
		getInstance().isAddingTeam2ForMatch = isAddingTeam2ForMatch;
	}

	/**
	 * @return the selectedTeam1ForMatch
	 */
	public static Team getSelectedTeam1ForMatch() {
		return getInstance().selectedTeam1ForMatch;
	}


	/**
	 * @param selectedTeam1ForMatch the selectedTeam1ForMatch to set
	 */
	public static void setSelectedTeam1ForMatch(Team selectedTeam1ForMatch) {
		getInstance().selectedTeam1ForMatch = selectedTeam1ForMatch;
	}

	/**
	 * @return the selectedTeam2ForMatch
	 */
	public static Team getSelectedTeam2ForMatch() {
		return getInstance().selectedTeam2ForMatch;
	}


	/**
	 * @param selectedTeam2ForMatch the selectedTeam2ForMatch to set
	 */
	public static void setSelectedTeam2ForMatch(Team selectedTeam2ForMatch) {
		getInstance().selectedTeam2ForMatch = selectedTeam2ForMatch;
	}

	private Team selectedTeam;
	
	private DatabaseHandler db;
	
	private Match selectedMatch;

	private boolean isAddingTeam1ForMatch;
	private boolean isAddingTeam2ForMatch;
	private Team selectedTeam1ForMatch;
	private Team selectedTeam2ForMatch;
	
	
}
