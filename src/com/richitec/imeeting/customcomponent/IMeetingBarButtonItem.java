package com.richitec.imeeting.customcomponent;

import android.content.Context;

import com.richitec.commontoolkit.activityextension.R;
import com.richitec.commontoolkit.customui.BarButtonItem;

public class IMeetingBarButtonItem extends BarButtonItem {

	public IMeetingBarButtonItem(Context context,
			BarButtonItemStyle barBtnItemStyle, CharSequence title,
			OnClickListener btnClickListener) {
		super(
				context,
				title,
				BarButtonItemStyle.LEFT_BACK == barBtnItemStyle ? context
						.getResources()
						.getDrawable(
								R.drawable.img_imeeting_leftbarbtnitem_normal_bg)
						: (BarButtonItemStyle.RIGHT_GO == barBtnItemStyle ? context
								.getResources()
								.getDrawable(
										R.drawable.img_imeeting_rightbarbtnitem_normal_bg)
								: null),
				BarButtonItemStyle.LEFT_BACK == barBtnItemStyle ? context
						.getResources()
						.getDrawable(
								R.drawable.img_imeeting_leftbarbtnitem_normal_bg)
						: (BarButtonItemStyle.RIGHT_GO == barBtnItemStyle ? context
								.getResources()
								.getDrawable(
										R.drawable.img_imeeting_rightbarbtnitem_normal_bg)
								: null), btnClickListener);
	}

	public IMeetingBarButtonItem(Context context,
			BarButtonItemStyle barBtnItemStyle, int titleId,
			OnClickListener btnClickListener) {
		this(context, barBtnItemStyle, context.getResources()
				.getString(titleId), btnClickListener);
	}

}
