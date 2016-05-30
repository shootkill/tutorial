package com.example.androidsecret.act;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidsecret.R;
import com.example.androidsecret.id.MyContext;
import com.example.androidsecret.net.Message;
import com.example.androidsecret.net.Timeline;
import com.example.androidsecret.net.UploadContacts;
import com.example.androidsecret.net.UploadContacts.FailCallback;
import com.example.androidsecret.net.UploadContacts.SuccessCallback;
import com.example.androidsecret.util.Configure;
import com.example.androidsecret.util.MD5Util;

public class TimelineActivity extends ListActivity {
	private String phone_num;
	private String token;
	
	TimelineMessageListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timln);
		adapter=new TimelineMessageListAdapter(this);
		setListAdapter(adapter);
		phone_num=getIntent().getStringExtra(Configure.KEY_PHONE_NM);
		token=getIntent().getStringExtra(Configure.KEY_TOKEN);
		
		new UploadContacts(MD5Util.MD5(phone_num), token,new MyContext().getContactsJSONString(this), new SuccessCallback() {
			
			@Override
			public void onSuccess() {
				System.out.println("success");
			}
		}, new FailCallback(){

			@Override
			public void onFail(int errorCode) {
				switch (errorCode) {
				case Configure.RESULT_STATUS_INVALID_TOKEN:
					Intent in=new Intent(TimelineActivity.this,LoginActivity.class);
					startActivity(in);
					finish();
					break;

				default:
					System.out.println("fail");
					break;
				}
			}
			
		});
		
		final ProgressDialog pd=ProgressDialog.show(this, "connectiong", "please wait");
		
		new Timeline(MD5Util.MD5(phone_num), token, 1, 20, new Timeline.SuccessCallback() {
			
			@Override
			public void onSuccess(int page, int perpage, List<Message> list) {
				
				pd.dismiss();
				adapter.clean();
				adapter.addMsg(list);
				
				
			}
		}, new Timeline.FailCallback() {
			
			@Override
			public void onFail(int errorCode) {
				if(errorCode==Configure.RESULT_STATUS_INVALID_TOKEN){
					Intent in=new Intent(TimelineActivity.this,LoginActivity.class);
					startActivity(in);
					finish();
				}
				pd.dismiss();
				Toast.makeText(TimelineActivity.this, "fail to get message", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.tml_menu_publish:
			Intent i=new Intent(TimelineActivity.this,PublishActivity.class);
			i.putExtra(Configure.KEY_PHONE_MD5, MD5Util.MD5(phone_num));
			i.putExtra(Configure.KEY_TOKEN, token);
			startActivityForResult(i, 0);
			break;

		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public  void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Message msg=adapter.getItem(position);
		
		Intent in=new Intent(this,MessageActivity.class);
		in.putExtra(Configure.KEY_MSG, msg.getMsg());
		in.putExtra(Configure.KEY_MSG_ID, msg.getMsgId());
		in.putExtra(Configure.KEY_PHONE_MD5, msg.getPhone_md5());
		in.putExtra(Configure.KEY_TOKEN, token);
		startActivity(in);
		
	}

}
