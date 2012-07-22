package com.bntx.tournament;

import com.bntx.tournament.row.Tournament;

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
	public static Tournament getSelectedTournament() {
		return getInstance().selectedTournament;
	}

	/**
	 * @param selectedTournament the selectedTournament to set
	 */
	public static void setSelectedTournament(Tournament selectedTournament) {
		getInstance().selectedTournament = selectedTournament;
	}

	private Tournament selectedTournament;
	
}
