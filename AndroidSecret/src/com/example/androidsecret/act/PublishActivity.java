package com.example.androidsecret.act;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidsecret.R;
import com.example.androidsecret.net.Publish;
import com.example.androidsecret.util.Configure;

public class PublishActivity extends Activity implements OnClickListener{

	private Button publish;
	private EditText content;
	private String phone_md5,token;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pubmsg);
		publish=(Button) findViewById(R.id.publish_btn_send);
		content=(EditText) findViewById(R.id.publish_et_content);
		phone_md5=getIntent().getStringExtra(Configure.KEY_PHONE_MD5);
		token=getIntent().getStringExtra(Configure.KEY_TOKEN);
		publish.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.publish_btn_send:
			if (TextUtils.isEmpty(content.getText().toString())) {
				Toast.makeText(PublishActivity.this, "can not be empty", Toast.LENGTH_LONG).show();
				return;
			}
			final ProgressDialog pd=ProgressDialog.show(PublishActivity.this, "sending", "wait a mimute");
			new Publish(phone_md5, content.getText().toString(), token, new Publish.SuccessCallback() {
				
				@Override
				public void onSuccess() {
					pd.dismiss();
					setResult(Configure.ACTIVITY_RESULT_NEEDFRESH);
					Toast.makeText(PublishActivity.this, "send success", Toast.LENGTH_LONG).show();
					
					finish();
				}
			}, new Publish.FailCallback() {
				
				@Override
				public void onFail(int errorCode) {
					pd.dismiss();
					if (errorCode==Configure.RESULT_STATUS_INVALID_TOKEN) {
						startActivity(new Intent(PublishActivity.this,LoginActivity.class));
						finish();
					}else{
						Toast.makeText(PublishActivity.this, "failed", Toast.LENGTH_LONG).show();
					}
				}
			});
			break;

		default:
			break;
		}
	}
	
}
