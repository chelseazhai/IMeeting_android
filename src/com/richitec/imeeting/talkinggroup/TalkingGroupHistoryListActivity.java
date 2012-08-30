package com.richitec.imeeting.talkinggroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.richitec.commontoolkit.activityextension.NavigationActivity;
import com.richitec.commontoolkit.customui.BarButtonItem;
import com.richitec.imeeting.R;
import com.richitec.imeeting.assistant.SettingActivity;
import com.richitec.imeeting.contactselect.ContactSelectActivity;

public class TalkingGroupHistoryListActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.talking_group_history_list_activity_layout);

		// set title text
		setTitle(R.string.myTalkingGroup_history_list_nav_title_text);

		// init setting bar button item
		BarButtonItem _settingBarBtnItem = new BarButtonItem(this);
		// set attributes
		_settingBarBtnItem.setText(R.string.setting_nav_btn_title);
		_settingBarBtnItem.setOnClickListener(new SettingBtnOnClickListener());
		// set setting bar button item as self activity left bar button
		// item
		setLeftBarButtonItem(_settingBarBtnItem);

		// test by ares
		// test attendees phone numbers
		String[][] _testGroupPhones = new String[][] {
				{ "13770662051", "18001582338" },
				{ "18652096792", "18652970325", "13813005146", "14751800329",
						"13382794516" },
				{ "13412345432", "18976541234", "14752435262" },
				{ "13212321345", "18976541234", "14752435262", "13312345678" },
				{ "13111111111" } };

		// list view data list
		List<Map<String, Object>> _dataList = new ArrayList<Map<String, Object>>();
		// generate data
		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> _dataMap = new HashMap<String, Object>();

			// set data
			_dataMap.put("group_id", "-" + i);
			_dataMap.put("group_createdTime", "+" + i);
			_dataMap.put("group_attendees_phone", _testGroupPhones[i]);

			// add to data list
			_dataList.add(_dataMap);
		}

		// set my group history listView adapter
		((ListView) findViewById(R.id.myGroup_history_listView))
				.setAdapter(new TalkingGroupHistoryListItemAdapter(this,
						_dataList,
						R.layout.talking_group_history_list_item_layout,
						new String[] { "group_id", "group_createdTime",
								"group_attendees_phone" }, new int[] {
								R.id.talkingGroupId_textView,
								R.id.talkingGroup_createdTime_textView,
								R.id.talkingGroup_attendees_tableRow }));

		// bind new talking group button on click listener
		((Button) findViewById(R.id.newTalkingGroup_btn))
				.setOnClickListener(new NewTalkingGroupBtnOnClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(
				R.menu.talking_group_history_list_activity_layout, menu);
		return true;
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
			// go to talking group attendee select activity
			pushActivity(ContactSelectActivity.class);
		}

	}

}
