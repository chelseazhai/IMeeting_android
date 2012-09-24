package com.richitec.imeeting.talkinggroup;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TableRow;

import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class TalkingGroupActivity extends IMeetingNavigationActivity {

	private static final String LOG_TAG = "TalkingGroupActivity";

	// talking group activity parameter keys
	public static final String TALKINGGROUP_ACTIVITY_PARAM_TALKINGGROUPID = "talking group id";
	public static final String TALKINGGROUP_ACTIVITY_PARAM_TALKINGGROUP_ATTENDEESPHONE = "talking group attendees phone";

	// talking group id
	private String _mTalkingGroupId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.talking_group_activity_layout);

		// get talking group bottom toolBar tableRow
		TableRow _talkingGroupBottomToolbarTableRow = (TableRow) findViewById(R.id.talkingGroup_bottomToolbar_tableRow);

		for (int i = 0; i < _talkingGroupBottomToolbarTableRow.getChildCount(); i++) {
			// get each bottom toolBar item
			Button _bottomToolbarItemBtn = (Button) _talkingGroupBottomToolbarTableRow
					.getChildAt(i).findViewById(
							R.id.talkingGroup_bottomToolbarItem_btn);

			// init each bottom toolBar item
			_bottomToolbarItemBtn.setText("Test");
			_bottomToolbarItemBtn.setOnClickListener(null);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.talking_group_activity_layout, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Log.d("cc", "cc");
	}

	@Override
	protected boolean hideNavigationBarWhenOnCreated() {
		return true;
	}

}
