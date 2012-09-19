package com.richitec.imeeting.talkinggroup;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.richitec.commontoolkit.customadapter.CommonListAdapter;
import com.richitec.imeeting.R;

public class TalkingGroupHistoryListItemAdapter extends CommonListAdapter {

	private static final String LOG_TAG = "MyGroupHistoryListItemAdapter";

	public TalkingGroupHistoryListItemAdapter(Context context,
			List<? extends Map<String, ?>> data, int itemsLayoutResId,
			String[] dataKeys, int[] itemsComponentResIds) {
		super(context, data, itemsLayoutResId, dataKeys, itemsComponentResIds);
	}

	@Override
	protected void bindView(View view, Map<String, ?> dataMap, String dataKey) {
		// get item data object
		Object _itemData = dataMap.get(dataKey);

		// check view type
		// textView
		if (view instanceof TextView) {
			// set view text
			((TextView) view).setText((null == ((TextView) view).getHint() ? ""
					: ((TextView) view).getHint())
					+ (null == _itemData ? "" : _itemData.toString()));
		}
		// tableRow
		else if (view instanceof TableRow) {
			// define item data array
			String[] _itemDataArray;
			try {
				// convert item data to string array
				_itemDataArray = (String[]) _itemData;

				// process each table row item
				for (int i = 0; i < ((TableRow) view).getVirtualChildCount(); i++) {
					// get table row item
					View _tableRowItem = ((TableRow) view).getVirtualChildAt(i);

					// check visible view
					if (i < _itemDataArray.length) {
						// shown table row item
						_tableRowItem.setVisibility(View.VISIBLE);

						// check table row item type
						// linearLayout
						if (_tableRowItem instanceof RelativeLayout) {
							// set attendee name
							((TextView) ((RelativeLayout) _tableRowItem)
									.findViewById(R.id.attendee_name_textView))
									.setText(_itemDataArray[i]);
						}
						// textView
						else if (_tableRowItem instanceof TextView) {
							// test by ares, ????
							Log.d(LOG_TAG, "It's a TextView");
						}
					} else {
						// hide table row item
						_tableRowItem.setVisibility(View.GONE);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG_TAG, "??????");
			}
		}
	}

}
