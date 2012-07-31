package com.bntx.tournament;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bntx.tournament.row.Event;
import com.bntx.tournament.row.Match;
import com.bntx.tournament.row.Player;
import com.bntx.tournament.row.Row;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.TeamPlayer;
import com.bntx.tournament.table.EventsTable;
import com.bntx.tournament.table.MatchesTable;
import com.bntx.tournament.table.PlayersTable;
import com.bntx.tournament.table.TeamsPlayersTable;
import com.bntx.tournament.table.TeamsTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * TODO: close database after operations
 * @author Miso | michal.kovac@bonetics.com
 * Bonetics, s.r.o. | http://bonetics.com | fb|tw - @bonetics
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 10;
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
        TeamsTable.onCreate(db);
        PlayersTable.onCreate(db);
        TeamsPlayersTable.onCreate(db);
        MatchesTable.onCreate(db);
        EventsTable.onCreate(db);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.d("DatabaseHandler", "onUpgrade");
    	TeamsTable.onUpgrade(db, oldVersion, newVersion);
    	PlayersTable.onUpgrade(db, oldVersion, newVersion);
    	TeamsPlayersTable.onUpgrade(db, oldVersion, newVersion);
    	MatchesTable.onUpgrade(db, oldVersion, newVersion);
    	EventsTable.onUpgrade(db, oldVersion, newVersion);
    }
    
    public void addRow(Row row) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.insert(row.getTableName(), null, row.getContentValues());
    	db.close();
    }
    
    public void updateRow(Row row) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.update(row.getTableName(), row.getContentValues(), Row.KEY_ID + " = " + row.getId(), null);
    	db.close();
    }
    
    public void deleteRow(Row row) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(row.getTableName(), Row.KEY_ID + " = ?", new String[]{row.getId().toString()});
    	db.close();
    }

    public List<Team> getAllTeams() {
        List<Team> teamList = new ArrayList<Team>();
        String selectQuery = "SELECT * FROM teams";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                teamList.add(new Team(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return teamList;
    }

    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM players";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                playerList.add(new Player(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return playerList;
    }

    public List<Match> getAllMatches() {
        List<Match> matchList = new ArrayList<Match>();
        String selectQuery = "SELECT  * FROM matches";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                matchList.add(new Match(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return matchList;
    }

    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<Event>();
        String selectQuery = "SELECT  * FROM events";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                eventList.add(new Event(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return eventList;
    }
    
    public List<Player> getPlayersNotInTeam(Team team) {
        List<Player> playerList = getAllPlayers();
        for (TeamPlayer teamPlayer : getPlayersForTeam(team)) {
			for (Player player : playerList) {
				if(player.getId() == teamPlayer.getPlayerId()) {
					playerList.remove(player);
					break;
				}
			}
		}
        return playerList;
    }

    public Team getTeamById(Long team_id) {
    	String selectQuery = "SELECT * FROM teams WHERE _id = " + team_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	Team team = null;
    	if (cursor.moveToFirst()) {
    		team = new Team(cursor);
    	}
    	db.close();
    	return team;
    }
    
    public Match getMatchById(Long match_id) {
    	String selectQuery = "SELECT * FROM matches WHERE _id = " + match_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	Match match = null;
    	if (cursor.moveToFirst()) {
    		match = new Match(cursor);
    	}
    	db.close();
    	return match;
    }

    public Player getPlayerById(Long player_id) {
    	String selectQuery = "SELECT * FROM players WHERE _id = " + player_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	Player player = null;
    	if (cursor.moveToFirst()) {
    		player = new Player(cursor);
    	}
    	db.close();
    	return player;
    }
    


    public TeamPlayer getTeamPlayerById(Long team_player_id) {
    	String selectQuery = "SELECT teams_players.*, players.name FROM teams_players JOIN players ON players._id = teams_players.player_id WHERE teams_players._id = " + team_player_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	TeamPlayer teamPlayer = null;
    	if (cursor.moveToFirst()) {
    		teamPlayer = new TeamPlayer(cursor);
    	}
    	db.close();
    	return teamPlayer;
    }

    public Event getEventById(Long event_id) {
    	String selectQuery = "SELECT * FROM events WHERE _id = " + event_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	Event event = null;
    	if (cursor.moveToFirst()) {
    		event = new Event(cursor);
    	}
    	db.close();
    	return event;
    }
    

    public List<TeamPlayer> getPlayersForTeam(Team team) {
        List<TeamPlayer> teamPlayerList = new ArrayList<TeamPlayer>();
        String selectQuery = "SELECT teams_players.*, players.name FROM teams_players JOIN players ON teams_players.player_id = players._id WHERE team_id = " + team.getId();
        Log.d("getPlayersForTeam", selectQuery);
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                teamPlayerList.add(new TeamPlayer(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return teamPlayerList;
    }
    
    public List<Event> getEventsForMatch(Match match) {
    	List<Event> eventList = new ArrayList<Event>();
    	String selectQuery = "SELECT * FROM events WHERE match_id = " + match.getId();
    	
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
            	eventList.add(new Event(cursor));
            } while (cursor.moveToNext());
        }
        db.close();
        return eventList;
    }
    
    public int getPointsForTeamInMatch(Team team, Match match) {

    	String selectQuery = "SELECT COUNT(*) FROM events JOIN teams_players ON events.target_id = teams_players._id WHERE events.code = " + Event.SCORE + " AND teams_players.team_id = " + team.getId() + " AND match_id = " + match.getId();
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	Integer points = null;
    	if (cursor.moveToFirst()) {
    		points = cursor.getInt(0);
    	}
    	db.close();
    	return points;
    }
    
}