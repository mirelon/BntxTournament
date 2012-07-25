package com.bntx.tournament.row;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import com.bntx.tournament.Globals;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Event extends Row {

	public static Long START = 1L;
	public static Long END = 2L;
	public static Long PASS = 3L;
	public static Long CATCH = 4L;
	public static Long BAD_PASS = 5L;
	public static Long PULL = 6L;
	public static Long DROP_PULL = 7L;
	public static Long BLOCK = 8L;
	public static Long GREATEST = 8L;
	public static Long LAYOUT_CATCH = 9L;
	public static Long LAYOUT_BLOCK = 10L;
	public static Long ASSIST = 11L;
	public static Long SCORE = 12L;
	public static Long HALF_TIME = 13L;
	public static Long TIMEOUT = 14L;
	public static Long INJURY = 15L;
	public static Long PLAYER_DOWN = 16L;
	public static Long PLAYER_UP = 17L;
		
	
	private Long code;
	private Long match_id;
	/**
	 * team_player_id or team_id for global events
	 */
	private Long target_id;
	private Timestamp timestamp;
	
	public Event() {
	}
	
	public Event(Cursor cursor) {
		setId(Integer.parseInt(cursor.getString(0)));
        setTimestamp(Timestamp.valueOf(cursor.getString(1)));
        setCode(Long.parseLong(cursor.getString(2)));
        setTargetId(Long.parseLong(cursor.getString(3)));
        setMatchId(Long.parseLong(cursor.getString(4)));
	}
	
	/**
	 * @return the name
	 */
	public Long getCode() {
		return code;
	}
	/**
	 * @param name the name to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}
	
	public String getName() {
		if(getCode() == START)return "start";
		if(getCode() == END)return "end";
		if(getCode() == PASS)return "pass";
		if(getCode() == CATCH)return "catch";
		if(getCode() == BAD_PASS)return "bad pass";
		if(getCode() == PULL)return "pull";
		if(getCode() == DROP_PULL)return "drop pull";
		if(getCode() == BLOCK)return "block";
		if(getCode() == GREATEST)return "greatest";
		if(getCode() == LAYOUT_CATCH)return "layout catch";
		if(getCode() == LAYOUT_BLOCK)return "layout block";
		if(getCode() == ASSIST)return "assist";
		if(getCode() == SCORE)return "score";
		if(getCode() == HALF_TIME)return "half time";
		if(getCode() == TIMEOUT)return "timeout";
		if(getCode() == INJURY)return "injury";
		if(getCode() == PLAYER_DOWN)return "player down";
		if(getCode() == PLAYER_UP)return "player up";
		return "";
	}
	
	/**
	 * Will be used by ArrayAdapter
	 */
	@Override
	public String toString() {
		return getId() + ":" + getTimestamp().toString() + ":" + getMatchId() + ":" + getTargetId() + ":" + getName();
	}

	public ContentValues getContentValues() {
		ContentValues values = super.getContentValues();
		values.put("timestamp", getTimestamp().toString());
		values.put("code", getCode());
		values.put("target_id", getTargetId());
		values.put("match_id", getMatchId());
		return values;
	}

	public String getTableName() {
		return "events";
	}

	public static Event getFromListItem(String listItem) {
		return Globals.getDb().getEventById(parseIdFromListItem(listItem));
	}

	/**
	 * @return the match_id
	 */
	public Long getMatchId() {
		return match_id;
	}

	/**
	 * @param match_id the match_id to set
	 */
	public void setMatchId(Long match_id) {
		this.match_id = match_id;
	}

	/**
	 * @return the target_id
	 */
	public Long getTargetId() {
		return target_id;
	}

	/**
	 * @param target_id the target_id to set
	 */
	public void setTargetId(Long target_id) {
		this.target_id = target_id;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public void setTimestampNow() {
		setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
