package com.richitec.imeeting;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.richitec.commontoolkit.activityextension.AppLaunchActivity;
import com.richitec.commontoolkit.utils.DataStorageUtils;
import com.richitec.imeeting.account.AccountSettingActivity;
import com.richitec.imeeting.account.AccountSettingActivity.AppAccountStatus;
import com.richitec.imeeting.talkinggroup.TalkingGroupHistoryListActivity;

public class IMeetingAppLaunchActivity extends AppLaunchActivity {

	// main activity class name storage key
	public static final String MAINACTIVITY_STORAGE_KEY = "mainActivityClass";

	@Override
	public Drawable splashImg() {
		return getResources().getDrawable(R.drawable.ic_splash);
	}

	@Override
	public Intent intentActivity() {
		// init return intent and set default value - go to account setting
		// activity
		Intent _ret = new Intent(this, AccountSettingActivity.class);
		// put extra data
		_ret.putExtra(
				AccountSettingActivity.ACCOUNT_SETTING_ACTIVITY_PARAM_KEY,
				AppAccountStatus.ESTABLISHING);

		// get main activity class name from storage
		String _mainActivityClass = DataStorageUtils
				.getString(MAINACTIVITY_STORAGE_KEY);

		// check main activity class name
		if (TalkingGroupHistoryListActivity.class.getName().equalsIgnoreCase(
				_mainActivityClass)) {
			_ret = new Intent(this, TalkingGroupHistoryListActivity.class);
		}

		return _ret;
	}

	@Override
	public void didFinishLaunching() {
		// sleep 3 seconds
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
