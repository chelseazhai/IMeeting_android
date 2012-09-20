package com.richitec.imeeting.customcomponent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.richitec.commontoolkit.customcomponent.CommonPopupWindow;
import com.richitec.imeeting.R;

public abstract class AddNotExistedInABContactPopupWindow extends
		CommonPopupWindow {

	public AddNotExistedInABContactPopupWindow(int resource, int width,
			int height, boolean focusable, boolean isBindDefListener) {
		super(resource, width, height, focusable, isBindDefListener);
	}

	public AddNotExistedInABContactPopupWindow(int resource, int width,
			int height) {
		super(resource, width, height);
	}

	@Override
	protected void bindPopupWindowComponentsListener() {
		// bind add not existed in address book contact popup window dismiss and
		// confirm added button on click listener
		((Button) getContentView().findViewById(
				R.id.add_notExistedInABContact_popupWindow_dismiss_btn))
				.setOnClickListener(new AddNotExistedInABContactPopupWindowDismissBtnOnClickListener());
		((Button) getContentView().findViewById(
				R.id.add_notExistedInABContact_confirmBtn))
				.setOnClickListener(new AddNotExistedInABContactPopupWindowConfirmAddedBtnOnClickListener());
	}

	@Override
	protected void resetPopupWindow() {
		// clear add not existed in address book contact editText text
		((EditText) getContentView().findViewById(
				R.id.add_notExistedInABContact_editText)).setText("");
	}

	// add not existed in address book contact popup window confirm added button
	// on click
	public abstract void confirmAddedBtnOnClick(String addedPhone);

	// inner class
	// add not existed in address book contact popup window dismiss button on
	// click listener
	class AddNotExistedInABContactPopupWindowDismissBtnOnClickListener
			implements OnClickListener {

		@Override
		public void onClick(View v) {
			// dismiss add not existed in address book contact popup window
			dismiss();
		}

	}

	// add not existed in address book contact popup window confirm added button
	// on click listener
	class AddNotExistedInABContactPopupWindowConfirmAddedBtnOnClickListener
			implements OnClickListener {

		@Override
		public void onClick(View v) {
			// get added not existed in address book contact phone number
			String _addedNotExistedInABContactPhoneNumber = ((EditText) getContentView()
					.findViewById(R.id.add_notExistedInABContact_editText))
					.getText().toString();

			// dismiss add not existed in address book contact popup window
			dismiss();

			// confirm added the phone
			confirmAddedBtnOnClick(_addedNotExistedInABContactPhoneNumber);
		}

	}

}
