package com.bntx.tournament.activity;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.activity.list.MatchListActivity;
import com.bntx.tournament.activity.list.PlayerListActivity;
import com.bntx.tournament.activity.list.TeamListActivity;

public class BntxTournamentActivity extends Activity {
	
    private static final String IMPORT_FROM_DATABASE_NAME = "/sdcard/BntxTournament/db2.db";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Globals.setDb(new DatabaseHandler(this));

        Button teamsButton = (Button) findViewById(R.id.button2);
        teamsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("BntxTournamentActivity", "teams click");
				startActivity(new Intent(BntxTournamentActivity.this, TeamListActivity.class));
			}
		});

        Button playersButton = (Button) findViewById(R.id.button3);
        playersButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("BntxTournamentActivity", "players click");
		        Globals.setSelectedTeam(null);
				startActivity(new Intent(BntxTournamentActivity.this, PlayerListActivity.class));
			}
		});

        Button matchesButton = (Button) findViewById(R.id.button1);
        matchesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("BntxTournamentActivity", "matches click");
				startActivity(new Intent(BntxTournamentActivity.this, MatchListActivity.class));
			}
		});

        Button importButton = (Button) findViewById(R.id.button4);
        importButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("BntxTournamentActivity", "import click");
		        File file = new File(IMPORT_FROM_DATABASE_NAME);
		        if(file.exists()) {
		        	Globals.getDb().importFromDb(IMPORT_FROM_DATABASE_NAME);
		        } else {
		        	Toast.makeText(BntxTournamentActivity.this.getApplicationContext(), "Missing file " + IMPORT_FROM_DATABASE_NAME, Toast.LENGTH_SHORT).show();
		        }
			}
		});

        Button statisticsButton = (Button) findViewById(R.id.button5);
        statisticsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("BntxTournamentActivity", "statistics click");

		        String statisticsText = "Highest scorers:\n";
		        List<Object[]> highScorers = Globals.getDb().getHighScorers(15);
		        for (Object[] objects : highScorers) {
					statisticsText += objects[1] + ": " + objects[0] + "\n";
				}
		        List<Object[]> highAssists = Globals.getDb().getHighAssists(15);
		        statisticsText += "Highest assists:\n";
		        for (Object[] objects : highAssists) {
					statisticsText += objects[1] + ": " + objects[0] + "\n";
				}
		        statisticsText += Globals.getDb().getTimeStatistics();
		        AlertDialog.Builder builder = new AlertDialog.Builder(BntxTournamentActivity.this);
		        builder.setMessage(statisticsText);
		        builder.show();
			}
		});
    }
    
}