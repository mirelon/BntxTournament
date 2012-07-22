package com.bntx.tournament.activity;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.R;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.row.Team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddTeamActivity extends Activity {
	
	protected DatabaseHandler db;
	
	private EditText editText1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AddTournamentActivity", "onCreate");
        setContentView(R.layout.add_team);
        db = new DatabaseHandler(this);
        
        editText1 = (EditText) findViewById(R.id.editText1);
        
        Button addTeamButton = (Button) findViewById(R.id.button1);
        addTeamButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String team_name = editText1.getText().toString();
				Log.d("New team name:", team_name);
				Team team = new Team();
				team.setName(team_name);
				Log.d("Team name saved: ", team.getName());
				db.addRow(team);
				AddTeamActivity.this.finish();
			}
		});
        
    }
}