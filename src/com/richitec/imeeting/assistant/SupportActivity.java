package com.richitec.imeeting.assistant;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richitec.imeeting.R;
import com.richitec.imeeting.customcomponent.IMeetingNavigationActivity;

public class SupportActivity extends IMeetingNavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.support_activity_layout);

		// set title text
		setTitle(R.string.support_nav_title_text);

		// get support webView
		WebView _supportWebView = (WebView) findViewById(R.id.support_webView);

		// load support url
		_supportWebView.loadUrl(getResources().getString(R.string.server_url)
				+ getResources().getString(R.string.support_url));

		// add web chrome client for loading progress changed
		_supportWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);

				// set support loading progressBar progress
				((ProgressBar) findViewById(R.id.support_loading_progressBar))
						.setProgress(newProgress);

				// set support loading textView text
				((TextView) findViewById(R.id.support_loading_textView))
						.setText(getResources().getString(
								R.string.support_loading_textView_textHeader)
								+ newProgress + "%");

				// check support page loading completed
				if (Integer.parseInt(getResources().getString(
						R.string.support_loading_progressBar_max)) == newProgress) {
					// support loading completed, remove support loading
					// linearLayout
					((LinearLayout) findViewById(R.id.support_loading_linearLayout))
							.setVisibility(View.GONE);
				}
			}

		});
	}

}
