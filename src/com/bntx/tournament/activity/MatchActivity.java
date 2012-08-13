package com.bntx.tournament.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.activity.list.ActivePlayerListActivity;
import com.bntx.tournament.adapter.ScorePlayerAdapter;
import com.bntx.tournament.row.Match;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.TeamPlayer;

public class MatchActivity extends Activity {

	protected TextView textView1;
	protected TextView textView3;

	protected Drawable textView1Background;
	protected Drawable textView3Background;

	protected Spinner spinner1;
	protected Spinner spinner2;

	protected void toastShort(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	protected void toastLong(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("MatchActivity", "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.match);
		
		Globals.setSelectedTeam(null);
		if(Globals.getSelectedMatch().getEvents().size() == 0) {
			Globals.getSelectedMatch().start();
		} else {
			Globals.setSelectedTeam(Globals.getSelectedMatch().getOffendingTeam());
		}

		textView1 = (TextView) findViewById(R.id.textView1);
		textView3 = (TextView) findViewById(R.id.textView3);
		Button button3 = (Button) findViewById(R.id.button3);
		final Button button1 = (Button) findViewById(R.id.button1);
		
		updateScoreBoard();
		
		textView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Globals.getSelectedTeam() == null ||  Globals.getSelectedTeam().getId() == getTeam2().getId()) {
					if(Globals.getSelectedTeam() == null) {
						Globals.getSelectedMatch().pullForTeam(getTeam2());
						toastLong(getTeam2().getName() + " is pulling");
					} else {
						Globals.getSelectedMatch().turnoverForTeam(getTeam1());
					}
					Globals.setSelectedTeam(getTeam1());
					updateScoreBoard();
					reloadSpinners();
					toastShort(getTeam1().getName() + " is on offense");
				}
			}
		});
		
		textView3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Globals.getSelectedTeam() == null ||  Globals.getSelectedTeam().getId() == getTeam1().getId()) {
					if(Globals.getSelectedTeam() == null) {
						Globals.getSelectedMatch().pullForTeam(getTeam1());
						toastLong(getTeam1().getName() + " is pulling");
					} else {
						Globals.getSelectedMatch().turnoverForTeam(getTeam2());
					}
					Globals.setSelectedTeam(getTeam2());
					updateScoreBoard();
					reloadSpinners();
					toastShort(getTeam2().getName() + " is on offense");
				}
			}
		});
		

		
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("MatchActivity", "score click");
				if(spinner1.getSelectedItem() == null || spinner2.getSelectedItem() == null) {
					if(Globals.getSelectedTeam() == null) {
						toastShort("Select offending team");
					} else {
						toastShort("Select assist and scorer");
					}
				} else {
					Globals.getSelectedMatch().score((TeamPlayer)spinner2.getSelectedItem(), (TeamPlayer)spinner1.getSelectedItem());
					Globals.setSelectedTeam(null);
					updateScoreBoard();
					reloadSpinners();
				}
			}
		});
		
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("MatchActivity", "half-time click");
				Match match = Globals.getSelectedMatch();
				if(match.isAfterHalfTime()) {
					match.end();
					Globals.setSelectedTeam(null);
				} else {
					match.halfTime();
					Globals.setSelectedTeam(null);
					button1.setText(R.string.end);
				}
			}
		});
	}
	
	protected void updateScoreBoard() {
		textView1.setText(getTeam1().getName() + " " + Globals.getSelectedMatch().getTeam1Points());
		textView1.setTextColor(isTeam1Offending() ? Color.YELLOW : Color.WHITE);
		textView1.setTypeface(null, isTeam1Offending() ? Typeface.BOLD : Typeface.NORMAL);
		textView3.setText(Globals.getSelectedMatch().getTeam2Points() + " " + getTeam2().getName());
		textView3.setTextColor(isTeam2Offending() ? Color.YELLOW : Color.WHITE);
		textView3.setTypeface(null, isTeam2Offending() ? Typeface.BOLD : Typeface.NORMAL);
	}

	protected boolean isTeam1Offending() {
		return Globals.getSelectedTeam() != null && Globals.getSelectedTeam().getId() == getTeam1().getId();
	}

	protected boolean isTeam2Offending() {
		return Globals.getSelectedTeam() != null && Globals.getSelectedTeam().getId() == getTeam2().getId();
	}

	protected Team getTeam1() {
		return Globals.getSelectedMatch().getTeam1();
	}

	protected Team getTeam2() {
		return Globals.getSelectedMatch().getTeam2();
	}
	
	protected void reloadSpinner1() {
		Log.d("MatchActivity", "reloadSpinner1");
		ScorePlayerAdapter adapterForAssist = new ScorePlayerAdapter(this, android.R.layout.simple_spinner_item, getActivePlayerListForSpinner1());
		adapterForAssist.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner1.setAdapter(adapterForAssist);
	}
	
	protected void reloadSpinners() {
		Log.d("MatchActivity", "reloadSpinners");
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		reloadSpinner1();
		
		
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		ScorePlayerAdapter adapterForScore = new ScorePlayerAdapter(this, android.R.layout.simple_spinner_item, getActivePlayerListForSpinner2());
		adapterForScore.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner2.setAdapter(adapterForScore);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				reloadSpinner1();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	protected List<TeamPlayer> getActivePlayerListForSpinner1() {
		Log.d("MatchActivity", "getActivePlayerListForSpinner1");
		List<TeamPlayer> playerList = getActivePlayerListForCurrentTeam(true);
		if(spinner2 != null && spinner2.getSelectedItem() != null) {
			Long teamPlayerId = ((TeamPlayer)spinner2.getSelectedItem()).getId();
			int position = -1;
			for (int i = 0; i < playerList.size(); i++) {
				if(playerList.get(i).getId() == teamPlayerId) {
					position = i;
				}
			}
			if(position != -1) {
				playerList.remove(position);
			}
		}
		return playerList;
		
	}
	
	protected List<TeamPlayer> getActivePlayerListForSpinner2() {
		Log.d("MatchActivity", "getActivePlayerListForSpinner2");
		return getActivePlayerListForCurrentTeam(false);
	}
	
	protected List<TeamPlayer> getActivePlayerListForCurrentTeam(boolean allowNull) {
		Log.d("MatchActivity", "getActivePlayerListForCurrentTeam");
		List<TeamPlayer> playerList = new ArrayList<TeamPlayer>();
		if(Globals.getSelectedTeam() == null) {
			return playerList;
		}
		playerList = Globals.getSelectedTeam().getPlayers();
		if(allowNull) {
			TeamPlayer nullPlayer = new TeamPlayer();
			nullPlayer.setId(0L);
			playerList.add(nullPlayer);
		}
		return playerList;
	}

	@Override
	protected void onResume() {
		super.onResume();
		reloadSpinners();
	}
	
	

}