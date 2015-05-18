package com.snowman.wlan.engine;

import com.snowman.wlan.engine.monitor.CellTrigger;
import com.snowman.wlan.engine.monitor.ScreenTrigger;
import com.snowman.wlan.engine.monitor.TimerTrigger;
import com.snowman.wlan.engine.monitor.WifiTrigger;
import com.snowman.wlan.ui.AuthDialog;
import com.snowman.wlan.ui.MainActivity;
import com.snowman.wlan.ui.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;

public class WLANEngineService extends Service {
	private static final int MESSAGE_TIMER			= 0;
	private static final int MESSAGE_SCREEN_ON		= 1;
	private static final int MESSAGE_SCREEN_OFF		= -1;
	private static final int MESSAGE_CELL_ON		= 3;
	private static final int MESSAGE_CELL_OFF		= -3;
	private static final int MESSAGE_WIFI_CONNECTED		= 4;
	private static final int MESSAGE_WIFI_DISCONNECTED	= -4;
	
	public static final String KEY_DEBUG_MODE = "debugMode";
	public static final String KEY_DEBUG_SOUND = "debugSound";
	public static final String KEY_SERVICE_MODE = "serviceMode";
	public static final String KEY_APP_LIST = "appList";
	
	private static final int SERVICE_MODE_AUTO		= 1;
	
    private volatile Looper mServiceLooper;
    private volatile Handler mServiceHandler;
	private NotificationManager mNM;
	
	public static final int NOTIFICATION_DEBUG  = 1;
	public static final int NOTIFICATION_AUTH	= 2;
	public static final int NOTIFICATION_STATUS = 3;
	
	public static final int STATUS_INIT 		= 1;
	public static final int STATUS_INSIDE 		= 2;
	public static final int STATUS_OUTSIDE 		= 3;
	
	private SharedPreferences prefs = null;
	private boolean debugMode = false;
	private String debugString = null;
	
	private ScreenTrigger screenTrigger;
	private CellTrigger cellTrigger;
	private WifiTrigger wifiTrigger;
	private TimerTrigger timerTrigger;
	private WifiAction wifiAction;
    
	public boolean debugModeOn() {
		return debugMode;
	}
	
