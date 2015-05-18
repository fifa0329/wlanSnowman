package com.snowman.wlan.ui;

import com.snowman.wlan.engine.SmartWifiApplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ResetActivity extends Activity {
	private Button back, reset;
	private String[] yunyingshang = new String[] { "中国移动  WLAN", "中国电信 WLAN" };
	private String[] taocan = new String[] {};
	private String[][] pandc = new String[][] { { "15小时套餐", "40小时套餐", "200小时套餐" }, { "15小时套餐", "40小时套餐", "200小时套餐" } };
	private Spinner sp;
	private Spinner sp2;
	ArrayAdapter<String> adapter;
	ArrayAdapter<String> adapter2;
	
	private SharedPreferences prefs = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset);
		prefs = PreferenceManager.getDefaultSharedPreferences(ResetActivity.this);
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, yunyingshang);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp = (Spinner) findViewById(R.id.yunyingshang);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(selectListener);
		sp.setSelection(prefs.getInt("carrier", SmartWifiApplication.CARRIER_CMCC) - 1);

		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, taocan);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2 = (Spinner) findViewById(R.id.taocan);
		sp2.setAdapter(adapter2);
		
		back = (Button) findViewById(R.id.button3);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		reset = (Button) findViewById(R.id.button2);
		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int hours = 15;
				if (sp2.getSelectedItemId() == 0) {
					hours = 15;
				} else if (sp2.getSelectedItemId() == 1) {
					hours = 40;
				} else if (sp2.getSelectedItemId() == 2) {
					hours = 200;
				}
				if (sp.getSelectedItemId() == 0) {
					Editor editor = prefs.edit();
					editor.putInt("carrier", SmartWifiApplication.CARRIER_CMCC);
					editor.putInt("cmcc-hours", hours);
					editor.commit();
				} else if (sp.getSelectedItemId() == 1) {
					Editor editor = prefs.edit();
					editor.putInt("carrier", SmartWifiApplication.CARRIER_CT);
					editor.putInt("ct-hours", hours);
					editor.commit();
				}
				finish();
			}
		});		
	}

	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView parent, View v, int position,
				long id) {
			int pos = sp.getSelectedItemPosition();
			adapter2 = new ArrayAdapter<String>(ResetActivity.this,
					android.R.layout.simple_spinner_item, pandc[pos]);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp2.setAdapter(adapter2);
			int hours = 15;
			if (pos == 0) {
				hours = prefs.getInt("cmcc-hours", 15);
			} else if (pos == 1) {
				hours = prefs.getInt("ct-hours", 15);
			}
			sp2.setSelection(adapter2.getPosition(hours + "小时套餐"));
		}

		public void onNothingSelected(AdapterView arg0) {
		}
	};
}
