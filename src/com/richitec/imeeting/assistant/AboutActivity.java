package com.richitec.imeeting.assistant;

import android.os.Bundle;
import android.widget.TextView;

import com.richitec.commontoolkit.activityextension.NavigationActivity;
import com.richitec.commontoolkit.utils.VersionUtils;
import com.richitec.imeeting.R;

public class AboutActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.about_activity_layout);

		// set title text
		setTitle(R.string.about_nav_title_text);

		// set product version name
		((TextView) findViewById(R.id.product_versionName_textView))
				.setText(VersionUtils.currentVersionName(this));
	}

}
