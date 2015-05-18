package com.snowman.wlan.ui;

import java.text.SimpleDateFormat;

import com.snowman.wlan.engine.SmartWifiApplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private Button reset,logout;
	private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(HomeActivity.class.getName())) {
				refreshChronometer();
			}
		}
	};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		setTitle("首页");

		IntentFilter filter = new IntentFilter();
		filter.addAction(HomeActivity.class.getName());
		registerReceiver(refreshReceiver, filter);
		
		reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, ResetActivity.class));
			}
		});

		logout = (Button) findViewById(R.id.button3);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, LogoutDialog.class));
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(refreshReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshChronometer();
	}

	private void refreshChronometer() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int carrier = prefs.getInt("carrier", SmartWifiApplication.CARRIER_CMCC);
		int hours = 15;
		long minutes = 0;
		
		TextView carrierText = (TextView) findViewById(R.id.textView1);
		if (carrier == SmartWifiApplication.CARRIER_CMCC) {
			carrierText.setText("中国移动  WLAN");
			hours = prefs.getInt("cmcc-hours", 15);
			minutes = prefs.getLong("cmcc-minutes", 0);
		} else if (carrier == SmartWifiApplication.CARRIER_CT) {
			carrierText.setText("中国电信  WLAN");
			hours = prefs.getInt("ct-hours", 15);
			minutes = prefs.getLong("ct-minutes", 0);
		}
		TextView type = (TextView) findViewById(R.id.textView2);
		type.setText("套餐类型：" + hours + "小时/月");
		
		String total_minutes =  String.format("%02d:%02d", minutes / 60, minutes % 60);
		Chronometer total = (Chronometer)findViewById(R.id.chronometer3);
		Chronometer begin = (Chronometer)findViewById(R.id.chronometer1);
		Chronometer chronometer = (Chronometer)findViewById(R.id.chronometer2);
		long start_time = ((SmartWifiApplication)getApplication()).getStart_time();
		if (start_time > 0) {
			SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm");
			chronometer.setBase(start_time);
			chronometer.start();
			begin.setText(timestampFormat.format(((SmartWifiApplication)getApplication()).getTime_base()));
			total.setText(total_minutes);
			logout.setEnabled(true);
		} else {
			chronometer.stop();
			chronometer.setText("00:00");
			begin.setText("00:00");
			total.setText(total_minutes);
			logout.setEnabled(false);
		}
		
		int percent = (int)Math.round(minutes / (hours * 60.0) * 100);
        Bitmap widget = Bitmap.createBitmap(227, 227, Config.ARGB_8888);
        Canvas canvas = new Canvas(widget);
        canvas.drawColor(Color.TRANSPARENT);
        Paint p = new Paint();
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName,Typeface.BOLD);
        p.setColor(Color.GREEN);
        p.setTypeface(font);
        p.setTextSize(18);
        p.setStrokeWidth(2);
        
        Bitmap clock_back = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
        Bitmap back = Bitmap.createScaledBitmap(clock_back, 227, 227, true);
        canvas.drawBitmap(back, 0, 0, p);
        clock_back.recycle();
        back.recycle();
        canvas.drawText(percent + "%", 100, 192, p);
        double angle = (230 * percent / 100 - 115) * Math.PI / 180;
        canvas.drawLine(114, 114, Math.round(114 + 90 * Math.sin(angle)), Math.round(114 - 90 * Math.cos(angle)), p);
        
        ImageView clock = (ImageView)findViewById(R.id.imageView1);
        ((BitmapDrawable)clock.getDrawable()).getBitmap().recycle();
        clock.setImageBitmap(widget);
	}
}
