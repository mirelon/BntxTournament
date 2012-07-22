package com.bntx.tournament.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bntx.tournament.DatabaseHandler;
import com.bntx.tournament.R;
import com.bntx.tournament.row.Player;

public class AddPlayerActivity extends Activity {
	
	protected DatabaseHandler db;
	
	private EditText editText1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AddPlayerActivity", "onCreate");
        setContentView(R.layout.add_player);
        db = new DatabaseHandler(this);
        
        editText1 = (EditText) findViewById(R.id.editText1);
        
        Button addPlayerButton = (Button) findViewById(R.id.button1);
        addPlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String player_name = editText1.getText().toString();
				Log.d("New player name:", player_name);
				Player player = new Player();
				player.setName(player_name);
				Log.d("Player name saved: ", player.getName());
				db.addRow(player);
				AddPlayerActivity.this.finish();
			}
		});
        
    }
}