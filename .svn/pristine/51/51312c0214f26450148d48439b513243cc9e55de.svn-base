package com.snowman.wlan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class SettingSetActivity extends Activity {

	private ImageButton back;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_set);
		setTitle("œµÕ≥…Ë÷√");
		
		init();}
		
		private void init()
		{
		
	    View view1 = findViewById(R.id.setting_set);
	    back = (ImageButton) view1.findViewById(R.id.backbutton);
	    back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(SettingSetActivity.this, MainActivity.class));
				
				
			
				
			}
		});	
		
	}

}