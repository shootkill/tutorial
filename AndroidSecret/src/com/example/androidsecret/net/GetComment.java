package com.example.androidsecret.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class GetComment {
	public GetComment(String phone_md5, String token, String msgId, int page,
			int perpage, final SuccessCallback successCallback,
			final FailCallback failCallback) {
		new NetConnect(Configure.SERVER_URL, HttpMethod.POST,
				new NetConnect.SuccessCallback() {

					@Override
					public void onSuccess(String str) {
						try {
							JSONObject obj = new JSONObject(str);
							switch (obj.getInt(Configure.KEY_STATUS)) {
							case Configure.RESULT_STATUS_SUCCESS:
								if(successCallback!=null){
								JSONArray jsonArray=obj.getJSONArray(Configure.KEY_COMMENTS);
								List<Comment> list=new ArrayList<Comment>();
								JSONObject jsonObj;
								for(int i = 0;i<jsonArray.length();i++){
									jsonObj=jsonArray.getJSONObject(i);
									list.add(new Comment(jsonObj.getString(Configure.KEY_PHONE_MD5), Configure.KEY_CONTENT));
								}
								successCallback.onSuccess(obj.getString(Configure.KEY_MSG_ID), obj.getInt(Configure.KEY_PAGE), obj.getInt(Configure.KEY_PERPAGE), list);
								}
								break;
							case Configure.RESULT_STATUS_INVALID_TOKEN:
								if (failCallback != null) {
									failCallback
											.onFail(Configure.RESULT_STATUS_INVALID_TOKEN);
								}
								break;
							default:
								break;
							}
						} catch (JSONException e) {
							e.printStackTrace();
							if (failCallback != null) {
								failCallback
										.onFail(Configure.RESULT_STATUS_FAIL);
							}
						}

					}
				}, new NetConnect.FailCallback() {

					@Override
					public void onFail() {
						if (failCallback != null) {
							failCallback.onFail(Configure.RESULT_STATUS_FAIL);
						}
					}
				}, Configure.KEY_ACTION, Configure.ACTION_COMMENT,
				Configure.KEY_TOKEN, token, Configure.KEY_MSG_ID, msgId,
				Configure.KEY_PAGE, page + "", Configure.KEY_PERPAGE, perpage
						+ "");
	}

	public static interface SuccessCallback {
		void onSuccess(String msgId, int page, int perpage,
				List<Comment> comments);

	}

	public static interface FailCallback {
		void onFail(int errorCode);
	}
}
