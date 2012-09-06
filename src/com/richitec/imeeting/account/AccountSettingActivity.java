package com.richitec.imeeting.account;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.richitec.commontoolkit.customui.BarButtonItem.BarButtonItemStyle;
import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingBarButtonItem;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class AccountSettingActivity extends IMeetingNavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.account_setting_activity_layout);

		// set title text
		setTitle(R.string.account_setting_nav_title_text);

		// set user register bar button item as self activity right bar button
		// item
		setRightBarButtonItem(new IMeetingBarButtonItem(this,
				BarButtonItemStyle.RIGHT_GO, R.string.register_nav_btn_title,
				new RigisterBtnOnClickListener()));
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
