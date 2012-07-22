package com.bntx.tournament.activity.list;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.R;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.activity.AddPlayerActivity;
import com.bntx.tournament.row.Player;
import com.bntx.tournament.row.Team;

public class PlayerListActivity extends Activity {
	
	protected DatabaseHandler db;
	
	protected ListView listView;
	protected ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_list);
        
        
        db = new DatabaseHandler(this);
        
        Log.d("Player list has length: ", Integer.toString(getPlayerList().size()));
        
        listView = (ListView) findViewById(R.id.listView1);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getPlayerList());
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("PlayerListActivity", "item long click");

				final Player player = db.getPlayerById(Player
						.parseIdFromListItem(((TextView) arg1).getText()
								.toString()));
				DeleteDialog deleteDialog = new DeleteDialog(
						PlayerListActivity.this, "player " + player.getName());
				deleteDialog.setDeletable(new Deletable() {

					@Override
					public void delete() {
						db.deleteRow(player);
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
    	ArrayList<String> list = new ArrayList<String>();
    	for (Player player : db.getAllPlayers()) {
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