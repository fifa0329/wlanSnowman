package com.snowman.wlan.ui;

import com.snowman.wlan.engine.*;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class SystemSwitchDialog extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_switch_dialog);
		setTitle("系统开关");
		
		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(SystemSwitchDialog.this);
		TextView text_view = (TextView) findViewById(R.id.text);
		text_view.setText("是否启用智能WiFi后台服务？");
		final CheckBox icon_check = (CheckBox) findViewById(R.id.icon_check);
		icon_check.setChecked(prefs.getBoolean("disableSystemIcon", false));
		Button ok_button = (Button) findViewById(R.id.ok_button);
		ok_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				Editor editor = prefs.edit();
				editor.putBoolean("serviceRunning", true);
				editor.putBoolean("disableSystemIcon", icon_check.isChecked());
				editor.commit();
				startService(new Intent(SystemSwitchDialog.this,
						WLANEngineService.class));
				finish();
			}
		});
		Button cancel_button = (Button) findViewById(R.id.cancel_button);
		cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Editor editor = prefs.edit();
				editor.putBoolean("serviceRunning", false);
				editor.putBoolean("disableSystemIcon", icon_check.isChecked());
				stopService(new Intent(SystemSwitchDialog.this,
						WLANEngineService.class));
				editor.commit();
				finish();
			}
		});
	}
}
