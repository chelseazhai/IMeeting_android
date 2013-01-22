package com.richitec.imeeting.talkinggroup;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.richitec.commontoolkit.CTApplication;
import com.richitec.commontoolkit.addressbook.AddressBookManager;
import com.richitec.commontoolkit.customadapter.CTListAdapter;
import com.richitec.commontoolkit.utils.JSONUtils;
import com.richitec.imeeting.R;

public class TalkingGroupHistoryListItemAdapter extends CTListAdapter {

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
								new ForegroundColorSpan(CTApplication
										.getContext().getResources()
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
							// get address book manager reference
							AddressBookManager _addressBookManager = AddressBookManager
									.getInstance();

							// get attendee phone
							String _attendeePhone = JSONUtils
									.getStringFromJSONArray(_itemDataJSONArray,
											i);

							// set attendee name
							((TextView) ((RelativeLayout) _tableRowItem)
									.findViewById(R.id.attendee_name_textView))
									.setText(_addressBookManager
											.getContactsDisplayNamesByPhone(
													_attendeePhone).get(0));

							// set attendee photo
							// define contact photo bitmap
							Bitmap _contactPhotoBitmap = ((BitmapDrawable) _mLayoutInflater
									.getContext().getResources()
									.getDrawable(R.drawable.img_default_avatar))
									.getBitmap();

							// get contact photo
							byte[] _contactPhoto = _addressBookManager
									.getContactsPhotosByPhone(_attendeePhone)
									.get(0);

							if (null != _contactPhoto) {
								try {
									// get photo data stream
									InputStream _photoDataStream = new ByteArrayInputStream(
											_contactPhoto);

									// check photo data stream
									if (null != _photoDataStream) {
										_contactPhotoBitmap = BitmapFactory
												.decodeStream(_photoDataStream);

										// close photo data stream
										_photoDataStream.close();
									}
								} catch (IOException e) {
									e.printStackTrace();

									Log.e(LOG_TAG,
											"Get contact photo data stream error, error message = "
													+ e.getMessage());
								}
							}

							// set photo
							((ImageView) ((RelativeLayout) _tableRowItem)
									.findViewById(R.id.attendee_avatar_imageView))
									.setImageBitmap(_contactPhotoBitmap);
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
