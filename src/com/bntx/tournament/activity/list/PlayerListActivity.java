package com.bntx.tournament.activity.list;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.activity.AddPlayerActivity;
import com.bntx.tournament.row.Player;
import com.bntx.tournament.row.Team;
import com.bntx.tournament.row.TeamPlayer;

public class PlayerListActivity extends Activity {
		
	protected ListView listView;
	protected ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_list);
        
        Log.d("Player list has length: ", Integer.toString(getPlayerList().size()));
        
        listView = (ListView) findViewById(R.id.listView1);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getPlayerList());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(Globals.getSelectedTeam() != null) {
					final Player player = Player.getFromListItem(((TextView) arg1).getText().toString());
					TeamPlayer teamPlayer = new TeamPlayer();
					teamPlayer.setPlayerId(player.getId());
					teamPlayer.setTeamId(Globals.getSelectedTeam().getId());
					teamPlayer.save();
					PlayerListActivity.this.finish();
				}
			}
		});
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("PlayerListActivity", "item long click");
				
				final Player player = Player.getFromListItem(((TextView) arg1).getText().toString());
				DeleteDialog deleteDialog = new DeleteDialog(
						PlayerListActivity.this, "player " + player.getName());
				deleteDialog.setDeletable(new Deletable() {

					@Override
					public void delete() {
						Globals.getDb().deleteRow(player);
						PlayerListActivity.this.onResume();
					}
				});
				deleteDialog.show();

				return false;
			}
		});

        Button addPlayerButton = (Button) findViewById(R.id.button1);
        addPlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("PlayerListActivity", "click");
				startActivity(new Intent(PlayerListActivity.this, AddPlayerActivity.class));
			}
		});
        
    }
    
    public ArrayList<String> getPlayerList() {
    	List<Player> playerList;
    	if(Globals.getSelectedTeam() == null) {
    		playerList = Globals.getDb().getAllPlayers();
    	} else {
    		playerList = Globals.getDb().getPlayersNotInTeam(Globals.getSelectedTeam());
    	}
    	ArrayList<String> list = new ArrayList<String>();
    	for (Player player : playerList) {
			list.add(player.getIdWithName());
		}
    	return list;
    }

	@Override
	protected void onResume() {
		Log.d("PlayerListActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getPlayerList());
        listView.setAdapter(adapter);
	}
    
}