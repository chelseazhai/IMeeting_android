package com.richitec.imeeting.account;

import android.os.Bundle;

import com.richitec.commontoolkit.activityextension.NavigationActivity;
import com.richitec.imeeting.R;

public class AccountRegisterActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.account_register_activity_step1_layout);

		// set title text
		setTitle(R.string.account_register_nav_title_text);
	}

}
