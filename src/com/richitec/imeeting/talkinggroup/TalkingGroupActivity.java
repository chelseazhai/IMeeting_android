package com.richitec.imeeting.talkinggroup;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;

import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class TalkingGroupActivity extends IMeetingNavigationActivity {

	private static final String LOG_TAG = "TalkingGroupDetailInfoActivity";

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

		// generate talking group activity bottom toolBar items data list and
		// init bottom toolBar items
		List<Map<String, Object>> _bottomToolbarItemsDataList = generateBottomToolbarItemsDataList();

		TableRow _talkingGroupDetailInfoBottomToolbarTableRow = (TableRow) findViewById(R.id.talkingGroup_bottomToolbar_tableRow);

		for (int i = 0; i < _talkingGroupDetailInfoBottomToolbarTableRow
				.getChildCount(); i++) {
			// get each bottom toolBar item
			Button _bottomToolbarItemBtn = (Button) _talkingGroupDetailInfoBottomToolbarTableRow
					.getChildAt(i).findViewById(
							R.id.talkingGroup_bottomToolbarItem_btn);

			// init each bottom toolBar item
			_bottomToolbarItemBtn.setText((String) _bottomToolbarItemsDataList
					.get(i).get(""));
			_bottomToolbarItemBtn
					.setOnClickListener((OnClickListener) _bottomToolbarItemsDataList
							.get(i).get(""));
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

	// generate talking group activity bottom toolBar items data list
	private List<Map<String, Object>> generateBottomToolbarItemsDataList() {
		return null;
	}

}
