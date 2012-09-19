package com.richitec.imeeting.contactselect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.richitec.commontoolkit.addressbook.AddressBookManager;
import com.richitec.commontoolkit.addressbook.ContactBean;
import com.richitec.commontoolkit.customcomponent.BarButtonItem.BarButtonItemStyle;
import com.richitec.commontoolkit.utils.StringUtils;
import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingBarButtonItem;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;
import com.richitec.imeeting.talkinggroup.TalkingGroupDetailInfoActivity;

public class ContactSelectActivity extends IMeetingNavigationActivity {

	// current talking group status, default value is establishing
	private TalkingGroupStatus _mCurrentTalkingGroupStatus = TalkingGroupStatus.ESTABLISHING;

	// contact search status
	private ContactSearchStatus _mContactSearchStatus = ContactSearchStatus.NONESEARCH;

	// define add not existed in address book contact popup window and its
	// parent view
	private PopupWindow _mAddNotExistedInABContactPopupWindow;
	private View _mNotExistedInABContactPopupWindowFrameLayoutView;

	// all address book name phonetic sorted contacts detail info list
	private final List<ContactBean> allNamePhoneticSortedContactsInfoArray = AddressBookManager
			.getInstance().getAllNamePhoneticSortedContactsInfoArray();
	// present contacts in address book detail info list
	private List<ContactBean> _mPresentContactsInABInfoArray;

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

		// set contacts in address book listView adapter
		_mPresentContactsInABInfoArray = allNamePhoneticSortedContactsInfoArray;
		((ListView) findViewById(R.id.contactInAB_listView))
				.setAdapter(generateInABContactAdapter(_mPresentContactsInABInfoArray));

		// test in and prein talking group attendees name
		generateIn7PreinTalkingGroupContactAdapter(null);
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

		// bind contact search editText text watcher
		((EditText) findViewById(R.id.contact_search_editText))
				.addTextChangedListener(new ContactSearchEditTextTextWatcher());

		// bind add not existed in address book contact button on click listener
		((Button) findViewById(R.id.add_notExistedInABContact_btn))
				.setOnClickListener(new AddNotExistedInABContactBtnOnClickListener());

		// init add not existed in address book contact popup window frameLayout
		// view
		_mNotExistedInABContactPopupWindowFrameLayoutView = ContactSelectActivity.this
				.getLayoutInflater().inflate(
						R.layout.add_notexistedinabcontact_popupwindow_layout,
						null);

		// init add not existed in address book contact popup window
		_mAddNotExistedInABContactPopupWindow = new PopupWindow(
				_mNotExistedInABContactPopupWindowFrameLayoutView,
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		// bind add not existed in address book contact popup window
		// frameLayout, relativeLayout on touch listener, dismiss and confirm
		// added button on click listener
		_mNotExistedInABContactPopupWindowFrameLayoutView
				.setOnTouchListener(new AddNotExistedInABContactPopupWindowOnTouchListener());
		((RelativeLayout) _mNotExistedInABContactPopupWindowFrameLayoutView
				.findViewById(R.id.add_notExistedInABContact_popupWindow_relativeLayout))
				.setOnTouchListener(new AddNotExistedInABContactPopupWindowOnTouchListener());
		((Button) _mNotExistedInABContactPopupWindowFrameLayoutView
				.findViewById(R.id.add_notExistedInABContact_popupWindow_dismiss_btn))
				.setOnClickListener(new AddNotExistedInABContactPopupWindowDismissBtnOnClickListener());
		((Button) _mNotExistedInABContactPopupWindowFrameLayoutView
				.findViewById(R.id.add_notExistedInABContact_confirmBtn))
				.setOnClickListener(new AddNotExistedInABContactPopupWindowConfirmAddedBtnOnClickListener());
	}

	@Override
	public void onBackPressed() {
		// check add not existed in address book contact popup window state
		if (_mAddNotExistedInABContactPopupWindow.isShowing()) {
			// dismiss add not existed in address book contact popup window
			_mAddNotExistedInABContactPopupWindow.dismiss();
		} else {
			super.onBackPressed();
		}
	}

