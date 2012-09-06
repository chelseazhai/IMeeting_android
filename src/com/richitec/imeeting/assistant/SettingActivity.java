package com.richitec.imeeting.assistant;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.richitec.imeeting.R;
import com.richitec.imeeting.account.AccountSettingActivity;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class SettingActivity extends IMeetingNavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.setting_activity_layout);

		// set title text
		setTitle(R.string.setting_nav_title_text);

		// bind account setting button on click listener
		((Button) findViewById(R.id.accountSetting_btn))
				.setOnClickListener(new AccountSettingBtnOnClickListener());

		// bind help button on click listener
		((Button) findViewById(R.id.help_btn))
				.setOnClickListener(new HelpBtnOnClickListener());

		// bind about button on click listener
		((Button) findViewById(R.id.about_btn))
				.setOnClickListener(new AboutBtnOnClickListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.setting_activity_layout, menu);
		return true;
	}

	// inner class
	// account setting button on click listener
	class AccountSettingBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to user account setting activity
			pushActivity(AccountSettingActivity.class);
		}

	}

	// help button on click listener
	class HelpBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to help activity
			pushActivity(SupportActivity.class);
		}

	}

	// about button on click listener
	class AboutBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to about activity
			pushActivity(AboutActivity.class);
		}

	}

}
