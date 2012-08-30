package com.richitec.imeeting.contactselect;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.richitec.commontoolkit.customadapter.CommonListAdapter;

public class InAB6In7PreinTalkingGroupContactAdapter extends CommonListAdapter {

	public InAB6In7PreinTalkingGroupContactAdapter(Context context,
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
			((TextView) view).setText(null == _itemData ? "" : _itemData
					.toString());
		}
	}

}
