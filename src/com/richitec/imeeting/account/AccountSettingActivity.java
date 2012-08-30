package com.richitec.imeeting.account;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.richitec.commontoolkit.activityextension.NavigationActivity;
import com.richitec.commontoolkit.customui.BarButtonItem;
import com.richitec.imeeting.R;

public class AccountSettingActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.account_setting_activity_layout);

		// set title text
		setTitle(R.string.account_setting_nav_title_text);

		// init user register bar button item
		BarButtonItem _registerBarBtnItem = new BarButtonItem(this);
		// set attributes
		_registerBarBtnItem.setText(R.string.register_nav_btn_title);
		_registerBarBtnItem
				.setOnClickListener(new RigisterBtnOnClickListener());
		// set user register bar button item as self activity right bar button
		// item
		setRightBarButtonItem(_registerBarBtnItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.account_setting_activity_layout, menu);
		return true;
	}

	// inner class
	// user register button on click listener
	class RigisterBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to account register activity
			pushActivity(AccountRegisterActivity.class);
		}

	}

}
