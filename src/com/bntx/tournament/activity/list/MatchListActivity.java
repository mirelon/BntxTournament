package com.bntx.tournament.activity.list;

import java.util.ArrayList;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.activity.AddMatchActivity;
import com.bntx.tournament.activity.AddTeamActivity;
import com.bntx.tournament.activity.BntxTournamentActivity;
import com.bntx.tournament.activity.MatchActivity;
import com.bntx.tournament.activity.TeamActivity;
import com.bntx.tournament.row.Match;
import com.bntx.tournament.row.Team;

import android.app.Activity;
import android.content.Intent;
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

public class MatchListActivity extends Activity {

	protected ListView listView;
	protected ArrayAdapter<String> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.match_list);

		Log.d("Match list has length: ", Integer.toString(getMatchList().size()));

		listView = (ListView) findViewById(R.id.listView1);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getMatchList());
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("MatchListActivity", "item click");
				Globals.setSelectedMatch(Match.getFromListItem(((TextView)arg1).getText().toString()));
				startActivity(new Intent(MatchListActivity.this, MatchActivity.class));
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("MatchListActivity", "item long click");

				final Match match = Match.getFromListItem(((TextView) arg1).getText()
								.toString());
				DeleteDialog deleteDialog = new DeleteDialog(
						MatchListActivity.this, "match " + match.toString());
				deleteDialog.setDeletable(new Deletable() {

					@Override
					public void delete() {
						match.delete();
						MatchListActivity.this.onResume();
					}
				});
				deleteDialog.show();
				return false;
			}
		});

		Button addMatchButton = (Button) findViewById(R.id.button1);
		addMatchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Log.d("MatchListActivity", "click");
				startActivity(new Intent(MatchListActivity.this,
						AddMatchActivity.class));
			}
		});

	}

	public ArrayList<String> getMatchList() {
		ArrayList<String> list = new ArrayList<String>();
		for (Match match : Globals.getDb().getAllMatches()) {
			list.add(match.getIdWithName());
		}
		return list;
	}

	@Override
	protected void onResume() {
		Log.d("MatchListActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getMatchList());
		listView.setAdapter(adapter);
	}

}