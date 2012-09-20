package com.richitec.imeeting.customcomponent;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.richitec.commontoolkit.activityextension.AppLaunchActivity;
import com.richitec.imeeting.R;

public class AddNotExistedInABContactPopupWindow extends PopupWindow {

	public AddNotExistedInABContactPopupWindow() {
		super();
	}

	public AddNotExistedInABContactPopupWindow(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AddNotExistedInABContactPopupWindow(Context context,
			AttributeSet attrs) {
		super(context, attrs);
	}

	public AddNotExistedInABContactPopupWindow(Context context) {
		super(context);
	}

	public AddNotExistedInABContactPopupWindow(int width, int height) {
		super(width, height);
	}

	public AddNotExistedInABContactPopupWindow(View contentView, int width,
			int height, boolean focusable) {
		super(contentView, width, height, focusable);
	}

	public AddNotExistedInABContactPopupWindow(View contentView, int width,
			int height) {
		super(contentView, width, height);
	}

	public AddNotExistedInABContactPopupWindow(View contentView) {
		super(contentView);
	}

	// constructor with add not existed in address book contact popup window
	// layout resource id
	public AddNotExistedInABContactPopupWindow(int resource, int width,
			int height) {
		super(((LayoutInflater) AppLaunchActivity.getAppContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).inflate(
				resource, null), width, height);

		// bind add not existed in address book contact popup window content
		// view and relativeLayout on touch listener
		getContentView().setOnTouchListener(
				new AddNotExistedInABContactPopupWindowOnTouchListener());
		((RelativeLayout) getContentView().findViewById(
				R.id.add_notExistedInABContact_popupWindow_relativeLayout))
				.setOnTouchListener(new AddNotExistedInABContactPopupWindowOnTouchListener());

		// bind add not existed in address book contact popup window content
		// view on key listener
		getContentView().setOnKeyListener(
				new AddNotExistedInABContactPopupWindowOnKeyListener());
	}

	// inner class
	// add not existed in address book contact popup window on touch listener
	class AddNotExistedInABContactPopupWindowOnTouchListener implements
			OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// check on touch view class
			if (v instanceof FrameLayout) {
				// dismiss add not existed in address book contact popup window
				dismiss();
			}

			return true;
		}

	}

	// add not existed in address book contact popup window on key listener
	class AddNotExistedInABContactPopupWindowOnKeyListener implements
			OnKeyListener {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			Log.d("", "view = " + v + ", key code = " + keyCode
					+ " and key event = " + event);

			// listen back button pressed
			if (KeyEvent.KEYCODE_BACK == keyCode
					&& KeyEvent.ACTION_DOWN == event.getAction()) {
				// dismiss add not existed in address book contact popup window
				dismiss();
			}

			return false;
		}

	}

}
