package com.bntx.tournament.row;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bntx.tournament.Globals;
import com.bntx.tournament.activity.list.TeamListActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Match extends Row {
	

	private Long team1_id;
	private Long team2_id;
	
	public Match() {
	}
	
	public Match(Cursor cursor) {
		setId(Integer.parseInt(cursor.getString(0)));
		setTeam1Id(Long.parseLong(cursor.getString(1)));
		setTeam2Id(Long.parseLong(cursor.getString(2)));
	}

	public Team getTeam1() {
		return Globals.getDb().getTeamById(getTeam1Id());
	}

	public Team getTeam2() {
		return Globals.getDb().getTeamById(getTeam2Id());
	}
	
	/**
	 * Will be used by ArrayAdapter
	 */
	@Override
	public String toString() {
		return getTeam1().getName() + " : " + getTeam2().getName();
	}

	public ContentValues getContentValues() {
		ContentValues values = super.getContentValues();
		values.put("team1_id", getTeam1Id());
		values.put("team2_id", getTeam2Id());
		return values;
	}

	public String getTableName() {
		return "matches";
	}

	public static Match getFromListItem(String listItem) {
		return Globals.getDb().getMatchById(parseIdFromListItem(listItem));
	}

	/**
	 * @return the team1_id
	 */
	public Long getTeam1Id() {
		return team1_id;
	}

	/**
	 * @param team1_id the team1_id to set
	 */
	public void setTeam1Id(Long team1_id) {
		this.team1_id = team1_id;
	}

	/**
	 * @return the team2_id
	 */
	public Long getTeam2Id() {
		return team2_id;
	}

	/**
	 * @param team2_id the team2_id to set
	 */
	public void setTeam2Id(Long team2_id) {
		this.team2_id = team2_id;
	}
	
	public void turnoverForTeam(Team team) {
		Event event = new Event();
		event.setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
		event.setCode(Event.CODE_BLOCK);
		event.setMatchId(this.getId());
		event.setTargetId(team.getId());
		Log.d("Turnover event:", event.toString());
		event.save();
	}
	
	public List<TeamPlayer> getActivePlayersForTeam(Team team) {
		HashMap<Long, Boolean> activeStatus = new HashMap<Long, Boolean>();
		for (TeamPlayer teamPlayer : team.getPlayers()) {
			activeStatus.put(teamPlayer.getId(), false);
		}
		for (Event event : getEvents()) {
			if(event.getCode() == Event.CODE_PLAYER_UP) {
				activeStatus.put(event.getTargetId(), true);
			}
			if(event.getCode() == Event.CODE_PLAYER_DOWN) {
				activeStatus.put(event.getTargetId(), false);
			}
		}
		List<TeamPlayer> playerList = new ArrayList<TeamPlayer>();
		for (Map.Entry<Long, Boolean> entry : activeStatus.entrySet()) {
			if(entry.getValue()) {
				playerList.add(TeamPlayer.getById(entry.getKey()));
			}
		}
		Log.d("Match: active players for team", team.toString() + ": " + Arrays.toString(playerList.toArray()));
		return playerList;
	}
	
	public List<Event> getEvents() {
		List<Event> eventList = Globals.getDb().getEventsForMatch(this);
		Log.d("match events: ", Arrays.toString(eventList.toArray()));
		return eventList;
	}
	
}
