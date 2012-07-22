package com.bntx.tournament.activity;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.R;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.activity.list.PlayerListActivity;
import com.bntx.tournament.activity.list.TeamListActivity;
import com.bntx.tournament.activity.list.TournamentListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BntxTournamentActivity extends Activity {
	
	protected DatabaseHandler db;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button tournamentsButton = (Button) findViewById(R.id.button1);
        tournamentsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("BntxTournamentActivity", "tournaments click");
				startActivity(new Intent(BntxTournamentActivity.this, TournamentListActivity.class));
			}
		});

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
				startActivity(new Intent(BntxTournamentActivity.this, PlayerListActivity.class));
			}
		});
    }
    
}