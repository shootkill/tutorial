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
import com.example.androidsecret.net.GetCode;
import com.example.androidsecret.net.Login;
import com.example.androidsecret.net.Login.FailCallback;
import com.example.androidsecret.net.Login.SuccessCallback;
import com.example.androidsecret.util.Configure;
import com.example.androidsecret.util.MD5Util;

public class LoginActivity extends Activity implements OnClickListener {
	Button getCode, login;
	EditText phonetext, codetext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getCode = (Button) findViewById(R.id.lg_btn_code);
		login = (Button) findViewById(R.id.lg_btn_login);
		phonetext = (EditText) findViewById(R.id.lg_edt_phone);
		codetext = (EditText) findViewById(R.id.lg_edt_code);
		getCode.setOnClickListener(this);
		login.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lg_btn_code:
			if (TextUtils.isEmpty(phonetext.getText())) {
				Toast.makeText(LoginActivity.this, "can no be empty",
						Toast.LENGTH_SHORT).show();
				return;
			}
			final ProgressDialog pd = ProgressDialog.show(LoginActivity.this,
					"sending", "please wait");
			new GetCode(phonetext.getText().toString(),
					new GetCode.SuccessCallback() {

						@Override
						public void onSuccess() {
							pd.dismiss();
							Toast.makeText(LoginActivity.this,
									"please check your short message",
									Toast.LENGTH_SHORT).show();

						}

					}, new GetCode.FailCallback() {

						@Override
						public void onFail() {
							pd.dismiss();
							Toast.makeText(LoginActivity.this,
									"get code failed", Toast.LENGTH_SHORT)
									.show();

						}
					});
			break;

		case R.id.lg_btn_login:
			String phone = phonetext.getText().toString();
			String code = codetext.getText().toString();
			if (phone == null || code == null || phone.trim().equals("")
					|| code.trim().equals("")) {
				Toast.makeText(LoginActivity.this,
						"phone or code can not be empty", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			final ProgressDialog pda=ProgressDialog.show(LoginActivity.this,"connecting","please wait a minute");
			new Login(MD5Util.MD5(phone), code, new SuccessCallback() {

				@Override
				public void onSuccess(String token) {
					pda.dismiss();
					Configure.cachedToken(LoginActivity.this, token);
					Configure.cachedPhoneNum(LoginActivity.this, phonetext
							.getText().toString());
					Intent in = new Intent(LoginActivity.this,
							TimelineActivity.class);
					in.putExtra(Configure.KEY_TOKEN, token);
					in.putExtra(Configure.KEY_PHONE_NM, phonetext.getText()
							.toString());
					startActivity(in);
					finish();
				}

			}, new FailCallback() {

				@Override
				public void onFail() {
					pda.dismiss();
					Toast.makeText(LoginActivity.this,
							"fail to login please try to login later",
							Toast.LENGTH_SHORT).show();

				}
			});
		default:
			break;
		}

	}
}
