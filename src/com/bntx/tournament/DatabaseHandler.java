package com.bntx.tournament;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

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

	private static final int DATABASE_VERSION = 13;
	public static final String KEY_ID = "_id";
	
    private static final String DATABASE_NAME = "/sdcard/BntxTournament/db1.db";
 
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


    public List<Match> getNotEndedMatches() {
        List<Match> matchList = new ArrayList<Match>();
        String selectQuery = "SELECT * FROM matches WHERE matches._id NOT IN (SELECT events.match_id FROM events WHERE events.code = " + Event.END +")";
     
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
    	String selectQuery = "SELECT * FROM events WHERE match_id = " + match.getId() + " ORDER BY timestamp ASC";
    	
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

    public int getBlocksForTeamInMatch(Team team, Match match) {

    	String selectQuery = "SELECT COUNT(*) FROM events WHERE code = " + Event.BLOCK + " AND target_id = " + team.getId() + " AND match_id = " + match.getId();
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	Integer points = null;
    	if (cursor.moveToFirst()) {
    		points = cursor.getInt(0);
    	}
    	db.close();
    	return points;
    }
    
    public void importFromDb(String filename) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("ATTACH DATABASE ? AS db2;", new Object[]{filename});
    	db.execSQL("INSERT INTO events(timestamp, code, target_id, match_id) SELECT timestamp, code, target_id, match_id FROM db2.events;", new Object[]{});
    }

    public List<Object[]> getPlayersWithHighestEventCount(Long eventCode, Integer limit) {
    	List<Object[]> playerList = new ArrayList<Object[]>();
    	String selectQuery = "SELECT players.name, COUNT(*) AS total FROM players JOIN teams_players ON teams_players.player_id = players._id JOIN events ON events.target_id = teams_players._id WHERE events.code = ? GROUP BY players._id ORDER BY total DESC LIMIT ?;";
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, new String[]{eventCode.toString(), limit.toString()});
    	if (cursor.moveToFirst()) {
    		do {
    			playerList.add(new Object[]{cursor.getString(0), cursor.getLong(1)});
            } while (cursor.moveToNext());
    	}
    	return playerList;
    }

    public List<Object[]> getHighScorers(int limit) {
    	return getPlayersWithHighestEventCount(Event.SCORE, limit);
    }
    
    public List<Object[]> getHighAssists(int limit) {
    	return getPlayersWithHighestEventCount(Event.ASSIST, limit);
    }
    public String getTimeStatistics() {
    	String stats = "";
		TreeMap<String, Long> offendingTimeForOnePoint = new TreeMap<String, Long>();
		TreeMap<String, Long> offendingTimePercentForMatch = new TreeMap<String, Long>();
		TreeMap<String, Long> scoreVsDropPercentForMatch = new TreeMap<String, Long>();
    	for (Match match : getAllMatches()) {

        	HashMap<Long, Long> offenseTimeForTeam = new HashMap<Long, Long>();
        	HashMap<Long, Long> pointsForTeam = new HashMap<Long, Long>();
    		if(!offenseTimeForTeam.keySet().contains(match.getTeam1Id())) {
    			offenseTimeForTeam.put(match.getTeam1Id(), 0L);
    		}
    		if(!offenseTimeForTeam.keySet().contains(match.getTeam2Id())) {
    			offenseTimeForTeam.put(match.getTeam2Id(), 0L);
    		}
    		if(!pointsForTeam.keySet().contains(match.getTeam1Id())) {
    			pointsForTeam.put(match.getTeam1Id(), 0L);
    		}
    		if(!pointsForTeam.keySet().contains(match.getTeam2Id())) {
    			pointsForTeam.put(match.getTeam2Id(), 0L);
    		}
    		
    		Timestamp lastTimestamp = new Timestamp(0L);
    		Long offendingTeamId = 0L;
    		Timestamp freezeTimestamp = new Timestamp(0L);
    		
			for (Event event : match.getEvents()) {
				Log.d("event code: ", event.getCode().toString());
				long eventTime = event.getTimestamp().getTime();
				Log.d("event timestamp: ", event.getTimestamp().toString() + " " + eventTime);
				if(event.getCode() == Event.PULL) {
					lastTimestamp = event.getTimestamp();
					offendingTeamId = match.getOtherTeamId(event.getTargetId());
				}
				else if(event.getCode() == Event.BLOCK) {
					offenseTimeForTeam.put(offendingTeamId, offenseTimeForTeam.get(offendingTeamId) + eventTime - lastTimestamp.getTime());
					Log.d("team " + offendingTeamId + "dropped the disc, offending time:", offenseTimeForTeam.get(offendingTeamId).toString());
					lastTimestamp = event.getTimestamp();
					offendingTeamId = event.getTargetId();
				}
				else if(event.getCode() == Event.SCORE) {
					offenseTimeForTeam.put(offendingTeamId, offenseTimeForTeam.get(offendingTeamId) + eventTime - lastTimestamp.getTime());
					Log.d("team " + offendingTeamId + "scored, offending time:", offenseTimeForTeam.get(offendingTeamId).toString());
					pointsForTeam.put(offendingTeamId, pointsForTeam.get(offendingTeamId) + 1);
					lastTimestamp = event.getTimestamp();
					offendingTeamId = event.getTargetId();
				} else if(event.getCode() == Event.FREEZE) {
					freezeTimestamp = event.getTimestamp();
				} else if(event.getCode() == Event.PLAY_ON) {
					// shift lastTimestamp by freeze period
					if(freezeTimestamp.getTime() > 0) {
						lastTimestamp = new Timestamp(lastTimestamp.getTime() + eventTime - freezeTimestamp.getTime());
					} else {
						Log.d("Time statistics", "Event play-on is called with no freeze event before");
					}
				}
			}
			for (Entry<Long, Long> entry : pointsForTeam.entrySet()) {
				String vzapase = Team.getById(entry.getKey()).getName() + " v zapase s " + match.getOtherTeam(entry.getKey()).getName();
				if(entry.getValue() > 0) {
					offendingTimeForOnePoint.put(vzapase,
							offenseTimeForTeam.get(entry.getKey()) / entry.getValue() / 1000);
					offendingTimePercentForMatch.put(vzapase,
							100*offenseTimeForTeam.get(entry.getKey()) / (offenseTimeForTeam.get(entry.getKey()) + offenseTimeForTeam.get(match.getOtherTeamId(entry.getKey()))));
					scoreVsDropPercentForMatch.put(vzapase,
							100 * pointsForTeam.get(entry.getKey()) / (pointsForTeam.get(entry.getKey()) + match.getNumberOfBlocksForTeam(match.getOtherTeamId(entry.getKey()))));
				}
			}
			
		}
    	stats += "Least effective teams (time needed to score):\n";
    	for (Entry<String, Long> entry : offendingTimeForOnePoint.entrySet()) {
			stats += entry.getKey() + ": " + entry.getValue() / 60 + ":" + entry.getValue() % 60 + "\n";  
		}
    	stats += "Most pressing teams (offense time in percent):\n";
    	for (Entry<String, Long> entry : offendingTimePercentForMatch.entrySet()) {
			stats += entry.getKey() + ": " + entry.getValue() + "%\n";
		}
    	stats += "Most effective teams (chance to score when offending):\n";
    	for (Entry<String, Long> entry : scoreVsDropPercentForMatch.entrySet()) {
			stats += entry.getKey() + ": " + entry.getValue() + "%\n";
		}
    	return stats;
    	
    }

    /**
     * Returns number of rows deleted (0 or 1)
     * @param match
     * @return
     */
	public int deleteLastEvent(Match match) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(
				"events",
				"_id = (SELECT _id FROM events WHERE match_id = ? ORDER BY timestamp DESC LIMIT 1)",
				new String[]{Long.toString(match.getId())}
		);
	}
    
}