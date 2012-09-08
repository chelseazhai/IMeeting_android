package com.richitec.imeeting;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.richitec.commontoolkit.activityextension.AppLaunchActivity;
import com.richitec.imeeting.talkinggroup.TalkingGroupHistoryListActivity;

public class IMeetingAppLaunchActivity extends AppLaunchActivity {

	@Override
	public Drawable splashImg() {
		return getResources().getDrawable(R.drawable.ic_splash);
	}

	@Override
	public Intent intentActivity() {
		return new Intent(this, TalkingGroupHistoryListActivity.class);
	}

	@Override
	public void didFinishLaunching() {
		// sleep 5 seconds
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
