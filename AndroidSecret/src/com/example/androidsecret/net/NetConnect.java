package com.example.androidsecret.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

import com.example.androidsecret.util.Configure;

public class NetConnect {

	public NetConnect(final String url, final HttpMethod method,
			final SuccessCallback success, final FailCallback fail,
			final String... kvs) {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < kvs.length; i = +2) {
					sb.append(kvs[i]).append("=").append(kvs[i + 1])
							.append("&");
				}

				URLConnection uc;

				try {
					switch (method) {
					case POST:
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Configure.CHARSET));
						bw.write(sb.toString());
						break;
					default:
						uc=new URL(url+"?"+sb.toString()).openConnection();
						break;
					}
					BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream(),Configure.CHARSET));
					String line;
					StringBuffer result = new StringBuffer();
					while((line=br.readLine())!=null){
						result.append(line);
					}
					return result.toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}
			@Override
			protected void onPostExecute(String result) {
				
				super.onPostExecute(result);
				if(result!=null){
					if(success!=null){
						success.onSuccess(result);
					}
					
				}else{
					if(fail!=null){
						fail.onFail();
					}
				}
			}

		}.execute();
	}

	
	public static interface SuccessCallback {
		public void onSuccess(String str);
	}

	public static interface FailCallback {
		public void onFail();
	}
}
