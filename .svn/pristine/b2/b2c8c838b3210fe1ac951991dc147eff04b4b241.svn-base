package com.snowman.wlan.ui;

import com.snowman.wlan.engine.Downloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FeedbackActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		setTitle("Òâ¼û·´À¡");
		
		Button submit_button = (Button) findViewById(R.id.button1);
		submit_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				new Downloader(FeedbackActivity.this).uploadLog();
			}
		});
		
		Button cancel_button = (Button) findViewById(R.id.button2);
		cancel_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
