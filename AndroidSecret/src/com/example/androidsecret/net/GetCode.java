package com.example.androidsecret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class GetCode {
	
	public GetCode(String phone,final SuccessCallback successCallback,final FailCallback failCallback){
		new NetConnect(Configure.SERVER_URL, HttpMethod.POST,
			new	NetConnect.SuccessCallback(){

				@Override
				public void onSuccess(String result) {
					try {
						JSONObject jsonObj=new JSONObject(result);
						switch (jsonObj.getInt(Configure.KEY_STATUS)) {
						case Configure.RESULT_STATUS_SUCCESS:
							if(successCallback!=null){
								successCallback.onSuccess();
							}
							break;

						default:
							if(failCallback!=null){
								failCallback.onFail();
							}
							break;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			
		} ,new NetConnect.FailCallback(){

			@Override
			public void onFail() {
				
				if(failCallback!=null){
					failCallback.onFail();
				}
			}
			
		} , Configure.KEY_ACTION, Configure.ACTION_GET_CODE, Configure.KEY_PHONE_NM,phone);
	}
	public static interface SuccessCallback{
		void onSuccess();
	}
	public static interface FailCallback{
		void onFail();
	}

}
