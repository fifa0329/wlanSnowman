package com.snowman.wlan.engine;

import java.util.Calendar;

import com.snowman.wlan.ui.HomeActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class SmartWifiApplication extends Application {
	public static final int CARRIER_CMCC 	= 1;
	public static final int CARRIER_CT 		= 2;
	
	private long start_time = 0;
	private long time_base = 0;
	private long delay_start = 0;
	private int carrier = CARRIER_CMCC;

	public void setStart_time(int carrier) {
		this.carrier = carrier;
		start_time = SystemClock.elapsedRealtime();
		time_base = System.currentTimeMillis();
		sendBroadcast(new Intent(HomeActivity.class.getName()));
	}

	public long getStart_time() {
		return start_time;
	}

	public void clearStart_time() {
		if (start_time > 0) {
			long seconds = (SystemClock.elapsedRealtime() - start_time) / 1000;
			long minutes = seconds / 60;
			if (seconds % 60 > 0) {
				minutes ++;
			}
			
			Calendar now = Calendar.getInstance();
			int month = now.get(Calendar.YEAR) * 100 + now.get(Calendar.MONTH);
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			if (carrier == CARRIER_CMCC) {
				if (month != prefs.getInt("cmcc-month", 0)) {
					Editor editor = prefs.edit();
					editor.putInt("cmcc-month", month);
					editor.putLong("cmcc-minutes", minutes);
					editor.commit();
				} else {
					minutes += prefs.getLong("cmcc-minutes", 0);
					prefs.edit().putLong("cmcc-minutes", minutes).commit();
				}
			} else if (carrier == CARRIER_CT) {
				if (month != prefs.getInt("ct-month", 0)) {
					Editor editor = prefs.edit();
					editor.putInt("ct-month", month);
					editor.putLong("ct-minutes", minutes);
					editor.commit();
				} else {
					minutes += prefs.getLong("ct-minutes", 0);
					prefs.edit().putLong("ct-minutes", minutes).commit();
				}
			}
			
			start_time = 0;
			time_base = 0;
			sendBroadcast(new Intent(HomeActivity.class.getName()));
		}
	}

	public long getTime_base() {
		return time_base;
	}

	public void setDelay_start() {
		this.delay_start = SystemClock.elapsedRealtime();
	}

	public long getDelay_start() {
		return delay_start;
	}

	public int getCarrier() {
		return carrier;
	}
}
