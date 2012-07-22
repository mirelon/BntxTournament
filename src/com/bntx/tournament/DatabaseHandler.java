package com.bntx.tournament;

import java.util.ArrayList;
import java.util.List;

import com.bntx.tournament.row.Player;
import com.bntx.tournament.row.Row;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.Tournament;
import com.bntx.tournament.row.TournamentTeam;
import com.bntx.tournament.table.PlayersTable;
import com.bntx.tournament.table.TeamsTable;
import com.bntx.tournament.table.TournamentsTable;
import com.bntx.tournament.table.TournamentsTeamsTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 4;
	public static final String KEY_ID = "_id";
	
    private static final String DATABASE_NAME = "tournaments.db";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DatabaseHandler", "constructor");
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("DatabaseHandler", "onCreate");
        TournamentsTable.onCreate(db);
        TeamsTable.onCreate(db);
        TournamentsTeamsTable.onCreate(db);
        PlayersTable.onCreate(db);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.d("DatabaseHandler", "onUpgrade");
    	TournamentsTable.onUpgrade(db, oldVersion, newVersion);
    	TeamsTable.onUpgrade(db, oldVersion, newVersion);
        TournamentsTeamsTable.onUpgrade(db, oldVersion, newVersion);
    	PlayersTable.onUpgrade(db, oldVersion, newVersion);
    }
    
    public void addRow(Row row) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.insert(row.getTableName(), null, row.getContentValues());
    	db.close();
    }
    
    public void deleteRow(Row row) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(row.getTableName(), Row.KEY_ID + " = ?", new String[]{row.getId().toString()});
    	db.close();
    }
    
    public Tournament getTournament(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("tournaments", new String[] { KEY_ID,
                "name" }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
    	if (cursor != null)
            cursor.moveToFirst();
     
        Tournament tournament = new Tournament();
        tournament.setId(Integer.parseInt(cursor.getString(0)));
        tournament.setName(cursor.getString(1));
        return tournament;
    }
    
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournamentList = new ArrayList<Tournament>();
        String selectQuery = "SELECT  * FROM tournaments";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                Tournament tournament = new Tournament();
                tournament.setId(Integer.parseInt(cursor.getString(0)));
                tournament.setName(cursor.getString(1));
                tournamentList.add(tournament);
            } while (cursor.moveToNext());
        }
     
        return tournamentList;
    }


    public List<Team> getAllTeams() {
        List<Team> teamList = new ArrayList<Team>();
        String selectQuery = "SELECT  * FROM teams";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
            	Team team = new Team();
                team.setId(Integer.parseInt(cursor.getString(0)));
                team.setName(cursor.getString(1));
                teamList.add(team);
            } while (cursor.moveToNext());
        }
     
        return teamList;
    }
    

    public List<TournamentTeam> getTeamsForTournament(Tournament tournament) {
        List<TournamentTeam> tournamentTeamList = new ArrayList<TournamentTeam>();
        String selectQuery = "SELECT tournaments_teams.*, teams.name FROM tournaments_teams JOIN teams ON tournaments_teams.team_id = teams._id WHERE tournament_id = " + tournament.getId();
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
            	TournamentTeam tournamentTeam = new TournamentTeam();
                tournamentTeam.setId(Integer.parseInt(cursor.getString(0)));
                tournamentTeam.setTournamentId(Long.parseLong(cursor.getString(1)));
                tournamentTeam.setTeamId(Long.parseLong(cursor.getString(2)));
                tournamentTeam.setName(cursor.getString(3));
                tournamentTeamList.add(tournamentTeam);
            } while (cursor.moveToNext());
        }
     
        return tournamentTeamList;
    }
    
    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM players";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
            	Player player = new Player();
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setName(cursor.getString(1));
                playerList.add(player);
            } while (cursor.moveToNext());
        }
     
        return playerList;
    }
    
    public Tournament getTournamentById(Long tournament_id) {
    	String selectQuery = "SELECT * FROM tournaments WHERE _id = " + tournament_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) {
    		Tournament tournament = new Tournament();
    		tournament.setId(Integer.parseInt(cursor.getString(0)));
    		tournament.setName(cursor.getString(1));
    		return tournament;
    	}
    	return null;
    }

    public Team getTeamById(Long team_id) {
    	String selectQuery = "SELECT * FROM teams WHERE _id = " + team_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) {
    		Team team = new Team();
    		team.setId(Integer.parseInt(cursor.getString(0)));
    		team.setName(cursor.getString(1));
    		return team;
    	}
    	return null;
    }

    public TournamentTeam getTournamentTeamById(Long tournament_team_id) {
    	String selectQuery = "SELECT tournaments_teams.*, teams.name FROM tournaments_teams JOIN teams ON teams._id = tournaments_teams.team_id WHERE tournaments_teams._id = " + tournament_team_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) {
    		TournamentTeam tournamentTeam = new TournamentTeam();
    		tournamentTeam.setId(Integer.parseInt(cursor.getString(0)));
            tournamentTeam.setTournamentId(Long.parseLong(cursor.getString(1)));
            tournamentTeam.setTeamId(Long.parseLong(cursor.getString(2)));
    		tournamentTeam.setName(cursor.getString(3));
    		return tournamentTeam;
    	}
    	return null;
    }

    public Player getPlayerById(Long player_id) {
    	String selectQuery = "SELECT * FROM players WHERE _id = " + player_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) {
    		Player player = new Player();
    		player.setId(Integer.parseInt(cursor.getString(0)));
    		player.setName(cursor.getString(1));
    		return player;
    	}
    	return null;
    }
    
}