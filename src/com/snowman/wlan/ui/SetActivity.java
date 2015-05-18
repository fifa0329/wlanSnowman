package com.snowman.wlan.ui;

import com.snowman.wlan.engine.Downloader;
import com.snowman.wlan.engine.WLANEngineService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class SetActivity extends Activity {

	private LinearLayout Xitong;
	private LinearLayout feedback;
	private LinearLayout share;
	/** 单项选择列表框 **/
	private static final int DIALOG_4 = 5;
	final String[] mItems = { "短信", "微博", "飞信", "其他" };
	private LinearLayout Guanyu;
	private LinearLayout help;
	int mSingleChoiceID = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		setTitle("设置");

		share = (LinearLayout) findViewById(R.id.linearLayout5);
		share.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//CreatDialog(DIALOG_4);
				new Downloader(SetActivity.this).shareByMessage();
			}
		});

		init();
	}

	private void init() {
		View view1 = findViewById(R.id.setting);
		Xitong = (LinearLayout) view1.findViewById(R.id.linearLayout1);
		Guanyu = (LinearLayout) view1.findViewById(R.id.linearLayout6);
		feedback = (LinearLayout) view1.findViewById(R.id.linearLayout4);
		help = (LinearLayout) view1.findViewById(R.id.help);

		Xitong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetActivity.this, SystemSwitchDialog.class));
			}
		});

		Guanyu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SetActivity.this, AboutActivity.class));
			}
		});
		
		help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SetActivity.this, HelpActivity.class));
			}
		});

		feedback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SetActivity.this,
						FeedbackActivity.class));
			}
		});
		
		LinearLayout checkNewVersion = (LinearLayout) view1.findViewById(R.id.linearLayout2);
		checkNewVersion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Downloader(SetActivity.this).checkNewVersion();
			}
		});
	}

	public void CreatDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
		switch (id) {
		case DIALOG_4:
			mSingleChoiceID = -1;
			builder.setIcon(R.drawable.wlan_icon);
			builder.setTitle("使用以下方式操作");
			builder.setSingleChoiceItems(mItems, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							mSingleChoiceID = whichButton;
							showDialog("你选择的id为" + whichButton + " , "
									+ mItems[whichButton]);
						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							if (mSingleChoiceID > 0) {
								showDialog("你选择的是" + mSingleChoiceID);
							}
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			break;
		}
		builder.create().show();
	}

	private void showDialog(String str) {
		new AlertDialog.Builder(SetActivity.this).setMessage(str).show();
	}

	public void run() {
		int Progress = 0;
	}
}