	// generate in address book contact adapter
	private ListAdapter generateInABContactAdapter(
			List<ContactBean> presentContactsInAB) {
		// contact adapter data keys
		final String CONTACT_NAME = "contact_name";
		final String CONTACT_PHONES = "contact_phones";

		// set address book contacts list view present data list
		List<Map<String, Object>> _addressBookContactsPresentDataList = new ArrayList<Map<String, Object>>();

		for (ContactBean _contact : presentContactsInAB) {
			// generate data
			HashMap<String, Object> _dataMap = new HashMap<String, Object>();

			// get contact name and phone matching indexes
			@SuppressWarnings("unchecked")
			SparseArray<Integer> _nameMatchingIndexes = (SparseArray<Integer>) _contact
					.getExtension().get(
							AddressBookManager.NAME_MATCHING_INDEXES);
			@SuppressWarnings("unchecked")
			List<List<Integer>> _phoneMatchingIndexes = (List<List<Integer>>) _contact
					.getExtension().get(
							AddressBookManager.PHONENUMBER_MATCHING_INDEXES);

			// set data
			if (ContactSearchStatus.SEARCHBYNAME == _mContactSearchStatus) {
				// get display name
				SpannableString _displayName = new SpannableString(
						_contact.getDisplayName());

				// set attributed
				for (int i = 0; i < _nameMatchingIndexes.size(); i++) {
					// get key and value
					Integer _nameCharMatchedPos = getRealPosInContactDisplayName(
							_contact.getDisplayName(),
							_nameMatchingIndexes.keyAt(i));
					Integer _nameCharMatchedLength = _nameMatchingIndexes
							.get(_nameMatchingIndexes.keyAt(i));

					_displayName
							.setSpan(
									new ForegroundColorSpan(Color.BLUE),
									_nameCharMatchedPos,
									AddressBookManager.NAME_CHARACTER_FUZZYMATCHED_LENGTH == _nameCharMatchedLength ? _nameCharMatchedPos + 1
											: _nameCharMatchedPos
													+ _nameCharMatchedLength,
									Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}

				_dataMap.put(CONTACT_NAME, _displayName);
			} else {
				_dataMap.put(CONTACT_NAME, _contact.getDisplayName());
			}
			if (ContactSearchStatus.SEARCHBYPHONE == _mContactSearchStatus) {
				// get format phone number string
				SpannableString _formatPhoneNumberString = new SpannableString(
						_contact.getFormatPhoneNumbers());

				// get format phone number string separator "\n" positions
				List<Integer> _sepPositions = StringUtils.subStringPositions(
						_contact.getFormatPhoneNumbers(), "\n");

				// set attributed
				for (int i = 0; i < _phoneMatchingIndexes.size(); i++) {
					// check the phone matched
					if (0 != _phoneMatchingIndexes.get(i).size()) {
						// get begin and end position
						int _beginPos = _phoneMatchingIndexes.get(i).get(0);
						int _endPos = _phoneMatchingIndexes.get(i).get(
								_phoneMatchingIndexes.get(i).size() - 1) + 1;

						// check matched phone
						if (1 <= i) {
							_beginPos += _sepPositions.get(i - 1) + 1;
							_endPos += _sepPositions.get(i - 1) + 1;
						}

						_formatPhoneNumberString.setSpan(
								new ForegroundColorSpan(Color.BLUE), _beginPos,
								_endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}

				_dataMap.put(CONTACT_PHONES, _formatPhoneNumberString);
			} else {
				_dataMap.put(CONTACT_PHONES, _contact.getFormatPhoneNumbers());
			}

			// add data to list
			_addressBookContactsPresentDataList.add(_dataMap);
		}

		return new InAB6In7PreinTalkingGroupContactAdapter(this,
				_addressBookContactsPresentDataList,
				R.layout.addressbook_contact_layout, new String[] {
						CONTACT_NAME, CONTACT_PHONES }, new int[] {
						R.id.adressBook_contact_displayName_textView,
						R.id.addressBook_contact_phoneNumber_textView });
	}

	// generate in and prein talking group contact adapter
	private ListAdapter generateIn7PreinTalkingGroupContactAdapter(List<?> lists) {
		return null;
	}

	// get real position in contact display name
	private Integer getRealPosInContactDisplayName(String displayName,
			Integer position) {
		int _realPos = 0;

		int _tmpPos = 0;
		boolean _prefixHasChar = false;

		for (int i = 0; i < displayName.length(); i++) {
			if (String.valueOf(displayName.charAt(i))
					.matches("[\u4e00-\u9fa5]")) {
				if (_prefixHasChar) {
					_prefixHasChar = false;

					_tmpPos += 1;
				}

				if (_tmpPos == position) {
					_realPos = i;

					break;
				}

				_tmpPos += 1;
			} else if (' ' == displayName.charAt(i)) {
				if (_prefixHasChar) {
					_prefixHasChar = false;

					_tmpPos += 1;
				}
			} else {
				if (_tmpPos == position) {
					_realPos = i;

					break;
				}

				_prefixHasChar = true;
			}
		}

		return _realPos;
	}

	// inner class
	// talking group status
	public static enum TalkingGroupStatus {
		ESTABLISHING, GOING
	}

	// contact search status
	enum ContactSearchStatus {
		NONESEARCH, SEARCHBYNAME, SEARCHBYPHONE
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

	// contact search editText text watcher
	class ContactSearchEditTextTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			// set contact search status
			if (null == s || 0 == s.length()) {
				_mContactSearchStatus = ContactSearchStatus.NONESEARCH;
			} else if (s.toString().matches("^[0-9]*$")) {
				_mContactSearchStatus = ContactSearchStatus.SEARCHBYPHONE;
			} else {
				_mContactSearchStatus = ContactSearchStatus.SEARCHBYNAME;
			}

			// update present contacts in address book detail info list
			switch (_mContactSearchStatus) {
			case SEARCHBYNAME:
				_mPresentContactsInABInfoArray = AddressBookManager
						.getInstance().getContactsByName(s.toString());
				break;

			case SEARCHBYPHONE:
				_mPresentContactsInABInfoArray = AddressBookManager
						.getInstance().getContactsByPhone(s.toString());
				break;

			case NONESEARCH:
			default:
				_mPresentContactsInABInfoArray = allNamePhoneticSortedContactsInfoArray;
				break;
			}

			// update contacts in address book listView adapter
			((ListView) findViewById(R.id.contactInAB_listView))
					.setAdapter(generateInABContactAdapter(_mPresentContactsInABInfoArray));
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	}

	// add not existed in address book contact button on click listener
	class AddNotExistedInABContactBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// show add not existed in address book contact popup window
			_mAddNotExistedInABContactPopupWindow.showAtLocation(v,
					Gravity.CENTER, 0, 0);

			// set add not existed in address book contact exitText focusable
			((EditText) _mNotExistedInABContactPopupWindowFrameLayoutView
					.findViewById(R.id.add_notExistedInABContact_editText))
					.setFocusable(true);
		}

	}

	// add not existed in address book contact popup window dismiss button on
	// click listener
	class AddNotExistedInABContactPopupWindowDismissBtnOnClickListener
			implements OnClickListener {

		@Override
		public void onClick(View v) {
			// dismiss add not existed in address book contact popup window
			_mAddNotExistedInABContactPopupWindow.dismiss();
		}

	}

	// add not existed in address book contact popup window confirm added button
	// on click listener
	class AddNotExistedInABContactPopupWindowConfirmAddedBtnOnClickListener
			implements OnClickListener {

		@Override
		public void onClick(View v) {
			//

			// dismiss add not existed in address book contact popup window
			_mAddNotExistedInABContactPopupWindow.dismiss();
		}

	}

	// add not existed in address book contact popup window on touch listener
	class AddNotExistedInABContactPopupWindowOnTouchListener implements
			OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// check on touch view class
			if (v instanceof FrameLayout) {
				// dismiss add not existed in address book contact popup window
				_mAddNotExistedInABContactPopupWindow.dismiss();
			}

			return true;
		}

	}

}
