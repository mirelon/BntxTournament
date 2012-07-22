package com.bntx.tournament.activity;

import java.util.ArrayList;
import java.util.List;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.R;
import com.bntx.tournament.R.id;
import com.bntx.tournament.R.layout;
import com.bntx.tournament.row.Tournament;

import android.app.Activity;
import android.app.backup.RestoreObserver;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddTournamentActivity extends Activity {
	
	protected DatabaseHandler db;
	
	private EditText editText1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AddTournamentActivity", "onCreate");
        setContentView(R.layout.add_tournament);
        db = new DatabaseHandler(this);
        
        editText1 = (EditText) findViewById(R.id.editText1);
        
        Button addTournamentButton = (Button) findViewById(R.id.button1);
        addTournamentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String tournament_name = editText1.getText().toString();
				Log.d("New tournament name:", tournament_name);
				Tournament tournament = new Tournament();
				tournament.setName(tournament_name);
				Log.d("Tournament name saved: ", tournament.getName());
				db.addRow(tournament);
				AddTournamentActivity.this.finish();
			}
		});
        
    }
}