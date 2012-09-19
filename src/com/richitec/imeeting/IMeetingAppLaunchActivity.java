package com.richitec.imeeting;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.richitec.commontoolkit.activityextension.AppLaunchActivity;
import com.richitec.commontoolkit.addressbook.AddressBookManager;
import com.richitec.commontoolkit.user.UserBean;
import com.richitec.commontoolkit.user.UserManager;
import com.richitec.commontoolkit.utils.DataStorageUtils;
import com.richitec.imeeting.account.AccountSettingActivity;
import com.richitec.imeeting.account.AccountSettingActivity.AppAccountStatus;
import com.richitec.imeeting.talkinggroup.TalkingGroupHistoryListActivity;

public class IMeetingAppLaunchActivity extends AppLaunchActivity {

	// main activity class name storage key
	public static final String MAINACTIVITY_STORAGE_KEY = "mainActivityClass";
	// login user name storage key
	public static final String LOGIN_USERNAME_STORAGE_KEY = "loginUserName";
	// login user password storage key
	public static final String LOGIN_USERPWD_STORAGE_KEY = "loginUserPwd";
	// remember login user password storage key
	public static final String ISREMEMBER_LOGINUSERPWD_STORAGE_KEY = "isRememberLoginUserPwd";
	// login user key storage key
	public static final String LOGIN_USERKEY_STORAGE_KEY = "loginUserKey";

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
		// traversal address book
		AddressBookManager.getInstance().traversalAddressBook();

		// get login user info from storage and add to user manager
		// save user bean and add to user manager
		UserManager.getInstance().setUser(
				new UserBean(DataStorageUtils
						.getString(LOGIN_USERNAME_STORAGE_KEY),
						DataStorageUtils.getString(LOGIN_USERPWD_STORAGE_KEY),
						DataStorageUtils.getString(LOGIN_USERKEY_STORAGE_KEY)));
	}

}
