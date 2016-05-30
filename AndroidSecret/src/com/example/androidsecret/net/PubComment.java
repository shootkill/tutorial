package com.example.androidsecret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class PubComment {
	public PubComment(String phone_md5,String token,String content,String msgId,final
			SuccessCallback successCallback,final FailCallback failCallback){
		new NetConnect(Configure.SERVER_URL, HttpMethod.POST, new NetConnect.SuccessCallback() {
			
			@Override
			public void onSuccess(String str) {
				try {
					JSONObject jsonObj=new JSONObject(str);
					switch (jsonObj.getInt(Configure.KEY_STATUS)) {
					case Configure.RESULT_STATUS_SUCCESS:
						if(successCallback!=null){
							successCallback.onSucccess();
						}
						break;
					case Configure.RESULT_STATUS_INVALID_TOKEN:
						if(failCallback!=null){
							failCallback.onFail(Configure.RESULT_STATUS_INVALID_TOKEN
									);
						}
						break;
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
		}, Configure.KEY_ACTION,Configure.ACTION_PUB_COMMENT
		,Configure.KEY_PHONE_MD5,phone_md5,
		Configure.KEY_TOKEN,token,Configure.KEY_CONTENT,content,
		Configure.KEY_MSG_ID,msgId);
	}

	public static interface SuccessCallback{
		void onSucccess();
	}
	public static interface FailCallback{
		void onFail(int errorCode);
	}
}
