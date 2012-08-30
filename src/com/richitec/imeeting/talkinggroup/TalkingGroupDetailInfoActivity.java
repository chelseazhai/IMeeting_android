package com.richitec.imeeting.talkinggroup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.richitec.imeeting.R;

public class TalkingGroupDetailInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.talking_group_detail_info_activity_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(
				R.menu.talking_group_detail_info_activity_layout, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Log.d("cc", "cc");
	}

}
