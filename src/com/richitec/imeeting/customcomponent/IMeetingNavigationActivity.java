package com.richitec.imeeting.customcomponent;

import com.richitec.commontoolkit.activityextension.NavigationActivity;
import com.richitec.imeeting.R;

public class IMeetingNavigationActivity extends NavigationActivity {

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		// set navigation bar background drawable
		setNavBarBackgroundResource(R.drawable.img_imeeting_navbar_bg);

		// update nav back bar button item drawable
		updateNavBackBarBtnItemResource(
				R.drawable.img_imeeting_leftbarbtnitem_normal_bg,
				R.drawable.img_imeeting_leftbarbtnitem_normal_bg);
	}

}
