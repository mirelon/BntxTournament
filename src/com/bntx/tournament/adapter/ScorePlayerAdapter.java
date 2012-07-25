package com.bntx.tournament.adapter;

import java.util.Arrays;
import java.util.List;

import com.bntx.tournament.row.TeamPlayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class ScorePlayerAdapter extends ArrayAdapter<TeamPlayer> {

	protected Context context;
	protected int layoutResourceId;
	protected List<TeamPlayer> teamPlayerList;
	protected int dropDownViewResource;
	
	public ScorePlayerAdapter(Context context, int textViewResourceId,
			List<TeamPlayer> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.teamPlayerList = objects;
	}
	
	protected View getFinalizedRow(int position, View row) {
		TeamPlayer teamPlayer = teamPlayerList.get(position);
		if(teamPlayer.getId() == 0L) {
			((TextView)row).setText("");
		} else {
			((TextView)row).setText(teamPlayer.getIdWithName());
		}
		((TextView)row).setTextColor(Color.BLACK);
		Log.d("getView", "position=" + position + ", text=" + ((TextView)row).getText().toString());
		return row;
	}
	
	protected View getView(int position, View convertView, ViewGroup parent, boolean isDropDown) {
		View row = convertView;
		
		if(row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(isDropDown?dropDownViewResource:layoutResourceId, parent, false);
		}
		return getFinalizedRow(position, row);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent, false);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent, true);
	}

	@Override
	public void setDropDownViewResource(int resource) {
		super.setDropDownViewResource(resource);
		dropDownViewResource = resource;
	}
	
	

}
