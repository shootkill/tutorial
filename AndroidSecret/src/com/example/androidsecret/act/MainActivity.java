package com.example.androidsecret.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidsecret.util.Configure;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		String cookie=Configure.getCachedToken(this);
		String phone_num=Configure.getCachedPhoneNum(this);
		Intent in;
		if(cookie!=null&&phone_num!=null){
			in=new Intent(MainActivity.this,TimelineActivity.class);
			in.putExtra(Configure.KEY_TOKEN, cookie);
			in.putExtra(Configure.KEY_PHONE_NM, phone_num);
			startActivity(in);
		}else{
			in=new Intent(this,LoginActivity.class);
			startActivity(in);
		}
		finish();
			
	}

	
}
