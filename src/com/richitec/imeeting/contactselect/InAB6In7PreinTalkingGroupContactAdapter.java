package com.richitec.imeeting.contactselect;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.richitec.commontoolkit.customadapter.CommonListAdapter;
import com.richitec.imeeting.R;

public class InAB6In7PreinTalkingGroupContactAdapter extends CommonListAdapter {

	private static final String LOG_TAG = "InAB6In7PreinTalkingGroupContactAdapter";

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
			((TextView) view)
					.setText(null == _itemData ? ""
							: _itemData instanceof SpannableString ? (SpannableString) _itemData
									: _itemData.toString());
		}
		// imageView
		else if (view instanceof ImageView) {
			// define item data boolean
			Boolean _itemDataBoolean;
			try {
				// convert item data to boolean
				_itemDataBoolean = (Boolean) _itemData;

				if (_itemDataBoolean) {
					// hide in talking group image view
					((ImageView) view).setVisibility(View.INVISIBLE);
				} else {
					// show in talking group image view
					((ImageView) view).setVisibility(View.VISIBLE);
				}
			} catch (Exception e) {
				e.printStackTrace();

				Log.e(LOG_TAG,
						"Convert item data to boolean error, item data = "
								+ _itemData);
			}
		}
		// imageView parent frameLayout
		else if (view instanceof FrameLayout) {
			// define item data boolean
			Boolean _itemDataBoolean;
			try {
				// convert item data to boolean
				_itemDataBoolean = (Boolean) _itemData;

				// get selected and unselected imageView
				ImageView _selectedImageView = (ImageView) ((FrameLayout) view)
						.findViewById(R.id.addressBook_contact_selected_imageView);
				ImageView _unselectedImageView = (ImageView) ((FrameLayout) view)
						.findViewById(R.id.addressBook_contact_unselected_imageView);

				if (_itemDataBoolean) {
					// show selected imageView and hide unselected imageView
					_selectedImageView.setVisibility(View.VISIBLE);
					_unselectedImageView.setVisibility(View.GONE);
				} else {
					// show unselected imageView and hide selected imageView
					_unselectedImageView.setVisibility(View.VISIBLE);
					_selectedImageView.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();

				Log.e(LOG_TAG,
						"Convert item data to boolean error, item data = "
								+ _itemData);
			}
		}
	}

}
