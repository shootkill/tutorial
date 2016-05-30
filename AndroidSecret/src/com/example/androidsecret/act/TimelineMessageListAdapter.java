package com.example.androidsecret.act;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.androidsecret.R;
import com.example.androidsecret.net.Message;

public class TimelineMessageListAdapter extends BaseAdapter{

	private Context context;
	private List<Message> list=new ArrayList<Message>();
	
	public TimelineMessageListAdapter(Context context){
		this.context=context;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void addMsg(List<Message> data){
		list.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clean(){
		list.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Message getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_timln, null);
			convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.list_timln_tv)));
		}
		
		ListCell lc=(ListCell) convertView.getTag();
		Message msg=getItem(position);
		lc.getTvCellLable().setText(msg.getMsg());
		return convertView;
	}
	
	private static class ListCell{
		public ListCell(TextView tvCellLable){
			this.tvCellLable=tvCellLable;
		}
		public TextView getTvCellLable() {
			return tvCellLable;
		}
		private TextView tvCellLable;
		
	}

}
