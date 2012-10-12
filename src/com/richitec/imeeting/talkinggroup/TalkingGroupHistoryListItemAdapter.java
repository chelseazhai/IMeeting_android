package com.richitec.imeeting.talkinggroup;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.richitec.commontoolkit.activityextension.AppLaunchActivity;
import com.richitec.commontoolkit.addressbook.AddressBookManager;
import com.richitec.commontoolkit.customadapter.CommonListAdapter;
import com.richitec.commontoolkit.utils.JSONUtils;
import com.richitec.imeeting.R;

public class TalkingGroupHistoryListItemAdapter extends CommonListAdapter {

	private static final String LOG_TAG = "MyGroupHistoryListItemAdapter";

	public TalkingGroupHistoryListItemAdapter(Context context,
			List<Map<String, ?>> data, int itemsLayoutResId, String[] dataKeys,
			int[] itemsComponentResIds) {
		super(context, data, itemsLayoutResId, dataKeys, itemsComponentResIds);
	}

	@Override
	protected void bindView(View view, Map<String, ?> dataMap, String dataKey) {
		// get item data object
		Object _itemData = dataMap.get(dataKey);

		// check view type
		// textView
		if (view instanceof TextView) {
			// generate view text
			SpannableString _viewNewText = new SpannableString(
					(null == ((TextView) view).getHint() ? ""
							: ((TextView) view).getHint())
							+ (null == _itemData ? "" : _itemData.toString()));
			// check data class name
			if (_itemData instanceof SpannableString) {
				_viewNewText
						.setSpan(
								new ForegroundColorSpan(AppLaunchActivity
										.getAppContext().getResources()
										.getColor(R.color.dark_seagreen)), 0,
								_viewNewText.length(),
								Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// set view text
			((TextView) view).setText(_viewNewText);
		}
		// tableRow
		else if (view instanceof TableRow) {
			try {
				// define item data json array and convert item data to json
				// array
				JSONArray _itemDataJSONArray = (JSONArray) _itemData;

				// process each table row item
				for (int i = 0; i < ((TableRow) view).getVirtualChildCount(); i++) {
					// get table row item
					View _tableRowItem = ((TableRow) view).getVirtualChildAt(i);

					// check visible view
					if (i < _itemDataJSONArray.length()) {
						// shown table row item
						_tableRowItem.setVisibility(View.VISIBLE);

						// check table row item type
						// linearLayout
						if (_tableRowItem instanceof RelativeLayout) {
							// set attendee name
							((TextView) ((RelativeLayout) _tableRowItem)
									.findViewById(R.id.attendee_name_textView))
									.setText(AddressBookManager
											.getInstance()
											.getContactsDisplayNamesByPhone(
													JSONUtils
															.getStringFromJSONArray(
																	_itemDataJSONArray,
																	i)).get(0));
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

				Log.e(LOG_TAG,
						"Convert item data to json array error, item data = "
								+ _itemData);
			}
		}
	}

}
