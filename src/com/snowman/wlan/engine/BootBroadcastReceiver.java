package com.snowman.wlan.engine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class BootBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("serviceRunning", true)) {
            context.startService(new Intent(context, WLANEngineService.class));
		}
	}
}
