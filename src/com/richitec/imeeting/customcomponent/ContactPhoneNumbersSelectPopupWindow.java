package com.richitec.imeeting.customcomponent;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.richitec.commontoolkit.activityextension.AppLaunchActivity;
import com.richitec.commontoolkit.customcomponent.CommonPopupWindow;
import com.richitec.imeeting.R;

public abstract class ContactPhoneNumbersSelectPopupWindow extends
		CommonPopupWindow {

	// select contact position
	private int _mSelectContactPosition;

	public ContactPhoneNumbersSelectPopupWindow(int resource, int width,
			int height, boolean focusable, boolean isBindDefListener) {
		super(resource, width, height, focusable, isBindDefListener);
	}

	public ContactPhoneNumbersSelectPopupWindow(int resource, int width,
			int height) {
		super(resource, width, height);
	}

	@Override
	protected void bindPopupWindowComponentsListener() {

		// get contact phones select phone button parent linearLayout
		LinearLayout _phoneBtnParentLinearLayout = (LinearLayout) getContentView()
				.findViewById(R.id.contactPhones_select_phoneBtn_linearLayout);

		// bind contact phone select phone button click listener
		for (int i = 0; i < _phoneBtnParentLinearLayout.getChildCount(); i++) {
			((Button) _phoneBtnParentLinearLayout.getChildAt(i))
					.setOnClickListener(new ContactPhoneSelectPhoneBtnOnClickListener());
		}

		// bind contact phone select phone listView item click listener
		((ListView) getContentView().findViewById(
				R.id.contactPhones_select_phonesListView))
				.setOnItemClickListener(new ContactPhoneSelectPhoneListViewOnItemClickListener());

		// bind contact phone select cancel button click listener
		((Button) getContentView().findViewById(
				R.id.contactPhones_select_cancelBtn))
				.setOnClickListener(new ContactPhoneSelectCancelBtnOnClickListener());
	}

	@Override
	protected void resetPopupWindow() {
		// hide contact phones select phone list view
		((ListView) getContentView().findViewById(
				R.id.contactPhones_select_phonesListView))
				.setVisibility(View.GONE);

		// get contact phones select phone button parent linearLayout and hide
		// it
		LinearLayout _phoneBtnParentLinearLayout = (LinearLayout) getContentView()
				.findViewById(R.id.contactPhones_select_phoneBtn_linearLayout);
		_phoneBtnParentLinearLayout.setVisibility(View.GONE);

		// process phone button
		for (int i = 0; i < _phoneBtnParentLinearLayout.getChildCount(); i++) {
			// hide contact phones select phone button
			((Button) _phoneBtnParentLinearLayout.getChildAt(i))
					.setVisibility(View.GONE);
		}
	}

	// set contact phone number for selecting
	public void setContactPhones4Selecting(String displayName,
			List<String> phoneNumbers, int position) {
		// update select contact position
		_mSelectContactPosition = position;

		// set contact phones select title textView text
		((TextView) getContentView().findViewById(
				R.id.contactPhones_select_titleTextView))
				.setText(AppLaunchActivity
						.getAppContext()
						.getResources()
						.getString(
								R.string.contactPhones_selectPopupWindow_titleTextView_text)
						.replace("***", displayName));

		// check phone numbers for selecting
		if (2 <= phoneNumbers.size() && phoneNumbers.size() <= 3) {
			// get contact phones select phone button parent linearLayout and
			// show it
			LinearLayout _phoneBtnParentLinearLayout = (LinearLayout) getContentView()
					.findViewById(
							R.id.contactPhones_select_phoneBtn_linearLayout);
			_phoneBtnParentLinearLayout.setVisibility(View.VISIBLE);

			// process phone button
			for (int i = 0; i < phoneNumbers.size(); i++) {
				// get contact phones select phone button
				Button _phoneBtn = (Button) _phoneBtnParentLinearLayout
						.getChildAt(i);

				// set button text and show it
				_phoneBtn.setText(phoneNumbers.get(i));
				_phoneBtn.setVisibility(View.VISIBLE);
			}
		} else {
			// get contact phones select phone list view
			ListView _phoneListView = (ListView) getContentView().findViewById(
					R.id.contactPhones_select_phonesListView);

			// set phone list view adapter
			_phoneListView
					.setAdapter(new ArrayAdapter<String>(
							AppLaunchActivity.getAppContext(),
							R.layout.contact_phonenumbers_select_phoneslist_item_layout,
							phoneNumbers));

			// show phone list view
			_phoneListView.setVisibility(View.VISIBLE);
		}
	}

	// contact phone select popup window phone button or phone listView item on
	// click
	public abstract void phoneBtn6PhoneListViewItemOnClick(
			String selectedPhone, int selectedContactPosition);

	// inner class
	// contact phone select phone button on click listener
	class ContactPhoneSelectPhoneBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// get phone button text
			String _selectedPhone = (String) ((Button) v).getText();

			// dismiss contact phone select popup window
			dismiss();

			// selected contact phone
			phoneBtn6PhoneListViewItemOnClick(_selectedPhone,
					_mSelectContactPosition);
		}

	}

	// contact phone select phone listView on item click listener
	class ContactPhoneSelectPhoneListViewOnItemClickListener implements
			OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// get phone listView item data
			String _selectedPhone = (String) ((TextView) view).getText();

			// dismiss contact phone select popup window
			dismiss();

			// selected contact phone
			phoneBtn6PhoneListViewItemOnClick(_selectedPhone,
					_mSelectContactPosition);

		}

	}

	// contact phone select cancel button on click listener
	class ContactPhoneSelectCancelBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// dismiss contact phone select popup window
			dismiss();
		}

	}

}
