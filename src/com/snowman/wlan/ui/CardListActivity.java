package com.snowman.wlan.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.snowman.wlan.widget.PullToRefreshListView.OnRefreshListener;
import com.snowman.wlan.widget.PullToRefreshListView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CardListActivity extends Activity{
	public static final String CARDS_URL   = "http://wifika.sinaapp.com/getcards.php";
	public final static String NODE_CARDS  = "cards";
	public final static String NODE_CARD   = "card";
	public final static String NODE_ID     = "id";
	public final static String NODE_TITLE  = "title";
	public final static String NODE_PRICE  = "price";
	public final static String NODE_URL    = "url";
	public final static String NODE_SELLER = "seller";
	
	private static final int MESSAGE_FAILED		= 0;
	private static final int MESSAGE_SUCCESS	= 1;	

	private ProgressDialog progress;
	private Handler progressHandler;
	private Context context;
	
	private PullToRefreshListView lvCard;
	private CardListViewAdapter   lvCardAdapter;
	private List<WifiCard>        lvCardData = new ArrayList<WifiCard>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_list);
		setTitle("购 卡");		
		
		Button orderList = (Button) findViewById(R.id.orderList);
		orderList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CardListActivity.this, ShoppingActivity.class));
			}
		});
		
		initCardListView();
		getWifiCardData();
		
		this.context = this;
		this.progressHandler = new ProgressHandler();		
	}
	
	public void onStart(){
		super.onStart();
		//lvCardData.clear();
		//genTestData();
	}
	
	private void genTestData()
	{
		WifiCard cardTest = new WifiCard();
		cardTest.setCardId(1);
		cardTest.setTitle("全国无线");
		cardTest.setPrice((float)0.26);
		cardTest.setSeller("5A");
		cardTest.setUrl("http://www.baidu.com");
		
		lvCardData.add(cardTest);
		
		WifiCard cardTest2 = new WifiCard();
		cardTest2.setCardId(2);
		cardTest2.setTitle("全国 chinanet 账号 包月 wifi账号 电信wifi宽带 无线上网帐号");
		cardTest2.setPrice((float)0.49);
		cardTest2.setSeller("4A");
		cardTest2.setUrl("http://www.baidu.com");
		lvCardData.add(cardTest2);
		
		WifiCard cardTest3 = new WifiCard();
		cardTest3.setCardId(2);
		cardTest3.setTitle("全国 chinanet 账号 包月 wifi账号 电信wifi宽带 无线上网帐号");
		cardTest3.setPrice((float)0.49);
		cardTest3.setSeller("4A");
		cardTest3.setUrl("http://www.baidu.com");
		lvCardData.add(cardTest3);
		
		WifiCard cardTest4 = new WifiCard();
		cardTest4.setCardId(2);
		cardTest4.setTitle("全国 chinanet 账号 包月 wifi账号 电信wifi宽带 无线上网帐号");
		cardTest4.setPrice((float)0.49);
		cardTest4.setSeller("4A");
		cardTest4.setUrl("http://www.baidu.com");
		lvCardData.add(cardTest4);
		
		WifiCard cardTest5 = new WifiCard();
		cardTest5.setCardId(2);
		cardTest5.setTitle("全国 chinanet 账号 包月 wifi账号 电信wifi宽带 无线上网帐号");
		cardTest5.setPrice((float)0.49);
		cardTest5.setSeller("4A");
		cardTest5.setUrl("http://www.baidu.com");
		lvCardData.add(cardTest5);
		
		WifiCard cardTest6 = new WifiCard();
		cardTest6.setCardId(2);
		cardTest6.setTitle("全国 chinanet 账号 包月 wifi账号 电信wifi宽带 无线上网帐号");
		cardTest6.setPrice((float)0.49);
		cardTest6.setSeller("4A");
		cardTest6.setUrl("http://www.baidu.com");
		lvCardData.add(cardTest6);
	}
	
	private void initCardListView()
    {
        lvCardAdapter = new CardListViewAdapter(this, lvCardData, R.layout.card_listitem);        
       
        lvCard = (PullToRefreshListView)findViewById(R.id.wificard_listview);

        lvCard.setAdapter(lvCardAdapter); 
        lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      		
        		WifiCard wifiCard = null;        		

        		if(view instanceof TextView){
        			wifiCard = (WifiCard)view.getTag();
        		}else{
        			TextView tv = (TextView)view.findViewById(R.id.card_title);
        			wifiCard = (WifiCard)tv.getTag();
        		}
        		if(wifiCard == null) return;
        		
        		Intent intent = new Intent(CardListActivity.this, ShoppingActivity.class);
        		intent.putExtra("shopping_url", wifiCard.getUrl());
        		startActivity(intent);
        	}        	
		});
        
        lvCard.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO Do work to refresh the list here.
            	new Thread(new Runnable() {
        			@Override
        			public void run() {
        		        try {
        		    		HttpClient client = new DefaultHttpClient();
        		            HttpGet get = new HttpGet(CARDS_URL);
        		            HttpResponse response = client.execute(get);
        		            int statusCode = response.getStatusLine().getStatusCode();
        		            if (statusCode == 200) {
        		            	Message msg = new Message();
        		            	
        			            HttpEntity entity = response.getEntity();			            
        			            
        			            msg.obj = entity.getContent();
        			            msg.what = MESSAGE_SUCCESS;
        			            
        			            progressHandler.sendMessage(msg);
        			           
        		            } else {
        		            	sendMesssage(MESSAGE_FAILED, 0);
        		            }
        		        } catch (Exception e) {
        		            e.printStackTrace();
        		            sendMesssage(MESSAGE_FAILED, 0);
        		        }
        			}
        		}).start();
            }
        });
					
    }
	

	public void getWifiCardData() {
		progress = new ProgressDialog(this);
		progress.setMessage("正在获取列表，请等待...");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
		        try {
		    		HttpClient client = new DefaultHttpClient();
		            HttpGet get = new HttpGet(CARDS_URL);
		            HttpResponse response = client.execute(get);
		            int statusCode = response.getStatusLine().getStatusCode();
		            if (statusCode == 200) {
		            	Message msg = new Message();
		            	
			            HttpEntity entity = response.getEntity();			            
			            
			            msg.obj = entity.getContent();
			            msg.what = MESSAGE_SUCCESS;
			            
			            progressHandler.sendMessage(msg);
			           
		            } else {
		            	sendMesssage(MESSAGE_FAILED, 0);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            sendMesssage(MESSAGE_FAILED, 0);
		        }
			}
		}).start();
	}
	
	public void sendMesssage(int what, int value) {
        Message message = new Message();
        message.what = what;
        message.arg1 = value;
        progressHandler.sendMessage(message);
    }
	
	class ProgressHandler extends Handler {
		WifiCard card = null;
		@Override
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case MESSAGE_FAILED:
        		Toast.makeText(context, "网络访问失败", Toast.LENGTH_LONG).show();
        		progress.dismiss();
        		break;
        	case MESSAGE_SUCCESS:
        		XmlPullParser xmlParser = Xml.newPullParser();
                try {        	
                    xmlParser.setInput((InputStream)msg.obj, "UTF-8");
                    int evtType=xmlParser.getEventType();
        			while(evtType!=XmlPullParser.END_DOCUMENT){ 
        	    		String tag = xmlParser.getName(); 
        			    switch(evtType){ 
        			    	case XmlPullParser.START_TAG:
        			    		if(tag.equalsIgnoreCase(NODE_CARDS))
        			    		{
        			    			lvCardData.clear();
        			    		}
        			    		else if(tag.equalsIgnoreCase(NODE_CARD))
        			    		{
        			    			card = new WifiCard();
        			    			if(card != null)
        			    			{
        			    				card.setCardId(Integer.valueOf(xmlParser.getAttributeValue(0)));
        			    				card.setTitle(xmlParser.getAttributeValue(1));
        			    				card.setSeller(xmlParser.getAttributeValue(2));
        			    				card.setPrice(Float.valueOf(xmlParser.getAttributeValue(3)));
        			    				card.setUrl(xmlParser.getAttributeValue(4));        			    				
        			    			}
        			    		} 
        			    		break;
        			    	case XmlPullParser.END_TAG:		
        				       	if (tag.equalsIgnoreCase(NODE_CARD) && card!=null) { 
        				       		lvCardData.add(card); 
        				       	}
        				       	break; 
        			    }
        			    evtType=xmlParser.next();
        			}		
                } catch (XmlPullParserException e) {
        			e.printStackTrace();
                }catch (IOException e){
                	e.printStackTrace();
                }
                
                CardListActivity.this.lvCardAdapter.notifyDataSetChanged();
                CardListActivity.this.lvCard.onRefreshComplete();
        		if(progress != null){
        			progress.dismiss();
        			progress = null;
        		}
        		break;        	
        	}
		}
	} 
}
