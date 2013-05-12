package com.richitec.imeeting.talkinggroup;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.richitec.commontoolkit.CTApplication;
import com.richitec.commontoolkit.addressbook.AddressBookManager;
import com.richitec.commontoolkit.customadapter.CTListAdapter;
import com.richitec.commontoolkit.customcomponent.BarButtonItem.BarButtonItemStyle;
import com.richitec.commontoolkit.utils.HttpUtils;
import com.richitec.commontoolkit.utils.HttpUtils.HttpRequestType;
import com.richitec.commontoolkit.utils.HttpUtils.OnHttpRequestListener;
import com.richitec.commontoolkit.utils.HttpUtils.PostRequestFormat;
import com.richitec.commontoolkit.utils.JSONUtils;
import com.richitec.imeeting.R;
import com.richitec.imeeting.assistant.SettingActivity;
import com.richitec.imeeting.contactselect.ContactSelectActivity;
import com.richitec.imeeting.contactselect.ContactSelectActivity.TalkingGroupStatus;
import com.richitec.imeeting.customcomponent.IMeetingBarButtonItem;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class TalkingGroupHistoryListActivity extends IMeetingNavigationActivity {

	private static final String LOG_TAG = "TalkingGroupHistoryListActivity";

	// my talking group history list need to refresh
	public static boolean TALKINGGROUP_HISTORYLIST_NEED2REFRESH = false;

	// my group history list item adapter data keys
	private final String GROUP_ID = "group_id";
	private final String GROUP_CREATEDTIME = "group_createdTime";
	private final String GROUP_ATTENDEESPHONES = "group_attendeesPhones";

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
		if (TALKINGGROUP_HISTORYLIST_NEED2REFRESH) {
			// clear the refresh flag
			TALKINGGROUP_HISTORYLIST_NEED2REFRESH = false;

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
				R.menu.talking_group_history_list_activity_menu, menu);
		return true;
	}

	// generate my talking group history list adapter data list
	private List<Map<String, ?>> generateMyTalkingGroupHistoryListDataList(
			JSONArray talkingGroupHistoryListInfo) {
		// talking group created time data format, format unix timeStamp
		final DateFormat _talkingGroupCreatedTimeDataFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm", Locale.getDefault());

		// my talking group history list view data list
		List<Map<String, ?>> _dataList = new ArrayList<Map<String, ?>>();

		// generate data
		for (int i = 0; i < talkingGroupHistoryListInfo.length(); i++) {
			// get group info json object
			JSONObject _groupInfoJsonObject = JSONUtils
					.getJSONObjectFromJSONArray(talkingGroupHistoryListInfo, i);

			// get talking group id and status
			String _talkingGroupId = JSONUtils
					.getStringFromJSONObject(
							_groupInfoJsonObject,
							getResources()
									.getString(
											R.string.rbgServer_myTalkingGroupHistoryList_listConfId));
			String _talkingGroupStatus = JSONUtils
					.getStringFromJSONObject(
							_groupInfoJsonObject,
							getResources()
									.getString(
											R.string.rbgServer_myTalkingGroupHistoryList_listStatus));

			// define each talking group data map
			Map<String, Object> _dataMap = new HashMap<String, Object>();

			// set data
			_dataMap.put(
					GROUP_ID,
					getResources()
							.getString(
									R.string.rbgServer_myTalkingGroupHistoryList_listStatus_open)
							.equalsIgnoreCase(_talkingGroupStatus) ? new SpannableString(
							_talkingGroupId) : _talkingGroupId);
			_dataMap.put(
					GROUP_CREATEDTIME,
					_talkingGroupCreatedTimeDataFormat.format(1000 * JSONUtils
							.getLongFromJSONObject(
									_groupInfoJsonObject,
									getResources()
											.getString(
													R.string.rbgServer_myTalkingGroupHistoryList_listCreatedTime))));
			_dataMap.put(
					GROUP_ATTENDEESPHONES,
					JSONUtils
							.getJSONArrayFromJSONObject(
									_groupInfoJsonObject,
									getResources()
											.getString(
													R.string.rbgServer_myTalkingGroupHistoryList_listAttendees)));

			// add to data list
			_dataList.add(_dataMap);
		}

		return _dataList;
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

	// talking group history list item adapter
	class TalkingGroupHistoryListItemAdapter extends CTListAdapter {

		private static final String LOG_TAG = "MyGroupHistoryListItemAdapter";

		public TalkingGroupHistoryListItemAdapter(Context context,
				List<Map<String, ?>> data, int itemsLayoutResId,
				String[] dataKeys, int[] itemsComponentResIds) {
			super(context, data, itemsLayoutResId, dataKeys,
					itemsComponentResIds);
		}

		@Override
		protected void bindView(View view, Map<String, ?> dataMap,
				String dataKey) {
			// get item data object
			Object _itemData = dataMap.get(dataKey);

			// check view type
			// textView
			if (view instanceof TextView) {
				// generate view text
				SpannableString _viewNewText = new SpannableString(
						(null == ((TextView) view).getHint() ? ""
								: ((TextView) view).getHint())
								+ (null == _itemData ? ""
										: _itemData.toString()));
				// check data class name
				if (_itemData instanceof SpannableString) {
					_viewNewText.setSpan(
							new ForegroundColorSpan(CTApplication.getContext()
									.getResources()
									.getColor(R.color.dark_seagreen)), 0,
							_viewNewText.length(),
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}

				// set view text
				((TextView) view).setText(_viewNewText);
			}
			// tableRow
			else if (view instanceof TableRow) {
				try {
					// define item data json array and convert item data to json
					// array
					JSONArray _itemDataJSONArray = (JSONArray) _itemData;

					// process each table row item
					for (int i = 0; i < ((TableRow) view)
							.getVirtualChildCount(); i++) {
						// get table row item
						View _tableRowItem = ((TableRow) view)
								.getVirtualChildAt(i);

						// check visible view
						if (i < _itemDataJSONArray.length()) {
							// shown table row item
							_tableRowItem.setVisibility(View.VISIBLE);

							// check table row item type
							// linearLayout
							if (_tableRowItem instanceof RelativeLayout) {
								// get address book manager reference
								AddressBookManager _addressBookManager = AddressBookManager
										.getInstance();

								// get attendee phone
								String _attendeePhone = JSONUtils
										.getStringFromJSONArray(
												_itemDataJSONArray, i);

								// set attendee name
								((TextView) ((RelativeLayout) _tableRowItem)
										.findViewById(R.id.attendee_name_textView))
										.setText(_addressBookManager
												.getContactsDisplayNamesByPhone(
														_attendeePhone).get(0));

								// set attendee photo
								// define contact photo bitmap
								Bitmap _contactPhotoBitmap = ((BitmapDrawable) _mLayoutInflater
										.getContext()
										.getResources()
										.getDrawable(
												R.drawable.img_default_avatar))
										.getBitmap();

								// get contact photo
								byte[] _contactPhoto = _addressBookManager
										.getContactsPhotosByPhone(
												_attendeePhone).get(0);

								if (null != _contactPhoto) {
									try {
										// get photo data stream
										InputStream _photoDataStream = new ByteArrayInputStream(
												_contactPhoto);

										// check photo data stream
										if (null != _photoDataStream) {
											_contactPhotoBitmap = BitmapFactory
													.decodeStream(_photoDataStream);

											// close photo data stream
											_photoDataStream.close();
										}
									} catch (IOException e) {
										e.printStackTrace();

										Log.e(LOG_TAG,
												"Get contact photo data stream error, error message = "
														+ e.getMessage());
									}
								}

								// set photo
								((ImageView) ((RelativeLayout) _tableRowItem)
										.findViewById(R.id.attendee_avatar_imageView))
										.setImageBitmap(_contactPhotoBitmap);
							}
						} else {
							// hide table row item
							_tableRowItem.setVisibility(View.GONE);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();

					Log.e(LOG_TAG,
							"Convert item data to json array error, item data = "
									+ _itemData);
				}
			}
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
					.put(TalkingGroupActivity.TALKINGGROUP_ACTIVITY_PARAM_TALKINGGROUPID,
							"123123");
			List<String> _inTalkingGroupAttendeesPhone = new ArrayList<String>();
			_inTalkingGroupAttendeesPhone.add("1234");
			_inTalkingGroupAttendeesPhone.add("4567");
			_extraData
					.put(TalkingGroupActivity.TALKINGGROUP_ACTIVITY_PARAM_TALKINGGROUP_ATTENDEESPHONE,
							_inTalkingGroupAttendeesPhone);

			// go to contact(talking group prein attendee) select activity
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
			JSONObject _respEntityStringJsonObject = JSONUtils
					.toJSONObject(_respEntityString);

			// get my talking group history list info
			JSONArray _myTalkingGroupHistoryListInfo = JSONUtils
					.getJSONArrayFromJSONObject(
							_respEntityStringJsonObject,
							getResources()
									.getString(
											R.string.rbgServer_myTalkingGroupHistoryList_list));

			// reset my group history listView adapter
			((ListView) findViewById(R.id.myGroup_history_listView))
					.setAdapter(new TalkingGroupHistoryListItemAdapter(
							TalkingGroupHistoryListActivity.this,
							generateMyTalkingGroupHistoryListDataList(_myTalkingGroupHistoryListInfo),
							R.layout.talking_group_historylist_item_layout,
							new String[] { GROUP_ID, GROUP_CREATEDTIME,
									GROUP_ATTENDEESPHONES }, new int[] {
									R.id.talkingGroupId_textView,
									R.id.talkingGroup_createdTime_textView,
									R.id.talkingGroup_attendees_tableRow }));
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
