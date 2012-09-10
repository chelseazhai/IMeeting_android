package com.richitec.imeeting.customcomponent;

import android.graphics.drawable.Drawable;

import com.richitec.commontoolkit.activityextension.NavigationActivity;
import com.richitec.imeeting.R;

public class IMeetingNavigationActivity extends NavigationActivity {

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		// set navigation bar background drawable
		setNavBarBackgroundResource(R.drawable.img_imeeting_navbar_bg);
	}

	@Override
	public Drawable backBarBtnItemNormalDrawable() {
		return getResources().getDrawable(
				R.drawable.img_imeeting_leftbarbtnitem_normal_bg);
	}

	@Override
	public Drawable backBarBtnItemPressedDrawable() {
		return getResources().getDrawable(
				R.drawable.img_imeeting_leftbarbtnitem_normal_bg);
	}

}
