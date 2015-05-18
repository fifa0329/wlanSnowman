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

public class AuthDialog extends Activity {
	private int carrier;
	private NotificationManager mNM;
	private BroadcastReceiver closeReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(AuthDialog.class.getName())) {
				AuthDialog.this.finish();
			}
		}
	};
	private Runnable login_runnable = new Runnable() {
		@Override
		public void run() {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AuthDialog.this);
			boolean result = false;
			if (carrier == SmartWifiApplication.CARRIER_CMCC) {
				result = AuthPortalCMCC.getInstance().login(prefs.getString("cmcc_user", ""), prefs.getString("cmcc_password", ""));
			} else if (carrier == SmartWifiApplication.CARRIER_CT) {
				result = AuthPortalCT.getInstance().login(prefs.getString("ct_user", ""), prefs.getString("ct_password", ""));
			}
			if (result) {
				showAuthNotification();
				AuthDialog.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AuthDialog.this, "登陆成功", Toast.LENGTH_LONG).show();
					}
				});
			} else {
				WifiAction.getInstance(AuthDialog.this).turnOffWifi();
				AuthDialog.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(AuthDialog.this, "登陆失败", Toast.LENGTH_LONG).show();
					}
				});
			}
		}
	};
	
    private void showAuthNotification() {
    	String text = "已认证，计费中";
    	((SmartWifiApplication)getApplication()).setStart_time(carrier);
        Notification notification = new Notification(R.drawable.money, text,
                System.currentTimeMillis());
        Intent logoutIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		logoutIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(this, getString(R.string.app_name),
                       text, contentIntent);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.timer_notification);
        notification.contentView = contentView;
        contentView.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime(), null, true);
        
        mNM.notify(WLANEngineService.NOTIFICATION_AUTH, notification);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth_dialog);
		
		carrier = getIntent().getIntExtra("carrier", SmartWifiApplication.CARRIER_CMCC);
		long delay_start = ((SmartWifiApplication)getApplication()).getDelay_start();
		if (delay_start > 0 && SystemClock.elapsedRealtime() - delay_start < 3600000) {
			new Thread(login_runnable).start();
			finish();
		}
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(AuthDialog.class.getName());
		registerReceiver(closeReceiver, filter);
		
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		TextView text_view = (TextView) findViewById(R.id.text);
		text_view.setText(String.format("您当前所在位置已经提供%sWLAN覆盖，您是否需要使用该服务？", (carrier == SmartWifiApplication.CARRIER_CMCC) ? "中国移动" : "中国电信"));
		final CheckBox repeat = (CheckBox) findViewById(R.id.repeat_check);
		Button ok_button = (Button) findViewById(R.id.ok_button);
		ok_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				unregisterReceiver(closeReceiver);
				if (repeat.isChecked()) {
					((SmartWifiApplication)getApplication()).setDelay_start();
				}
				new Thread(login_runnable).start();
				finish();
			}
		});
		Button cancel_button = (Button) findViewById(R.id.cancel_button);
		cancel_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				unregisterReceiver(closeReceiver);
				WifiAction.getInstance(AuthDialog.this).turnOffWifi();
				if (repeat.isChecked()) {
					Intent intent = new Intent(AuthDialog.this, WLANEngineService.class);
					intent.putExtra("delay", 3600);
					startService(intent);
				}
				finish();
			}
		});
	}
}
