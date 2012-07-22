package com.bntx.tournament.activity;

import java.util.ArrayList;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.activity.list.TeamListActivity;
import com.bntx.tournament.activity.list.TeamListForTournamentActivity;
import com.bntx.tournament.activity.list.TournamentListActivity;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.Tournament;
import com.bntx.tournament.row.TournamentTeam;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class TournamentActivity extends Activity {
	
	protected DatabaseHandler db;
	
	protected ArrayAdapter<String> adapter;
	protected ListView listView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.d("TournamentActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament);
        
        Log.d("TournamentActivity, intent = ", this.getIntent().toString());
        
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(Globals.getSelectedTournament().getName());
        
        db = new DatabaseHandler(this);
        
        Log.d("TournamentActivity", Globals.getSelectedTournament().getName() + " has " + Integer.toString(getTeamList().size()) + " teams");
        
        listView = (ListView) findViewById(R.id.listView1);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTeamList());
        listView.setAdapter(adapter);
        

        Button addTeamButton = (Button) findViewById(R.id.button1);
        addTeamButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("TournamentActivity", "click");
				startActivity(new Intent(TournamentActivity.this, TeamListForTournamentActivity.class));
			}
		});
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("TournamentActivity", "item long click");

				final TournamentTeam tournamentTeam = db.getTournamentTeamById(TournamentTeam
						.parseIdFromListItem(((TextView) arg1).getText()
								.toString()));
				DeleteDialog deleteDialog = new DeleteDialog(
						TournamentActivity.this, "participation of team " + tournamentTeam.getName());
				deleteDialog.setDeletable(new Deletable() {

					@Override
					public void delete() {
						db.deleteRow(tournamentTeam);
						TournamentActivity.this.onResume();
					}
				});
				deleteDialog.show();
				return false;
			}
		});
        
    }

	public ArrayList<String> getTeamList() {
    	ArrayList<String> list = new ArrayList<String>();
    	for (TournamentTeam tournamentTeam : db.getTeamsForTournament(Globals.getSelectedTournament())) {
			list.add(tournamentTeam.getIdWithName());
		}
    	return list;
    }

	@Override
	protected void onResume() {
		Log.d("TournamentActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTeamList());
        listView.setAdapter(adapter);
		
		
	}
    
}