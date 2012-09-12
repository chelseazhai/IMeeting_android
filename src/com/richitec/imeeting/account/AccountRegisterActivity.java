package com.richitec.imeeting.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.richitec.commontoolkit.utils.HttpUtils;
import com.richitec.commontoolkit.utils.HttpUtils.HttpRequestType;
import com.richitec.commontoolkit.utils.HttpUtils.OnHttpRequestListener;
import com.richitec.commontoolkit.utils.HttpUtils.PostRequestFormat;
import com.richitec.commontoolkit.utils.JSONUtils;
import com.richitec.commontoolkit.utils.StringUtils;
import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class AccountRegisterActivity extends IMeetingNavigationActivity {

	private static final String LOG_TAG = "AccountRegisterActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view, account register step 1 - get register phone
		// verification code
		setContentView(R.layout.account_register_activity_layout);

		// set title text
		setTitle(R.string.account_register_nav_title_text);

		// set get register phone verification code button on click listener
		((Button) findViewById(R.id.get_verificationCode_btn))
				.setOnClickListener(new GetVerificationCodeBtnOnClickListener());

		// set verify verification code button on click listener
		((Button) findViewById(R.id.verify_verificationCode_btn))
				.setOnClickListener(new VerifyVerificationCodeBtnOnClickListener());

		// set register finish button on click listener
		((Button) findViewById(R.id.register_finish_btn))
				.setOnClickListener(new RegisterFinishBtnOnClickListener());
	}

	// process account register exception
	private void processAccountRegisterException() {
		// show account register failed toast
		Toast.makeText(AccountRegisterActivity.this,
				R.string.toast_request_exception, Toast.LENGTH_LONG).show();
	}

	// inner class
	// get register phone verification code button on click listener
	class GetVerificationCodeBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// get register phone number
			String _registerPhoneNumber = ((EditText) findViewById(R.id.registerPhone_editText))
					.getText().toString();

			// check register phone number
			if (null == _registerPhoneNumber
					|| _registerPhoneNumber.equalsIgnoreCase("")) {
				Toast.makeText(AccountRegisterActivity.this,
						R.string.toast_accountRegister_phoneNumber_null,
						Toast.LENGTH_SHORT).show();

				return;
			}

			// get register phone verification code
			// generate get register phone verification code post request param
			Map<String, String> _getRegVerificationCodeParam = new HashMap<String, String>();
			_getRegVerificationCodeParam.put(
					getResources().getString(
							R.string.bg_server_accountRegisterPhoneNumber),
					_registerPhoneNumber);

			// send get register phone verification code post http request
			HttpUtils
					.postRequest(
							getResources().getString(R.string.server_url)
									+ getResources()
											.getString(
													R.string.get_registerPhone_verification_code_url),
							PostRequestFormat.URLENCODED,
							_getRegVerificationCodeParam, null,
							HttpRequestType.ASYNCHRONOUS,
							new GetVerificationCodeHttpRequestListener());
		}

	}

	// get register phone verification code http request listener
	class GetVerificationCodeHttpRequestListener extends OnHttpRequestListener {

		@Override
		public void onFinished(HttpRequest request, HttpResponse response) {
			// get http response entity string
			String _respEntityString = HttpUtils
					.getHttpResponseEntityString(response);

			// get http response entity string json object result and userKey
			String _result = JSONUtils.getJsonString(StringUtils
					.toJsonObject(_respEntityString),
					getResources()
							.getString(R.string.bg_server_req_resp_result));

			// check result
			if (null != _result) {
				switch (Integer.parseInt(_result)) {
				case 0:
					Log.d(LOG_TAG,
							"get register phone verification code successful");

					// goto account register step 2 - verify verification code
					((LinearLayout) findViewById(R.id.account_register_step1_linearLayout))
							.setVisibility(View.GONE);
					((LinearLayout) findViewById(R.id.account_register_step2_linearLayout))
							.setVisibility(View.VISIBLE);
					break;

				case 2:
					Log.d(LOG_TAG,
							"get register phone verification code failed, register phone number is invalid");

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_accountRegister_phoneNumber_invalid,
							Toast.LENGTH_LONG).show();
					break;

				case 3:
					Log.d(LOG_TAG,
							"get register phone verification code failed, register phone number is existed");

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_accountRegister_phoneNumber_existed,
							Toast.LENGTH_LONG).show();
					break;

				default:
					Log.e(LOG_TAG,
							"get register phone verification code failed, bg_server return result is unrecognized");

					processAccountRegisterException();
					break;
				}
			} else {
				Log.e(LOG_TAG,
						"get register phone verification code failed, bg_server return result is null");

				processAccountRegisterException();
			}
		}

		@Override
		public void onFailed(HttpRequest request, HttpResponse response) {
			Log.e(LOG_TAG,
					"get register phone verification code failed, send get register phone verification code post request failed");

			processAccountRegisterException();
		}

	}

	// verify verification code button on click listener
	class VerifyVerificationCodeBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// get verification code
			String _verificationCode = ((EditText) findViewById(R.id.verificationCode_verify_editText))
					.getText().toString();

			// check verification code
			if (null == _verificationCode
					|| _verificationCode.equalsIgnoreCase("")) {
				Toast.makeText(AccountRegisterActivity.this,
						R.string.toast_verificationCode_null,
						Toast.LENGTH_SHORT).show();

				return;
			}

			// verify verification code
			// generate verify verification code post request param
			Map<String, String> _verifyVerificationCodeParam = new HashMap<String, String>();
			_verifyVerificationCodeParam.put(
					getResources().getString(
							R.string.bg_server_verificationCode),
					_verificationCode);

			// send verify verification code post http request
			HttpUtils.postRequest(
					getResources().getString(R.string.server_url)
							+ getResources().getString(
									R.string.verify_verificationCode_url),
					PostRequestFormat.URLENCODED, _verifyVerificationCodeParam,
					null, HttpRequestType.ASYNCHRONOUS,
					new VerifyVerificationCodeHttpRequestListener());
		}

	}

	// verify verification code http request listener
	class VerifyVerificationCodeHttpRequestListener extends
			OnHttpRequestListener {

		@Override
		public void onFinished(HttpRequest request, HttpResponse response) {
			// get http response entity string
			String _respEntityString = HttpUtils
					.getHttpResponseEntityString(response);

			// get http response entity string json object result and userKey
			String _result = JSONUtils.getJsonString(StringUtils
					.toJsonObject(_respEntityString),
					getResources()
							.getString(R.string.bg_server_req_resp_result));

			// check result
			if (null != _result) {
				switch (Integer.parseInt(_result)) {
				case 0:
					Log.d(LOG_TAG, "verify verification code successful");

					// goto account register step 3 - finish register
					((LinearLayout) findViewById(R.id.account_register_step2_linearLayout))
							.setVisibility(View.GONE);
					((LinearLayout) findViewById(R.id.account_register_step3_linearLayout))
							.setVisibility(View.VISIBLE);
					break;

				case 2:
					Log.d(LOG_TAG,
							"verify verification code failed, verification code is wrong");

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_verificationCode_wrong,
							Toast.LENGTH_LONG).show();
					break;

				case 6:
					Log.d(LOG_TAG,
							"verify verification code failed, verify verification code http request session timeout");

					// goto account register step 1 - get register phone
					// verification code
					((LinearLayout) findViewById(R.id.account_register_step2_linearLayout))
							.setVisibility(View.GONE);
					((LinearLayout) findViewById(R.id.account_register_step1_linearLayout))
							.setVisibility(View.VISIBLE);

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_verificationCode_timeout,
							Toast.LENGTH_LONG).show();
					break;

				default:
					Log.e(LOG_TAG,
							"verify verification code failed, bg_server return result is unrecognized");

					processAccountRegisterException();
					break;
				}
			} else {
				Log.e(LOG_TAG,
						"verify verification code failed, bg_server return result is null");

				processAccountRegisterException();
			}
		}

		@Override
		public void onFailed(HttpRequest request, HttpResponse response) {
			Log.e(LOG_TAG,
					"verify verification code failed, send verify verification code post request failed");

			processAccountRegisterException();
		}

	}

	// register finish button on click listener
	class RegisterFinishBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// get password
			String _password = ((EditText) findViewById(R.id.pwd_editText))
					.getText().toString();

			// check password
			if (null == _password || _password.equalsIgnoreCase("")) {
				Toast.makeText(AccountRegisterActivity.this,
						R.string.toast_accountRegPassword_null,
						Toast.LENGTH_SHORT).show();

				return;
			}

			// get confirmation password
			String _confirmationPwd = ((EditText) findViewById(R.id.confirmationPwd_editText))
					.getText().toString();

			// check confirmation password
			if (null == _confirmationPwd
					|| _confirmationPwd.equalsIgnoreCase("")) {
				Toast.makeText(AccountRegisterActivity.this,
						R.string.toast_accountRegConfirmationPwd_null,
						Toast.LENGTH_SHORT).show();

				return;
			}

			// check two password
			if (!_password.equalsIgnoreCase(_confirmationPwd)) {
				Toast.makeText(AccountRegisterActivity.this,
						R.string.toast_accountReg_twoPwd_notMatched,
						Toast.LENGTH_LONG).show();

				return;
			}

			// finish register
			// generate finish register post request param
			Map<String, String> _finishRegisterParam = new HashMap<String, String>();
			_finishRegisterParam.put(
					getResources()
							.getString(R.string.bg_server_userRegPassword),
					_password);
			_finishRegisterParam.put(
					getResources().getString(
							R.string.bg_server_userRegConfirmationPwd),
					_confirmationPwd);

			// send finish register post http request
			HttpUtils.postRequest(getResources().getString(R.string.server_url)
					+ getResources().getString(R.string.account_register_url),
					PostRequestFormat.URLENCODED, _finishRegisterParam, null,
					HttpRequestType.ASYNCHRONOUS,
					new RegisterFinishHttpRequestListener());
		}

	}

	// register finish http request listener
	class RegisterFinishHttpRequestListener extends OnHttpRequestListener {

		@Override
		public void onFinished(HttpRequest request, HttpResponse response) {
			// get http response entity string
			String _respEntityString = HttpUtils
					.getHttpResponseEntityString(response);

			// get http response entity string json object result and userKey
			String _result = JSONUtils.getJsonString(StringUtils
					.toJsonObject(_respEntityString),
					getResources()
							.getString(R.string.bg_server_req_resp_result));

			// check result
			if (null != _result) {
				switch (Integer.parseInt(_result)) {
				case 0:
					Log.d(LOG_TAG, "register finish successful");

					// pop account setting activity
					popActivity();

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_accountRegister_successful,
							Toast.LENGTH_LONG).show();
					break;

				case 5:
					Log.d(LOG_TAG,
							"register finish failed, two password not matched");

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_accountReg_twoPwd_notMatched,
							Toast.LENGTH_LONG).show();
					break;

				case 6:
					Log.d(LOG_TAG,
							"register finish failed, register finish http request session timeout");

					// goto account register step 1 - get register phone
					// verification code
					((LinearLayout) findViewById(R.id.account_register_step3_linearLayout))
							.setVisibility(View.GONE);
					((LinearLayout) findViewById(R.id.account_register_step1_linearLayout))
							.setVisibility(View.VISIBLE);

					Toast.makeText(AccountRegisterActivity.this,
							R.string.toast_accountRegister_timeout,
							Toast.LENGTH_LONG).show();
					break;

				default:
					Log.e(LOG_TAG,
							"register finish failed, bg_server return result is unrecognized");

					processAccountRegisterException();
					break;
				}
			} else {
				Log.e(LOG_TAG,
						"register finish failed, bg_server return result is null");

				processAccountRegisterException();
			}
		}

		@Override
		public void onFailed(HttpRequest request, HttpResponse response) {
			Log.e(LOG_TAG,
					"register finish failed, send register finish post request failed");

			processAccountRegisterException();
		}

	}

}