	public void debugOutput(String text) {
		Log.d("WLANEngine", text);
		if (debugMode) {
			if (!text.equals(debugString)) {
				debugString = text;
				showDebugNotification(text);
			}
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		cleanUp();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		debugMode = prefs.getBoolean(KEY_DEBUG_MODE, false);
		debugOutput("启动WLAN优选服务");
		showStatusNotification(STATUS_INIT);
		
		int serviceMode = Integer.parseInt(prefs.getString(KEY_SERVICE_MODE, "1"));
		Runnable starter = null;
		switch (serviceMode) {
		case SERVICE_MODE_AUTO:
			mServiceHandler = new AutoServiceHandler(mServiceLooper);
	        screenTrigger = new ScreenTrigger(WLANEngineService.this, mServiceHandler, MESSAGE_SCREEN_ON, MESSAGE_SCREEN_OFF);
	        cellTrigger = new CellTrigger(WLANEngineService.this, mServiceHandler, MESSAGE_CELL_ON, MESSAGE_CELL_OFF, 5000);
	        wifiTrigger = new WifiTrigger(WLANEngineService.this, mServiceHandler, MESSAGE_WIFI_CONNECTED, MESSAGE_WIFI_DISCONNECTED);
	        timerTrigger = new TimerTrigger(WLANEngineService.this, mServiceHandler, MESSAGE_TIMER, 30000, false);
	        starter = new Runnable() {
	            @Override
	            public void run() {
	    	        screenTrigger.enableTrigger();
	    	        wifiTrigger.enableTrigger();
	            }
	        };
			break;
		}
		
        mServiceHandler.postDelayed(starter, intent.getIntExtra("delay", 0) * 1000);
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
        		Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        wifiAction = WifiAction.getInstance(WLANEngineService.this);
	}

	private void cleanUp() {
		if (screenTrigger != null) screenTrigger.disableTrigger();
		if (cellTrigger != null) cellTrigger.disableTrigger();
		if (wifiTrigger != null) wifiTrigger.disableTrigger();
		if (timerTrigger != null) timerTrigger.disableTrigger();
		hideDebugNotification();
		hideAuthNotification();
		hideStatusNotification();
	}
	
	@Override
	public void onDestroy() {
		cleanUp();
		mServiceLooper.quit();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
    private void showDebugNotification(String text) {
        Notification notification = new Notification(R.drawable.wlan_icon, text,
                System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, getString(R.string.app_name),
                       text, contentIntent);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        if (prefs.getBoolean(KEY_DEBUG_SOUND, false)) {
        	notification.defaults |= Notification.DEFAULT_SOUND;
        }
        
        startForeground(NOTIFICATION_DEBUG, notification);
    }
    
    private void hideDebugNotification() {
    	stopForeground(true);
    }
    
    private void hideAuthNotification() {
    	((SmartWifiApplication)getApplication()).clearStart_time();
        mNM.cancel(NOTIFICATION_AUTH);
    }
    
    private void showStatusNotification(int status) {
    	if (prefs.getBoolean("disableSystemIcon", false)) {
    		return;
    	}
    	
    	String text = "智能WiFi助手运行中";
    	int icon = 0;
    	switch (status) {
    	case STATUS_INIT:
    		icon = R.drawable.wlan_icon;
    		break;
    	case STATUS_INSIDE:
    		icon = R.drawable.wireless;
    		break;
    	case STATUS_OUTSIDE:
    		icon = R.drawable.wlan_icon;
    		break;
    	}
    	
        Notification notification = new Notification(icon, text,
                System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, "智能WiFi助手",
                       text, contentIntent);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNM.notify(NOTIFICATION_STATUS, notification);
    }
    
    private void hideStatusNotification() {
        mNM.cancel(NOTIFICATION_STATUS);
    }
    
    private final class AutoServiceHandler extends Handler {
        public AutoServiceHandler(Looper looper) {
            super(looper);
        }
        
        @Override
        public void handleMessage(Message msg) {
        	Log.d("WLANEngine", "MESSAGE:" + msg.what);
        	switch (msg.what) {
        	case MESSAGE_TIMER:
        		if (!screenTrigger.isStateOn()) {
        			wifiAction.turnOffWifi();
        		}
        		timerTrigger.disableTrigger();
        		break;
        	case MESSAGE_SCREEN_ON:
        		cellTrigger.enableTrigger();
        		break;
        	case MESSAGE_SCREEN_OFF:
        		cellTrigger.disableTrigger();
        		showStatusNotification(STATUS_INIT);
        		if (wifiTrigger.isStateOn()) {
	        		timerTrigger.disableTrigger();
	        		timerTrigger.enableTrigger();
        		} else {
        			wifiAction.turnOffWifi();
        		}
        		break;
        	case MESSAGE_CELL_ON:
        		showStatusNotification(STATUS_INSIDE);
        		wifiAction.turnOnWifi();
        		break;
        	case MESSAGE_CELL_OFF:
        		showStatusNotification(STATUS_OUTSIDE);
        		if (!wifiTrigger.isStateOn()) {
        			wifiAction.turnOffWifi();
        		}
        		break;
        	case MESSAGE_WIFI_CONNECTED:
        		if (!(cellTrigger.isTriggerStarted() && cellTrigger.isStateOn())) {
        			cellTrigger.rememberUserAp();
        		}
        		if (wifiAction.isCmccAp()) {
        			Intent intent = new Intent(WLANEngineService.this, AuthDialog.class);
        			intent.putExtra("carrier", SmartWifiApplication.CARRIER_CMCC);
        			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        			WLANEngineService.this.startActivity(intent);
        		} else if (wifiAction.isCtAp()) {
        			Intent intent = new Intent(WLANEngineService.this, AuthDialog.class);
        			intent.putExtra("carrier", SmartWifiApplication.CARRIER_CT);
        			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        			WLANEngineService.this.startActivity(intent);
        		}
        		break;
        	case MESSAGE_WIFI_DISCONNECTED:
        		hideAuthNotification();
        		sendBroadcast(new Intent(AuthDialog.class.getName()));  
        		if (!(cellTrigger.isTriggerStarted() && cellTrigger.isStateOn())) {
        			wifiAction.turnOffWifi();
        		}
        		break;
        	}
        }
    }
}
