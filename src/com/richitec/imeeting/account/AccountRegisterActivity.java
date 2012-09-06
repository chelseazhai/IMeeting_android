package com.richitec.imeeting.account;

import android.os.Bundle;

import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class AccountRegisterActivity extends IMeetingNavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.account_register_activity_step1_layout);

		// set title text
		setTitle(R.string.account_register_nav_title_text);
	}

}
