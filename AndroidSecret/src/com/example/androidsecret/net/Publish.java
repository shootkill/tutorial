package com.example.androidsecret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class Publish {
	public  Publish (String phone_md5,String msg,String token,final SuccessCallback successCallback
			,final FailCallback failCallback){

		new NetConnect(Configure.SERVER_URL	, HttpMethod.POST, new NetConnect.SuccessCallback() {
			
			@Override
			public void onSuccess(String str) {
				try {
					JSONObject jsonObj=new JSONObject(str);
					switch (jsonObj.getInt(Configure.KEY_STATUS)) {
					case Configure.RESULT_STATUS_SUCCESS:
						if(successCallback!=null){
							successCallback.onSuccess();
						}
						
						break;
					case Configure.RESULT_STATUS_INVALID_TOKEN:
						if(failCallback!=null){
							failCallback.onFail(Configure.RESULT_STATUS_INVALID_TOKEN);
						}
					default:
						if(failCallback!=null){
							failCallback.onFail(Configure.RESULT_STATUS_FAIL);
						}
						break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(failCallback!=null){
						failCallback.onFail(Configure.RESULT_STATUS_FAIL);
					}
				}
				
			}
		}, new NetConnect.FailCallback() {
			
			@Override
			public void onFail() {
				if(failCallback!=null){
					failCallback.onFail(Configure.RESULT_STATUS_FAIL);
				}
			}
		}, Configure.KEY_ACTION,Configure.ACTION_PUBLISH,Configure.KEY_PHONE_MD5,phone_md5,Configure.KEY_TOKEN,token,
		Configure.KEY_MSG,msg);
		
	}
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	public static interface FailCallback{
		void onFail(int errorCode);
	}
	
}
