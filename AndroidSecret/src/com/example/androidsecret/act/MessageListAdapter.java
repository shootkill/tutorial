package com.example.androidsecret.act;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.androidsecret.R;
import com.example.androidsecret.net.Comment;

public class MessageListAdapter extends BaseAdapter{

	private List<Comment> list;
	private Context context;
	
	public MessageListAdapter(Context context){
		this.context=context;
	}
	
	public Context getContext() {
		return context;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Comment getItem(int position) {
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
		Comment comm=getItem(position);
		ListCell lc=(ListCell) convertView.getTag();
		lc.getTextView().setText(comm.getComment());;
		return convertView;
	}
	
	public void addAll(List<Comment> list){
		this.list=list;
		notifyDataSetChanged();
	}
	
	public void clean(){
		list.clear();
		notifyDataSetChanged();
	}
	
	private static class ListCell{
		public ListCell(TextView textView){
			this.textView=textView;
		}
		private TextView textView;
		public TextView getTextView() {
			return textView;
		}
	}
	
}
