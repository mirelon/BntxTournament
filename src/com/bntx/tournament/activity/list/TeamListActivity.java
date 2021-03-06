package com.bntx.tournament.activity.list;

import java.util.ArrayList;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.DeleteDialog;
import com.bntx.tournament.Globals;
import com.bntx.tournament.R;
import com.bntx.tournament.DeleteDialog.Deletable;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.activity.AddTeamActivity;
import com.bntx.tournament.activity.BntxTournamentActivity;
import com.bntx.tournament.activity.TeamActivity;
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

public class TeamListActivity extends Activity {

	protected ListView listView;
	protected ArrayAdapter<String> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_list);

		Log.d("Team list has length: ", Integer.toString(getTeamList().size()));

		listView = (ListView) findViewById(R.id.listView1);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getTeamList());
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("TeamListActivity", "item click");
				if(Globals.isAddingTeam1ForMatch()) {
					Globals.setSelectedTeam1ForMatch(Team.getFromListItem(((TextView)arg1).getText().toString()));
					Globals.setAddingTeam1ForMatch(false);
					TeamListActivity.this.finish();
				} else if(Globals.isAddingTeam2ForMatch()) {
					Globals.setSelectedTeam2ForMatch(Team.getFromListItem(((TextView)arg1).getText().toString()));
					Globals.setAddingTeam2ForMatch(false);
					TeamListActivity.this.finish();
				} else {
					Globals.setSelectedTeam(Team.getFromListItem(((TextView)arg1).getText().toString()));
					startActivity(new Intent(TeamListActivity.this, TeamActivity.class));
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("TeamListActivity", "item long click");

				final Team team = Globals.getDb().getTeamById(Team
						.parseIdFromListItem(((TextView) arg1).getText()
								.toString()));
				DeleteDialog deleteDialog = new DeleteDialog(
						TeamListActivity.this, "team " + team.getName());
				deleteDialog.setDeletable(new Deletable() {

					@Override
					public void delete() {
						Globals.getDb().deleteRow(team);
						TeamListActivity.this.onResume();
					}
				});
				deleteDialog.show();
				return false;
			}
		});

		Button addTeamButton = (Button) findViewById(R.id.button1);
		addTeamButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Log.d("TeamListActivity", "click");
				startActivity(new Intent(TeamListActivity.this,
						AddTeamActivity.class));
			}
		});

	}

	public ArrayList<String> getTeamList() {
		ArrayList<String> list = new ArrayList<String>();
		for (Team team : Globals.getDb().getAllTeams()) {
			list.add(team.getIdWithName());
		}
		return list;
	}

	@Override
	protected void onResume() {
		Log.d("TeamListActivity", "onResume");
		super.onResume();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getTeamList());
		listView.setAdapter(adapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Globals.setAddingTeam1ForMatch(false);
		Globals.setAddingTeam2ForMatch(false);
	}

}