package com.bntx.tournament.activity.list;

import java.util.ArrayList;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.activity.AddTournamentActivity;
import com.bntx.tournament.activity.TournamentActivity;
import com.bntx.tournament.row.Tournament;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TournamentListActivity extends Activity {
	
	protected DatabaseHandler db;
	
	protected ArrayAdapter<String> adapter;
	protected ListView listView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.d("TournamentListActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_list);
        
        
        db = new DatabaseHandler(this);
        
        Log.d("Tournament list has length: ", Integer.toString(getTournamentList().size()));
        
        listView = (ListView) findViewById(R.id.listView1);
        
        
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("Tournament item click: ", "arg0=" + arg0 + ", arg1=" + arg1 + ", arg2=" + arg2 + ", arg3=" + arg3 + ", arg1.getId()=" + arg1.getId() + ", text=" + ((TextView)arg1).getText());
				/**
				 * TODO: check if to use arg2 or arg3 as tournament_id
				 */
				Globals.setSelectedTournament(db.getTournamentById(Tournament.parseIdFromListItem(((TextView)arg1).getText().toString())));
				
				startActivity(new Intent(TournamentListActivity.this, TournamentActivity.class));
			}
			
		});
        
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				Log.d("TournamentListActivity", "item long click");
				
				final Tournament tournament = db.getTournamentById(Tournament.parseIdFromListItem(((TextView)arg1).getText().toString()));
				DeleteDialog deleteDialog = new DeleteDialog(TournamentListActivity.this, "tournament " + tournament.getName());
				deleteDialog.setDeletable(new Deletable() {
					
					@Override
					public void delete() {
						db.deleteRow(tournament);
						TournamentListActivity.this.onResume();
					}
				});
				deleteDialog.show();
				return false;
				
			}
		});

        Button addTournamentButton = (Button) findViewById(R.id.button1);
        addTournamentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

		        Log.d("TournamentListActivity", "click");
				startActivity(new Intent(TournamentListActivity.this, AddTournamentActivity.class));
			}
		});
        
    }

    public ArrayList<String> getTournamentList() {
    	ArrayList<String> list = new ArrayList<String>();
    	for (Tournament tournament : db.getAllTournaments()) {
			list.add(tournament.getIdWithName());
		}
    	return list;
    }

	@Override
	protected void onResume() {
		Log.d("TournamentListActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTournamentList());
        listView.setAdapter(adapter);
		
	}
    
}