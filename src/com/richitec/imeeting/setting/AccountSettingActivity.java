package com.richitec.imeeting.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.richitec.imeeting.R;

public class AccountSettingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_setting_activity_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.account_setting_activity_layout, menu);
		return true;
	}
}
