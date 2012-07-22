package com.bntx.tournament.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.activity.list.PlayerListActivity;
import com.bntx.tournament.row.TeamPlayer;

public class TeamActivity extends Activity {
	
	protected DatabaseHandler db;
	
	protected ArrayAdapter<String> adapter;
	protected ListView listView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.d("TeamActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        
        Log.d("TeamActivity, intent = ", this.getIntent().toString());
        
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(Globals.getSelectedTeam().getName());
        
        db = new DatabaseHandler(this);
        
        Log.d("TeamActivity", Globals.getSelectedTeam().getName() + " has " + Integer.toString(getPlayerList().size()) + " players");
        
        listView = (ListView) findViewById(R.id.listView1);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getPlayerList());
        listView.setAdapter(adapter);
        

        Button addPlayerButton = (Button) findViewById(R.id.button1);
        addPlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("TeamActivity", "click");
				startActivity(new Intent(TeamActivity.this, PlayerListActivity.class));
			}
		});
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("TeamActivity", "item long click");

				final TeamPlayer teamPlayer = db.getTeamPlayerById(TeamPlayer
						.parseIdFromListItem(((TextView) arg1).getText()
								.toString()));
				DeleteDialog deleteDialog = new DeleteDialog(
						TeamActivity.this, "participation of player " + teamPlayer.getName() + " in team " + Globals.getSelectedTeam().getName() + "?");
				deleteDialog.setDeletable(new Deletable() {

					@Override
					public void delete() {
						db.deleteRow(teamPlayer);
						TeamActivity.this.onResume();
					}
				});
				deleteDialog.show();
				return false;
			}
		});
        
    }

	public ArrayList<String> getPlayerList() {
    	ArrayList<String> list = new ArrayList<String>();
    	for (TeamPlayer teamPlayer : db.getPlayersForTeam(Globals.getSelectedTeam())) {
			list.add(teamPlayer.getIdWithName());
		}
    	return list;
    }

	@Override
	protected void onResume() {
		Log.d("TeamActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getPlayerList());
        listView.setAdapter(adapter);
	}
    
}