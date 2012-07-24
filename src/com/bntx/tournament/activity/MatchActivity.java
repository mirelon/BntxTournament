package com.bntx.tournament.activity;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.activity.list.ActivePlayerListActivity;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.TeamPlayer;

public class MatchActivity extends Activity {

	protected TextView textView1;
	protected TextView textView3;

	protected Drawable textView1Background;
	protected Drawable textView3Background;

	protected Spinner spinner1;
	protected Spinner spinner2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("MatchActivity", "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.match);

		textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(getTeam1().getName());
		textView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("MatchActivity", "offense: " + getTeam1().getName());
				textView1.setTypeface(null, Typeface.BOLD);
				textView3.setTypeface(null, Typeface.NORMAL);
				textView1.setTextColor(Color.YELLOW);
				textView3.setTextColor(Color.WHITE);
				Globals.getSelectedMatch().turnoverForTeam(getTeam1());
				Globals.setSelectedTeam(getTeam1());
			}
		});
		textView3 = (TextView) findViewById(R.id.textView3);
		textView3.setText(getTeam2().getName());
		textView3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("MatchActivity", "offense: " + getTeam2().getName());
				textView1.setTypeface(null, Typeface.NORMAL);
				textView3.setTypeface(null, Typeface.BOLD);
				textView1.setTextColor(Color.WHITE);
				textView3.setTextColor(Color.YELLOW);
				Globals.getSelectedMatch().turnoverForTeam(getTeam2());
				Globals.setSelectedTeam(getTeam2());
			}
		});
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setText(getTeam1().getName() + " active players");
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("MatchActivity", "team1 click");
				Globals.setSelectedTeam(getTeam1());
				startActivity(new Intent(MatchActivity.this,
						ActivePlayerListActivity.class));
			}
		});
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setText(getTeam2().getName() + " active players");
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("MatchActivity", "team2 click");
				Globals.setSelectedTeam(getTeam2());
				startActivity(new Intent(MatchActivity.this,
						ActivePlayerListActivity.class));
			}
		});
	}

	protected Team getTeam1() {
		return Globals.getSelectedMatch().getTeam1();
	}

	protected Team getTeam2() {
		return Globals.getSelectedMatch().getTeam2();
	}
	
	protected void reloadSpinners() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapterForTeam1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getActivePlayerListForTeam1());
		spinner1.setAdapter(adapterForTeam1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> adapterForTeam2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getActivePlayerListForTeam2());
		spinner1.setAdapter(adapterForTeam2);
	}

	protected ArrayList<String> getActivePlayerListForTeam1() {
		ArrayList<String> playerList = new ArrayList<String>();
		for (TeamPlayer teamPlayer : Globals.getSelectedMatch().getActivePlayersForTeam(getTeam1())) {
			playerList.add(teamPlayer.getIdWithName());
		}
		return playerList;
	}

	protected ArrayList<String> getActivePlayerListForTeam2() {
		ArrayList<String> playerList = new ArrayList<String>();
		for (TeamPlayer teamPlayer : Globals.getSelectedMatch().getActivePlayersForTeam(getTeam2())) {
			playerList.add(teamPlayer.getIdWithName());
		}
		return playerList;
	}

	@Override
	protected void onResume() {
		super.onResume();
		reloadSpinners();
	}
	
	

}