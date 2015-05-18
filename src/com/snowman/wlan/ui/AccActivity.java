package com.snowman.wlan.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AccActivity extends Activity {

	private LinearLayout cmcc;
	private LinearLayout telecom;
	private LinearLayout wangwang;
	private static final int DIALOG_CMCC = 1;
	private static final int DIALOG_TELECOM = 2;
	private static final int DIALOG_WANGWANG = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		setTitle("账户");

		cmcc = (LinearLayout) findViewById(R.id.cmcc);
		cmcc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_CMCC);
			}
		});

		telecom = (LinearLayout) findViewById(R.id.telecom);
		telecom.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_TELECOM);
			}
		});
		
		/*
		wangwang = (LinearLayout) findViewById(R.id.wangwang);
		wangwang.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreatDialog(DIALOG_WANGWANG);
			}
		});
		*/

	}

	public void CreatDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(AccActivity.this);
		switch (id) {
		case DIALOG_CMCC:
		{
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.cmcc_dialog,
					null);
			final SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(AccActivity.this);
			final EditText userName = (EditText) textEntryView
					.findViewById(R.id.dl3_et1);
			final EditText password = (EditText) textEntryView
					.findViewById(R.id.dl3_et2);
			userName.setText(prefs.getString("cmcc_user", ""));
			password.setText(prefs.getString("cmcc_password", ""));
			
			builder.setIcon(R.drawable.wlan_icon);
			builder.setTitle("移动账户");
			builder.setView(textEntryView);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							prefs.edit()
									.putString("cmcc_user",
											userName.getText().toString())
									.putString("cmcc_password",
											password.getText().toString())
									.commit();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			builder.create().show();
			break;
		}
		case DIALOG_TELECOM:
		{
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.telecom_dialog,
					null);
			final SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(AccActivity.this);
			final EditText userName = (EditText) textEntryView
					.findViewById(R.id.d2_et1);
			final EditText password = (EditText) textEntryView
					.findViewById(R.id.d2_et2);
			userName.setText(prefs.getString("ct_user", ""));
			password.setText(prefs.getString("ct_password", ""));
			
			builder.setIcon(R.drawable.wlan_icon);
			builder.setTitle("电信账户");
			builder.setView(textEntryView);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							prefs.edit()
									.putString("ct_user",
											userName.getText().toString())
									.putString("ct_password",
											password.getText().toString())
									.commit();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			builder.create().show();
			break;
		}
		case DIALOG_WANGWANG:
		{
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.wangwang_dialog,
					null);
			final SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(AccActivity.this);
			final EditText userName = (EditText) textEntryView
					.findViewById(R.id.wangwang_user);
			userName.setText(prefs.getString("wangwang_user", ""));
			
			builder.setIcon(R.drawable.wlan_icon);
			builder.setTitle("旺旺账户");
			builder.setView(textEntryView);
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							prefs.edit()
									.putString("wangwang_user",
											userName.getText().toString())
									.commit();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			builder.create().show();
			break;
		}
		}
	}
}