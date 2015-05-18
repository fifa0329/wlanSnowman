package com.snowman.wlan.ui;

import java.util.List;
import com.snowman.wlan.ui.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CardListViewAdapter extends BaseAdapter {
	private Context 					context;//����������
	private List<WifiCard> 				listItems;//���ݼ���
	private LayoutInflater 				listContainer;//��ͼ����
	private int 						itemViewResource;//�Զ�������ͼԴ 
	
	static class ListItemView{				//�Զ���ؼ�����  
	        public TextView title;  
		    public TextView price;
		    public TextView rate;  
	 }  

	/**
	 * ʵ����Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public CardListViewAdapter(Context context, List<WifiCard> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//������ͼ����������������
		this.itemViewResource = resource;
		this.listItems = data;
	}
	
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}
	
	/**
	 * ListView Item����
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.d("method", "getView");
		
		//�Զ�����ͼ
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//��ȡ�ؼ�����
			listItemView.title = (TextView)convertView.findViewById(R.id.card_title);
			listItemView.price = (TextView)convertView.findViewById(R.id.card_price);
			listItemView.rate= (TextView)convertView.findViewById(R.id.card_rate);
			
			//���ÿؼ�����convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
      
		//�������ֺ�ͼƬ
		WifiCard card = listItems.get(position);

		listItemView.title.setText(card.getTitle());
		listItemView.title.setTag(card);//�������ز���(ʵ����)
		
		listItemView.price.setText(Float.toString(card.getPrice()));
		listItemView.rate.setText(card.getSeller());
		
		return convertView;
	}	
}
