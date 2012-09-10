package com.richitec.imeeting.contactselect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.richitec.commontoolkit.customcomponent.BarButtonItem.BarButtonItemStyle;
import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingBarButtonItem;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;
import com.richitec.imeeting.talkinggroup.TalkingGroupDetailInfoActivity;

public class ContactSelectActivity extends IMeetingNavigationActivity {

	// current talking group status, default value is establishing
	private TalkingGroupStatus _mCurrentTalkingGroupStatus = TalkingGroupStatus.ESTABLISHING;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.contact_select_activity_layout);

		// set title text
		setTitle(R.string.contactSelect_nav_title_text);

		// check current talking group status
		// establishing
		if (TalkingGroupStatus.ESTABLISHING == _mCurrentTalkingGroupStatus) {
			// set nav back bar button item as self activity left bar button
			// item
			setLeftBarButtonItem(new IMeetingBarButtonItem(this,
					BarButtonItemStyle.LEFT_BACK, R.string.back_nav_btn_title,
					new NavBackBtnOnClickListener()));

			// set open talking group bar button item as self activity right bar
			// button item
			setRightBarButtonItem(new IMeetingBarButtonItem(this,
					BarButtonItemStyle.RIGHT_GO,
					R.string.openTalkingGroup_nav_btn_title,
					new OpenTalkingGroupBtnOnClickListener()));
		}
		// going
		else {
			// set back bar button item as self activity left bar button item
			setLeftBarButtonItem(new IMeetingBarButtonItem(this,
					BarButtonItemStyle.LEFT_BACK, R.string.back_nav_btn_title,
					new BackBtnOnClickListener()));

			// set confirm add attendee bar button item as self activity right
			// bar button item
			setRightBarButtonItem(new IMeetingBarButtonItem(this,
					BarButtonItemStyle.RIGHT_GO,
					R.string.confirmAdd_nav_btn_title,
					new ConfirmAddAttendeeBtnOnClickListener()));
		}

		// test by ares
		// test address book attendees name and phone numbers
		String[][] _testAddressBookContactsName7Phones = new String[][] {
				{ "翟绍虎", "13770662051\n18001582338" },
				{ "18001582338-999999999", "18001582338" },
				{ "Ruby", "18652096792" }, { "无名字", "14751802319" },
				{ "王星", "13813005146" }, { "胡光辉", "13381794516\n18652970329" },
				{ "18652970329", "18652970329" }, { "皇上", "8888\n6666\n4444" } };

		// list view data list
		List<Map<String, Object>> _addressBookContactsDataList = new ArrayList<Map<String, Object>>();
		// generate data
		for (int i = 0; i < _testAddressBookContactsName7Phones.length; i++) {
			HashMap<String, Object> _dataMap = new HashMap<String, Object>();

			// set data
			_dataMap.put("contact_name",
					_testAddressBookContactsName7Phones[i][0]);
			_dataMap.put("contact_phoneNumbers",
					_testAddressBookContactsName7Phones[i][1]);

			// add to data list
			_addressBookContactsDataList.add(_dataMap);
		}

		// set contacts in address book listView adapter
		((ListView) findViewById(R.id.contactInAB_listView))
				.setAdapter(new InAB6In7PreinTalkingGroupContactAdapter(this,
						_addressBookContactsDataList,
						R.layout.addressbook_contact_layout, new String[] {
								"contact_name", "contact_phoneNumbers" },
						new int[] {
								R.id.adressBook_contact_displayName_textView,
								R.id.addressBook_contact_phoneNumber_textView }));

		// test in and prein talking group attendees name
		String[] _testIn7PreinTalkingGroupContactsName7Phone = new String[] {
				"翟绍虎", "王星", "胡光辉" };

		// list view data list
		List<Map<String, Object>> _In7PreinTalkingGroupContactsDataList = new ArrayList<Map<String, Object>>();
		// generate data
		for (int i = 0; i < _testIn7PreinTalkingGroupContactsName7Phone.length; i++) {
			HashMap<String, Object> _dataMap = new HashMap<String, Object>();

			// set data
			_dataMap.put("contact_name",
					_testIn7PreinTalkingGroupContactsName7Phone[i]);

			// add to data list
			_In7PreinTalkingGroupContactsDataList.add(_dataMap);
		}

		// set contacts in address book listView adapter
		((ListView) findViewById(R.id.contactIn7PreinTalkingGroup_listView))
				.setAdapter(new InAB6In7PreinTalkingGroupContactAdapter(
						this,
						_In7PreinTalkingGroupContactsDataList,
						R.layout.in7prein_talking_group_contact_layout,
						new String[] { "contact_name" },
						new int[] { R.id.in7preinTalkingGroup_contact_displayName_textView }));
	}

	// inner class
	// talking group status
	public static enum TalkingGroupStatus {
		ESTABLISHING, GOING
	}

	// nav back button on click listener
	class NavBackBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// back to last activity
			popActivity();
		}

	}

	// open talking group button on click listener
	class OpenTalkingGroupBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// go to talking group detail info activity
			pushActivity(TalkingGroupDetailInfoActivity.class);
		}

	}

	// back button on click listener
	class BackBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// back to talking group detail info activity
			Log.d("!", "!!");
		}

	}

	// confirm add attendee button on click listener
	class ConfirmAddAttendeeBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// back to talking group detail info activity
			Log.d("@", "@@");
		}

	}

}
