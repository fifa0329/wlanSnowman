package com.snowman.wlan.ui;

import com.snowman.wlan.engine.SmartWifiApplication;
import com.snowman.wlan.engine.WLANEngineService;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;

public class WelActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		Start();

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (((SmartWifiApplication)getApplication()).getStart_time() == 0) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			if (prefs.getBoolean("serviceRunning", true)) {
				startService(new Intent(this, WLANEngineService.class));
			}
		}
	}

	public void Start() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent();
				intent.setClass(WelActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}.start();
	}

}
