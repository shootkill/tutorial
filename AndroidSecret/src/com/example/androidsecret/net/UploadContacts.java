package com.example.androidsecret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;

public class UploadContacts {

	public UploadContacts(String phone_MD5, String token, String contacts,
			final SuccessCallback successCallback,
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
									successCallback.onSuccess();
								}
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
							if(failCallback!=null){
								failCallback.onFail(Configure.RESULT_STATUS_FAIL);
							}
							e.printStackTrace();
						}
					}
				}, new NetConnect.FailCallback() {

					@Override
					public void onFail() {
						failCallback.onFail(Configure.RESULT_STATUS_FAIL);
					}
				}, Configure.KEY_ACTION, Configure.ACTION_UPLOAD_CONTACTS,
				Configure.KEY_PHONE_MD5, phone_MD5, Configure.KEY_TOKEN, token,
				Configure.KEY_CONTACTS, contacts);
	}

	public static interface SuccessCallback {
		void onSuccess();
	}

	public static interface FailCallback {
		void onFail(int errorCode);
	}
}
