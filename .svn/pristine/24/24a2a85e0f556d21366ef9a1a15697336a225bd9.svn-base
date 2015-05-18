package com.snowman.wlan.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		WebView web = (WebView) findViewById(R.id.webview);
		web.setBackgroundColor(0);
		web.getSettings().setDefaultTextEncodingName("utf-8");
		web.loadUrl("file:///android_asset/about.html");
	}
}
