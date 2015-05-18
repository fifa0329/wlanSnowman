package com.snowman.wlan.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TabWidget;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.view.*;

public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
	public TabHost mth;
	public RadioGroup mainRadioGroup;
	public View msgTitle; //Õ∑≤øŒª÷√
	public static final String TAB_HOME=" ◊“≥";
	public static final String TAB_ACC="’À∫≈";
    public static final String TAB_SET="…Ë÷√";
    public static final String TAB_HELP="∞Ô÷˙";
    public static final String TAB_CARD="π∫ø®";
	
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        msgTitle = findViewById(R.id.msg_title); 
        mth = this.getTabHost();
        
        TabSpec ts1=mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
        ts1.setContent(new Intent(MainActivity.this,HomeActivity.class));
        mth.addTab(ts1);
        
        TabSpec ts2=mth.newTabSpec(TAB_ACC).setIndicator(TAB_ACC);
        ts2.setContent(new Intent(MainActivity.this,AccActivity.class));
        mth.addTab(ts2); 
        
        TabSpec ts4=mth.newTabSpec(TAB_CARD).setIndicator(TAB_CARD);
        //ts4.setContent(new Intent(MainActivity.this,HelpActivity.class));
        ts4.setContent(new Intent(MainActivity.this,CardListActivity.class));
        mth.addTab(ts4); 
        
        TabSpec ts3=mth.newTabSpec(TAB_SET).setIndicator(TAB_SET);
        ts3.setContent(new Intent(MainActivity.this,SetActivity.class));
        mth.addTab(ts3);
        
        
        this.mainRadioGroup=(RadioGroup)findViewById(R.id.main_radio);
        mainRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				switch(checkedId){
				case R.id.radio_button0:
					mth.setCurrentTabByTag(TAB_HOME);
					msgTitle.setVisibility(View.GONE);
					break;
				case R.id.radio_button1:
					mth.setCurrentTabByTag(TAB_ACC);
					msgTitle.setVisibility(View.GONE);
					break;
				case R.id.radio_button4:
					mth.setCurrentTabByTag(TAB_CARD);
					msgTitle.setVisibility(View.GONE);
					break;
				case R.id.radio_button5:
					mth.setCurrentTabByTag(TAB_SET);
					msgTitle.setVisibility(View.GONE);
					break;
				
				default:
				    break;
				
				}
			}
		});
    }
}