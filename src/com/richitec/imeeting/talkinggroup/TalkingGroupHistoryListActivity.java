package com.richitec.imeeting.talkinggroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.richitec.commontoolkit.customcomponent.BarButtonItem.BarButtonItemStyle;
import com.richitec.commontoolkit.utils.HttpUtils;
import com.richitec.commontoolkit.utils.HttpUtils.HttpRequestType;
import com.richitec.commontoolkit.utils.HttpUtils.OnHttpRequestListener;
import com.richitec.commontoolkit.utils.HttpUtils.PostRequestFormat;
import com.richitec.commontoolkit.utils.JSONUtils;
import com.richitec.commontoolkit.utils.StringUtils;
import com.richitec.imeeting.R;
import com.richitec.imeeting.assistant.SettingActivity;
import com.richitec.imeeting.contactselect.ContactSelectActivity;
import com.richitec.imeeting.contactselect.ContactSelectActivity.TalkingGroupStatus;
import com.richitec.imeeting.customcomponent.IMeetingBarButtonItem;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class TalkingGroupHistoryListActivity extends IMeetingNavigationActivity {

	private static final String LOG_TAG = "TalkingGroupHistoryListActivity";

	// my talking group history list need to refresh
	public static boolean MYTALKINGGROUP_HISTORYLIST_NEED2REFRESH = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.talking_group_history_list_activity_layout);

		// set title text
		setTitle(R.string.myTalkingGroup_history_list_nav_title_text);

		// set setting bar button item as self activity left bar button
		// item
		setLeftBarButtonItem(new IMeetingBarButtonItem(this,
				BarButtonItemStyle.RIGHT_GO, R.string.setting_nav_btn_title,
				new SettingBtnOnClickListener()));

		// send get my talking history list http post request
		HttpUtils.postSignatureRequest(
				getResources().getString(R.string.server_url)
						+ getResources().getString(
								R.string.myTalkingGroup_historyList_url),
				PostRequestFormat.URLENCODED, null, null,
				HttpRequestType.ASYNCHRONOUS,
				new GetMyTalkingGroupHistoryListHttpRequestListener());

		// bind new talking group button on click listener
		((Button) findViewById(R.id.newTalkingGroup_btn))
				.setOnClickListener(new NewTalkingGroupBtnOnClickListener());
	}

	@Override
	protected void onResume() {
		super.onResume();

		// check my talking group need to refresh
		if (MYTALKINGGROUP_HISTORYLIST_NEED2REFRESH) {
			// clear the refresh flag
			MYTALKINGGROUP_HISTORYLIST_NEED2REFRESH = false;

			Log.d(LOG_TAG, "refresh my talking group history list");
			// send get my talking history list http post request
			HttpUtils.postSignatureRequest(
					getResources().getString(R.string.server_url)
							+ getResources().getString(
									R.string.myTalkingGroup_historyList_url),
					PostRequestFormat.URLENCODED, null, null,
					HttpRequestType.ASYNCHRONOUS,
					new GetMyTalkingGroupHistoryListHttpRequestListener());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(
				R.menu.talking_group_history_list_activity_layout, menu);
		return true;
	}

	// generate my group history list item adapter
	private ListAdapter generateMyTalkingGroupHistoryListItemAdapter(
			JSONArray talkingGroupHistoryList) {
		// my group history list item adapter data keys
		final String GROUP_ID = "group_id";
		final String GROUP_CREATEDTIME = "group_createdTime";
		final String GROUP_ATTENDEESPHONES = "group_attendeesPhones";

		// data format, format unix timeStamp
		final DateFormat _dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		// my talking group history list view data list
		List<Map<String, Object>> _dataList = new ArrayList<Map<String, Object>>();

		// generate data
		for (int i = 0; i < talkingGroupHistoryList.length(); i++) {
			// get group info json object
			JSONObject _groupInfoJsonObject = JSONUtils
					.getJSONObjectFromJSONArray(talkingGroupHistoryList, i);

			// get talking group id and status
			String _talkingGroupId = JSONUtils
					.getStringFromJSONObject(
							_groupInfoJsonObject,
							getResources()
									.getString(
											R.string.bg_server_myTalkingGroupHistoryList_listConfId));
			String _talkingGroupStatus = JSONUtils
					.getStringFromJSONObject(
							_groupInfoJsonObject,
							getResources()
									.getString(
											R.string.bg_server_myTalkingGroupHistoryList_listStatus));

			// define each talking group data map
			HashMap<String, Object> _dataMap = new HashMap<String, Object>();

			// set data
			_dataMap.put(
					GROUP_ID,
					getResources()
							.getString(
									R.string.bg_server_myTalkingGroupHistoryList_listStatus_open)
							.equalsIgnoreCase(_talkingGroupStatus) ? new SpannableString(
							_talkingGroupId) : _talkingGroupId);
			_dataMap.put(
					GROUP_CREATEDTIME,
					_dataFormat.format(1000 * JSONUtils
							.getLongFromJSONObject(
									_groupInfoJsonObject,
									getResources()
											.getString(
													R.string.bg_server_myTalkingGroupHistoryList_listCreatedTime))));
			_dataMap.put(
					GROUP_ATTENDEESPHONES,
					JSONUtils
							.getJSONArrayFromJSONObject(
									_groupInfoJsonObject,
									getResources()
											.getString(
													R.string.bg_server_myTalkingGroupHistoryList_listAttendees)));

			// add to data list
			_dataList.add(_dataMap);
		}

		return new TalkingGroupHistoryListItemAdapter(this, _dataList,
				R.layout.talking_group_historylist_item_layout, new String[] {
						GROUP_ID, GROUP_CREATEDTIME, GROUP_ATTENDEESPHONES },
				new int[] { R.id.talkingGroupId_textView,
						R.id.talkingGroup_createdTime_textView,
						R.id.talkingGroup_attendees_tableRow });
	}

	// inner class
	// setting button on click listener
	class SettingBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to setting activity
			pushActivity(SettingActivity.class);
		}

	}

	// new talking group button
	class NewTalkingGroupBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// extra data, add for test by ares
			Map<String, Object> _extraData = new HashMap<String, Object>();
			_extraData
					.put(ContactSelectActivity.CONTACT_SELECT_ACTIVITY_PARAM_TALKINGGROUPSTATUS,
							TalkingGroupStatus.GOING);
			_extraData
					.put(TalkingGroupDetailInfoActivity.ACTIVITIES_PARAM_TALKINGGROUPID,
							"123123");
			List<String> _inTalkingGroupAttendeesPhone = new ArrayList<String>();
			_inTalkingGroupAttendeesPhone.add("1234");
			_inTalkingGroupAttendeesPhone.add("4567");
			_extraData
					.put(TalkingGroupDetailInfoActivity.ACTIVITIES_PARAM_TALKINGGROUP_ATTENDEESPHONE,
							_inTalkingGroupAttendeesPhone);

			// go to talking group attendee select activity
			pushActivity(ContactSelectActivity.class);
		}

	}

	// get my talking group history list http request listener
	class GetMyTalkingGroupHistoryListHttpRequestListener extends
			OnHttpRequestListener {

		@Override
		public void onFinished(HttpRequest request, HttpResponse response) {
			// get http response entity string
			String _respEntityString = HttpUtils
					.getHttpResponseEntityString(response);

			// get http response entity string json object
			JSONObject _respEntityStringJsonObject = StringUtils
					.toJSONObject(_respEntityString);

			// get my talking group history list
			JSONArray _myTalkingGroupHistoryList = JSONUtils
					.getJSONArrayFromJSONObject(
							_respEntityStringJsonObject,
							getResources()
									.getString(
											R.string.bg_server_myTalkingGroupHistoryList_list));

			// reset my group history listView adapter
			((ListView) findViewById(R.id.myGroup_history_listView))
					.setAdapter(generateMyTalkingGroupHistoryListItemAdapter(_myTalkingGroupHistoryList));
		}

		@Override
		public void onFailed(HttpRequest request, HttpResponse response) {
			Log.e(LOG_TAG,
					"get my talking group history list failed, send get my talking group history list signature post request failed");

			processGetMyTalkingGroupHistoryListException();
		}

		// process get my talking group history list exception
		private void processGetMyTalkingGroupHistoryListException() {
			// show get my talking group history list failed toast
			Toast.makeText(TalkingGroupHistoryListActivity.this,
					R.string.toast_request_exception, Toast.LENGTH_LONG).show();
		}

	}

}
