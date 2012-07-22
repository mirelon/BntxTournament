package com.bntx.tournament;

import java.util.ArrayList;
import java.util.List;

import com.bntx.tournament.row.Player;
import com.bntx.tournament.row.Row;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.TeamPlayer;
import com.bntx.tournament.table.PlayersTable;
import com.bntx.tournament.table.TeamsPlayersTable;
import com.bntx.tournament.table.TeamsTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 5;
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
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.d("DatabaseHandler", "onUpgrade");
    	TeamsTable.onUpgrade(db, oldVersion, newVersion);
    	PlayersTable.onUpgrade(db, oldVersion, newVersion);
    	TeamsPlayersTable.onUpgrade(db, oldVersion, newVersion);
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
            	Team team = new Team();
                team.setId(Integer.parseInt(cursor.getString(0)));
                team.setName(cursor.getString(1));
                teamList.add(team);
            } while (cursor.moveToNext());
        }
     
        return teamList;
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
    	if (cursor.moveToFirst()) {
    		Team team = new Team();
    		team.setId(Integer.parseInt(cursor.getString(0)));
    		team.setName(cursor.getString(1));
    		return team;
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
    


    public TeamPlayer getTeamPlayerById(Long team_player_id) {
    	String selectQuery = "SELECT teams_players.*, players.name FROM teams_players JOIN players ON players._id = teams_players.player_id WHERE teams_players._id = " + team_player_id;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) {
    		TeamPlayer teamPlayer = new TeamPlayer();
    		teamPlayer.setId(Integer.parseInt(cursor.getString(0)));
            teamPlayer.setTeamId(Long.parseLong(cursor.getString(1)));
            teamPlayer.setPlayerId(Long.parseLong(cursor.getString(2)));
    		return teamPlayer;
    	}
    	return null;
    }
    

    public List<TeamPlayer> getPlayersForTeam(Team team) {
        List<TeamPlayer> teamPlayerList = new ArrayList<TeamPlayer>();
        String selectQuery = "SELECT teams_players.*, players.name FROM teams_players JOIN players ON teams_players.player_id = players._id WHERE team_id = " + team.getId();
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
            	TeamPlayer teamPlayer = new TeamPlayer();
                teamPlayer.setId(Integer.parseInt(cursor.getString(0)));
                teamPlayer.setTeamId(Long.parseLong(cursor.getString(1)));
                teamPlayer.setPlayerId(Long.parseLong(cursor.getString(2)));
                teamPlayerList.add(teamPlayer);
            } while (cursor.moveToNext());
        }
     
        return teamPlayerList;
    }
    
}