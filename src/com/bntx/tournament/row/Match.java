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

	private Team team1;
	private Team team2;
	
	private List<Event> eventList = new ArrayList<Event>();
	private int team1Points = -1;
	private int team2Points = -1;
	
	public Match() {
	}
	
	public Match(Cursor cursor) {
		setId(Integer.parseInt(cursor.getString(0)));
		setTeam1Id(Long.parseLong(cursor.getString(1)));
		setTeam2Id(Long.parseLong(cursor.getString(2)));
	}

	public Team getTeam1() {
		if(team1 == null) {
			setTeam1(Globals.getDb().getTeamById(getTeam1Id()));
		}
		return team1;
	}

	public Team getTeam2() {
		if(team2 == null) {
			setTeam2(Globals.getDb().getTeamById(getTeam2Id()));
		}
		return team2;
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
	
	public Event addEvent(Long code) {
		return addEvent(code, 0L);
	}
	
	public Event addEvent(Long code, Long target_id) {
		return addEvent(code, target_id, new Timestamp(Calendar.getInstance().getTime().getTime()));
	}
	
	public Event addEvent(Long code, Long target_id, Timestamp timestamp) {
		Event event = new Event();
		event.setTimestamp(timestamp);
		event.setCode(code);
		event.setMatchId(this.getId());
		event.setTargetId(target_id);
		Log.d("Add event:", event.toString());
		event.save();
		eventList = getEvents();
		eventList.add(event);
		return event;
	}

	public void turnoverForTeam(Team team) {
		addEvent(Event.BLOCK, team.getId());
	}

	public void pullForTeam(Team team) {
		addEvent(Event.PULL, team.getId());
	}
	
	public void score(TeamPlayer scorer, TeamPlayer assist) {
		Event scoreEvent = addEvent(Event.SCORE, scorer.getId());
		if(assist.getId() != 0) {
			addEvent(Event.ASSIST, assist.getId(), scoreEvent.getTimestamp());
		}
		if(scorer.getTeamId() == getTeam1Id()) {
			setTeam1Points(getTeam1Points() + 1);
		}
		if(scorer.getTeamId() == getTeam2Id()) {
			setTeam2Points(getTeam2Points() + 1);
		}
	}

	public int getTeam1Points() {
		if(team1Points == -1) {
			setTeam1Points(Globals.getDb().getPointsForTeamInMatch(getTeam1(), this));
		}
		return team1Points;
	}

	public int getTeam2Points() {
		if(team2Points == -1) {
			setTeam2Points(Globals.getDb().getPointsForTeamInMatch(getTeam2(), this));
		}
		return team2Points;
	}
	
	public List<TeamPlayer> getActivePlayersForTeam(Team team) {
		Log.d("Match", "getActivePlayersForTeam");
		HashMap<Long, Boolean> activeStatus = new HashMap<Long, Boolean>();
		for (TeamPlayer teamPlayer : team.getPlayers()) {
			activeStatus.put(teamPlayer.getId(), false);
		}
		for (Event event : getEvents()) {
			if(event.getCode() == Event.PLAYER_UP && activeStatus.containsKey(event.getTargetId())) {
				activeStatus.put(event.getTargetId(), true);
			}
			if(event.getCode() == Event.PLAYER_DOWN && activeStatus.containsKey(event.getTargetId())) {
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
		if(eventList.isEmpty()) {
			eventList = Globals.getDb().getEventsForMatch(this);
		}
		Log.d("match events: ", Arrays.toString(eventList.toArray()));
		return eventList;
	}

	/**
	 * @param team1 the team1 to set
	 */
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	/**
	 * @param team2 the team2 to set
	 */
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	/**
	 * @param team1Points the team1Points to set
	 */
	public void setTeam1Points(int team1Points) {
		this.team1Points = team1Points;
	}

	/**
	 * @param team2Points the team2Points to set
	 */
	public void setTeam2Points(int team2Points) {
		this.team2Points = team2Points;
	}
	
	public boolean isAfterHalfTime() {
		for (Event event : getEvents()) {
			if(event.getCode() == Event.HALF_TIME) {
				return true;
			}
		}
		return false;
	}
	
	public void start() {
		addEvent(Event.START);
	}
	
	public void halfTime() {
		addEvent(Event.HALF_TIME);
	}
	
	public void end() {
		addEvent(Event.END);
	}
	
	/**
	 * Based on events. Returns null if no team is offending.
	 * @return Team
	 */
	public Team getOffendingTeam() {
		List<Event> eventList = getEvents();
		if(eventList.size() == 0) return null;
		Event event = eventList.get(eventList.size() - 1);
		if(event.getCode() == Event.SCORE || event.getCode() == Event.ASSIST) {
			return null; // pull is needed
		}
		if(event.getCode() == Event.BLOCK) {
			return Team.getById(event.getTargetId());
		}
		if(event.getCode() == Event.PULL) {
			return getOtherTeam(event.getTargetId());
		}
		return null;
	}
	
	public Long getOtherTeamId(Long teamId) {
		if(getTeam1Id() == teamId) return getTeam2Id();
		if(getTeam2Id() == teamId) return getTeam1Id();
		return null;
	}
	
	public Team getOtherTeam(Long teamId) {
		if(getTeam1Id() == teamId) return getTeam2();
		if(getTeam2Id() == teamId) return getTeam1();
		return null;
	}
	
	public int getNumberOfBlocksForTeam(Long teamId) {
		return Globals.getDb().getBlocksForTeamInMatch(Team.getById(teamId), this);
	}
	
	public boolean isFreezed() {
		boolean freezed = false;
		List<Event> eventList = getEvents();
		for (Event event : eventList) {
			if(event.getCode() == Event.FREEZE) {
				freezed = true;
			}
			if(event.getCode() == Event.PLAY_ON) {
				freezed = false;
			}
		}
		return freezed;
	}
	
	/**
	 * Returns deleted Event, or null
	 * @return
	 */
	public Event undo() {
		getEvents();
		if(eventList.size() > 0) {
			Event event = eventList.get(eventList.size()-1);
			if(Globals.getDb().deleteLastEvent(this) == 1) {
				eventList.remove(eventList.size()-1);
				return event;
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}
	
}
