package com.richitec.imeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.richitec.imeeting.setting.AccountSettingActivity;

public class AppRootActivity extends Activity {

	// delay 3 seconds
	private final int SPLASH_DISPLAY_TIME = 3000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// loading splash image
		setContentView(R.layout.app_root_activity_layout);

		// process application loading
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// go to user account setting activity and finish application
				// root activity
				Intent _intent = new Intent(AppRootActivity.this,
						AccountSettingActivity.class);
				startActivity(_intent);

				finish();
			}
		}, SPLASH_DISPLAY_TIME);
	}

}
