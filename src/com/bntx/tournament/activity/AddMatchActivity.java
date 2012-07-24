package com.bntx.tournament.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.activity.list.TeamListActivity;
import com.bntx.tournament.row.Match;
import com.bntx.tournament.row.Player;

public class AddMatchActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AddMatchActivity", "onCreate");
        setContentView(R.layout.add_match);

        Button addTeam1Button = (Button) findViewById(R.id.button1);
        addTeam1Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d("AddMatchActivity", "Add team1 click");
				Globals.setAddingTeam1ForMatch(true);
				startActivity(new Intent(AddMatchActivity.this, TeamListActivity.class));
			}
		});

        Button addTeam2Button = (Button) findViewById(R.id.button2);
        addTeam2Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d("AddMatchActivity", "Add team2 click");
				Globals.setAddingTeam2ForMatch(true);
				startActivity(new Intent(AddMatchActivity.this, TeamListActivity.class));
			}
		});
        
        Button addMatchButton = (Button) findViewById(R.id.button3);
        addMatchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Match match = new Match();
				match.setTeam1Id(Globals.getSelectedTeam1ForMatch().getId());
				match.setTeam2Id(Globals.getSelectedTeam2ForMatch().getId());
				match.save();
				Log.d("AddMatchActivity", "save new match: " + match.toString());
				AddMatchActivity.this.finish();
			}
		});
        
    }

	@Override
	protected void onResume() {
		super.onResume();
		TextView textView1 = (TextView)findViewById(R.id.textView1);
		if(Globals.getSelectedTeam1ForMatch() != null) {
			textView1.setText(Globals.getSelectedTeam1ForMatch().getName());
		}
		TextView textView2 = (TextView)findViewById(R.id.textView2);
		if(Globals.getSelectedTeam2ForMatch() != null) {
			textView2.setText(Globals.getSelectedTeam2ForMatch().getName());
		}
		Button addMatchButton = (Button) findViewById(R.id.button3);
		if(Globals.getSelectedTeam1ForMatch() != null && Globals.getSelectedTeam2ForMatch() != null) {
			addMatchButton.setClickable(true);
		} else {
			addMatchButton.setClickable(false);
		}
	}

}