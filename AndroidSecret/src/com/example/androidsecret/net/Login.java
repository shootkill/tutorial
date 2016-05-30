package com.example.androidsecret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class Login {

	public Login(String phone_md5,String code,final SuccessCallback successCallback,final FailCallback failCallback){
		new NetConnect(Configure.SERVER_URL, HttpMethod.POST, new NetConnect.SuccessCallback() {
			
			@Override
			public void onSuccess(String str) {
				try {
					JSONObject json=new JSONObject(str);
					if(json.getInt(Configure.KEY_STATUS)==Configure.RESULT_STATUS_SUCCESS){
						if(successCallback!=null){
							successCallback.onSuccess(json.getString(Configure.KEY_TOKEN));
						}
					}else{
						if(failCallback!=null){
							failCallback.onFail();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(failCallback!=null){
						failCallback.onFail();
					}
				}
				
				
			}
		}		
		, new NetConnect.FailCallback() {
			
			@Override
			public void onFail() {
				if(failCallback!=null){
					failCallback.onFail();
				}
				
			}
		}
	, Configure.KEY_ACTION,Configure.ACTION_LOGIN,Configure.KEY_PHONE_MD5,phone_md5,Configure.KEY_CODE,code);
		
	}
	
	public static interface SuccessCallback{
		void onSuccess(String token);
	}
	public static interface FailCallback{
		void onFail();
	}
	
}
