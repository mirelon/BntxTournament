package com.bntx.tournament.activity.list;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.adapter.ActivePlayerListAdapter;
import com.bntx.tournament.row.Event;
import com.bntx.tournament.row.TeamPlayer;

public class ActivePlayerListActivity extends Activity {
		
	protected ListView listView;
	protected ActivePlayerListAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_player_list);
        
        listView = (ListView) findViewById(R.id.listView1);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CheckedTextView ctv = (CheckedTextView)arg1;
				Log.d("Click: ", ctv.getText().toString() + ", checked = " + ctv.isChecked());
				TeamPlayer teamPlayer = TeamPlayer.getFromListItem(ctv.getText().toString());
				Event event = new Event();
				event.setTimestamp(new Timestamp(Calendar.getInstance().getTime().getTime()));
				/**
				 * itemClickEvent is called before the checked state changes
				 */
				if(ctv.isChecked()) {
					event.setCode(Event.CODE_PLAYER_DOWN);
				} else {
					event.setCode(Event.CODE_PLAYER_UP);
				}
				event.setMatchId(Globals.getSelectedMatch().getId());
				event.setTargetId(teamPlayer.getId());
				event.save();
				adapter.setActivePlayerList(getActivePlayers());
				adapter.notifyDataSetChanged();
			}
		});
        
    }

    public List<TeamPlayer> getPlayers() {
    	return Globals.getDb().getPlayersForTeam(Globals.getSelectedTeam());
    }

    public List<TeamPlayer> getActivePlayers() {
    	return Globals.getSelectedMatch().getActivePlayersForTeam(Globals.getSelectedTeam());
    }

	@Override
	protected void onResume() {
		Log.d("ActivePlayerListActivity", "onResume");
		super.onResume();
		adapter = new ActivePlayerListAdapter(this, android.R.layout.simple_list_item_multiple_choice, getPlayers());
		adapter.setActivePlayerList(getActivePlayers());
		listView.setAdapter(adapter);
        
	}
    
}