package com.bntx.tournament.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bntx.tournament.row.TeamPlayer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class ActivePlayerListAdapter extends ArrayAdapter<TeamPlayer> {
	
	protected Context context;
	protected int layoutResourceId;
	protected List<TeamPlayer> teamPlayerList;
	
	protected List<TeamPlayer> activePlayerList;

	public ActivePlayerListAdapter(Context context, int textViewResourceId,
			List<TeamPlayer> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.teamPlayerList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if(row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
		}
		TeamPlayer teamPlayer = teamPlayerList.get(position);
		((CheckedTextView)row).setText(teamPlayer.getIdWithName());
		((CheckedTextView)row).setChecked(getActivePlayerIds().contains(teamPlayer.getId()));
		Log.d("set checked", Long.toString(teamPlayer.getId()) + ":"  + Boolean.toString(getActivePlayerIds().contains(teamPlayer.getId())));
		Log.d("active player list", Arrays.toString(getActivePlayerIds().toArray()));
		Log.d("getView", "position=" + position + ", text=" + ((TextView)row).getText().toString());
		return row;
	}
	
	

	public List<TeamPlayer> getActivePlayerList() {
		return activePlayerList;
	}
	
	public List<Long> getActivePlayerIds() {
		List<Long> activePlayerIds = new ArrayList<Long>();
		for (TeamPlayer teamPlayer : getActivePlayerList()) {
			activePlayerIds.add(teamPlayer.getId());
		}
		return activePlayerIds;
	}

	public void setActivePlayerList(List<TeamPlayer> activePlayerList) {
		this.activePlayerList = activePlayerList;
	}
	
	

}
