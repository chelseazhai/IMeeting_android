package com.richitec.imeeting.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.richitec.commontoolkit.customcomponent.BarButtonItem.BarButtonItemStyle;
import com.richitec.commontoolkit.user.UserBean;
import com.richitec.commontoolkit.user.UserManager;
import com.richitec.commontoolkit.utils.DataStorageUtils;
import com.richitec.commontoolkit.utils.HttpUtils;
import com.richitec.commontoolkit.utils.HttpUtils.HttpRequestType;
import com.richitec.commontoolkit.utils.HttpUtils.OnHttpRequestListener;
import com.richitec.commontoolkit.utils.HttpUtils.PostRequestFormat;
import com.richitec.commontoolkit.utils.JSONUtils;
import com.richitec.commontoolkit.utils.StringUtils;
import com.richitec.imeeting.IMeetingAppLaunchActivity;
import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingBarButtonItem;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;
import com.richitec.imeeting.talkinggroup.TalkingGroupHistoryListActivity;

public class AccountSettingActivity extends IMeetingNavigationActivity {

	private static final String LOG_TAG = "AccountSettingActivity";

	// account setting activity onCreate param key
	public static final String ACCOUNT_SETTING_ACTIVITY_PARAM_KEY = "application account status";

	// current application account status, default value is reset
	private AppAccountStatus _mCurrentAppAccountStatus = AppAccountStatus.RESET;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get the intent parameter data
		Bundle _data = getIntent().getExtras();

		// check the data bundle
		if (null != _data) {
			_mCurrentAppAccountStatus = (AppAccountStatus) _data
					.get(ACCOUNT_SETTING_ACTIVITY_PARAM_KEY);
		}

		// set content view
		setContentView(R.layout.account_setting_activity_layout);

		// set title text
		setTitle(R.string.account_setting_nav_title_text);

		// set user register bar button item as self activity right bar button
		// item
		setRightBarButtonItem(new IMeetingBarButtonItem(this,
				BarButtonItemStyle.RIGHT_GO, R.string.register_nav_btn_title,
				new RigisterBtnOnClickListener()));

		// set user login confirm button on click listener
		((Button) findViewById(R.id.login_confirm_btn))
				.setOnClickListener(new LoginConfirmBtnOnClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.account_setting_activity_layout, menu);
		return true;
	}

	// inner class
	// application account status
	public static enum AppAccountStatus {
		ESTABLISHING, RESET
	}

	// user register button on click listener
	class RigisterBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to account register activity
			pushActivity(AccountRegisterActivity.class);
		}

	}

	// user login confirm button on click listener
	class LoginConfirmBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// get login user name
			String _loginUserName = ((EditText) findViewById(R.id.login_name_editText))
					.getText().toString();
			// check login user name
			if (null == _loginUserName || _loginUserName.equalsIgnoreCase("")) {
				Toast.makeText(AccountSettingActivity.this,
						R.string.toast_login_userName_null, Toast.LENGTH_SHORT)
						.show();

				return;
			}

			// get login password
			String _loginPassword = ((EditText) findViewById(R.id.login_pwd_editText))
					.getText().toString();
			// check login password
			if (null == _loginPassword || _loginPassword.equalsIgnoreCase("")) {
				Toast.makeText(AccountSettingActivity.this,
						R.string.toast_login_password_null, Toast.LENGTH_SHORT)
						.show();

				return;
			}

			// login confirm
			// generate user login post request param
			Map<String, String> _loginParam = new HashMap<String, String>();
			_loginParam.put(
					getResources().getString(R.string.bg_server_userLoginName),
					_loginUserName);
			_loginParam.put(
					getResources().getString(R.string.bg_server_userLoginPwd),
					StringUtils.md5(_loginPassword));

			// send user login post http request
			HttpUtils.postRequest(
					getResources().getString(R.string.server_url)
							+ getResources().getString(
									R.string.user_login7account_setting_url),
					PostRequestFormat.URLENCODED, _loginParam, null,
					HttpRequestType.ASYNCHRONOUS,
					new UserLoginHttpRequestListener());
		}

	}

	// user login confirm http request listener
	class UserLoginHttpRequestListener extends OnHttpRequestListener {

		@Override
		public void onFinished(HttpRequest request, HttpResponse response) {
			// get http response entity string
			String _respEntityString = HttpUtils
					.getHttpResponseEntityString(response);

			// get http response entity string json object result and userKey
			String _result = JSONUtils.getJsonString(StringUtils
					.convert2Json(_respEntityString),
					getResources()
							.getString(R.string.bg_server_req_resp_result));

			// check result
			if (null != _result) {
				switch (Integer.parseInt(_result)) {
				case 0:
					// save user bean and add to user manager
					UserManager
							.getInstance()
							.setUser(
									new UserBean(
											((EditText) findViewById(R.id.login_name_editText))
													.getText().toString(),
											((EditText) findViewById(R.id.login_pwd_editText))
													.getText().toString(),
											JSONUtils.getJsonString(
													StringUtils
															.convert2Json(_respEntityString),
													getResources()
															.getString(
																	R.string.bg_server_loginReq_resp_userkey))));

					// check goto activity
					if (AppAccountStatus.ESTABLISHING == _mCurrentAppAccountStatus) {
						Log.d(LOG_TAG, "login successful");

						// update main activity class name from storage
						DataStorageUtils
								.putObject(
										IMeetingAppLaunchActivity.MAINACTIVITY_STORAGE_KEY,
										TalkingGroupHistoryListActivity.class
												.getName());

						// go to talking group history list activity
						finish();
						startActivity(new Intent(AccountSettingActivity.this,
								TalkingGroupHistoryListActivity.class));

						Toast.makeText(AccountSettingActivity.this,
								R.string.toast_login_successful,
								Toast.LENGTH_LONG).show();
					} else {
						Log.d(LOG_TAG, "user account reset successful");

						// pop to setting activity
						popActivity();

						Toast.makeText(AccountSettingActivity.this,
								R.string.toast_userAccount_reset_successful,
								Toast.LENGTH_SHORT).show();
					}
					break;

				case 1:
					Log.d(LOG_TAG,
							"login failed, user name or password is wrong");

					Toast.makeText(AccountSettingActivity.this,
							R.string.toast_login_userName6Pwd_wrong,
							Toast.LENGTH_SHORT).show();
					break;

				default:
					Log.e(LOG_TAG,
							"login failed, bg_server return result is unrecognized");

					processLoginException();
					break;
				}
			} else {
				Log.e(LOG_TAG, "login failed, bg_server return result is null");

				processLoginException();
			}
		}

		@Override
		public void onFailed(HttpRequest request, HttpResponse response) {
			Log.e(LOG_TAG, "login failed, send user login post request failed");

			processLoginException();
		}

		// process login exception
		private void processLoginException() {
			// show login failed toast
			Toast.makeText(AccountSettingActivity.this,
					R.string.toast_request_exception, Toast.LENGTH_LONG).show();
		}

	}

}
