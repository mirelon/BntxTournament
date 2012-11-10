package com.bntx.tournament.activity;

import com.bntx.tournament.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsActivity extends Activity {
	
	public static final String ALLOW_HALFTIME = "allowHalftime";
	
	protected SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		CheckBox allowHalfTime = (CheckBox) findViewById(R.id.checkBox1);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		allowHalfTime.setChecked(preferences.getBoolean(ALLOW_HALFTIME, false));
		
		allowHalfTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(ALLOW_HALFTIME, isChecked); // value to store
				editor.commit();
			}
		});
	}

}
