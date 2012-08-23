package com.richitec.imeeting.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.richitec.imeeting.R;

public class SettingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.setting_activity_layout, menu);
		return true;
	}
}
