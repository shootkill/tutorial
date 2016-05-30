package com.example.androidsecret.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidsecret.util.Configure;
import com.example.androidsecret.util.MD5Util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class MyContext {

	public String getContactsJSONString(Context context){
		Cursor c =context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		String phoneNum;
		JSONArray array=new JSONArray();
		JSONObject obj=new JSONObject();
		while(c.moveToNext()){
			phoneNum=c.getString(c.getColumnIndex(Phone.NUMBER));
			if(phoneNum.startsWith("+86")){
				phoneNum.substring(3);
			}
			try {
				obj.put(Configure.KEY_PHONE_MD5, MD5Util.MD5(phoneNum));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(obj);
		}
		return array.toString();
		
	}
}
