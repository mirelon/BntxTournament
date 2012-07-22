package com.bntx.tournament.activity.list;

import java.util.ArrayList;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.activity.AddPlayerActivity;
import com.bntx.tournament.activity.AddTeamActivity;
import com.bntx.tournament.row.Player;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.TournamentTeam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TeamListForTournamentActivity extends Activity {
	
	protected DatabaseHandler db;
	
	protected ListView listView;
	protected ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_list_tournament);
        
        
        db = new DatabaseHandler(this);
        
        Log.d("Team list has length: ", Integer.toString(getTeamList().size()));
        
        listView = (ListView) findViewById(R.id.listView1);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTeamList());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/**
				 * TODO use arg2 or arg3?
				 */
				Log.d("TeamListForTournamentActivity", "item click: " + ((TextView)arg1).getText().toString());
				Team team = db.getTeamById(Team.parseIdFromListItem(((TextView)arg1).getText().toString()));
				Log.d("TeamListForTournamentActivity", "selected team: " + team);
				
				TournamentTeam tournamentTeam = new TournamentTeam();
				tournamentTeam.setTeamId(team.getId());
				tournamentTeam.setName(team.getName());
				tournamentTeam.setTournamentId(Globals.getSelectedTournament().getId());
				Log.d("New TournamentTeam: ", tournamentTeam.getName());
				db.addRow(tournamentTeam);
				TeamListForTournamentActivity.this.finish();
			}
		});
        
        
        
    }
    
    public ArrayList<String> getTeamList() {
    	ArrayList<String> list = new ArrayList<String>();
    	for (Team team : db.getAllTeams()) {
			list.add(team.getIdWithName());
		}
    	return list;
    }

	@Override
	protected void onResume() {
		Log.d("TeamListForTournamentActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTeamList());
        listView.setAdapter(adapter);
	}
    
}