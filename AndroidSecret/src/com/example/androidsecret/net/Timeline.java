package com.example.androidsecret.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class Timeline {

	public Timeline(String phone_md5,String token,int page,int perpage,final SuccessCallback successCallback,final FailCallback failCallback){
		
		new NetConnect(Configure.SERVER_URL, HttpMethod.POST, new NetConnect.SuccessCallback() {
			
			@Override
			public void onSuccess(String str) {
				JSONObject obj;
				try {
					obj = new JSONObject(str);
					switch (obj.getInt(Configure.KEY_STATUS)) {
					case Configure.RESULT_STATUS_SUCCESS:
						List<Message> list=new ArrayList<Message>();
						JSONArray jArray=obj.getJSONArray(Configure.KEY_TIMELINE);
						
						for(int i=0;i<jArray.length();i++){
							JSONObject jObj=jArray.getJSONObject(i);
							list.add(new Message(jObj.getString(Configure.KEY_MSG_ID),jObj.getString(Configure.KEY_PHONE_MD5) , jObj.getString(Configure.KEY_MSG)));
						
						}
						
						successCallback.onSuccess(obj.getInt(Configure.KEY_PAGE), obj.getInt(Configure.KEY_PERPAGE), list);
						break;
					case Configure.RESULT_STATUS_INVALID_TOKEN:
						if(failCallback!=null){
							failCallback.onFail(Configure.RESULT_STATUS_INVALID_TOKEN);
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
		}, Configure.KEY_ACTION,Configure.ACTION_TIMELINE,Configure.KEY_PHONE_MD5,phone_md5,
		Configure.KEY_TOKEN,token,Configure.KEY_PAGE,page+"",Configure.KEY_PERPAGE,perpage+"");
	
		
	}
	
	public static interface SuccessCallback{
		void onSuccess(int page,int perpage,List<Message> list);
	}
	
	public static interface FailCallback{
		void onFail(int errorCode);
	}
}
