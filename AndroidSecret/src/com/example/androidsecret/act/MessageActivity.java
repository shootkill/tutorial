package com.example.androidsecret.act;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidsecret.R;
import com.example.androidsecret.net.Comment;
import com.example.androidsecret.net.GetComment;
import com.example.androidsecret.net.PubComment;
import com.example.androidsecret.util.Configure;
import com.example.androidsecret.util.MD5Util;

public class MessageActivity extends ListActivity implements OnClickListener {

	private String msg, msgId, phone_md5, token;
	private TextView tvMsg;
	private Button send;
	private EditText etSend;
	MessageListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg);
		tvMsg = (TextView) findViewById(R.id.msg_tv_msg);
		send = (Button) findViewById(R.id.msg_btn_send);
		etSend = (EditText) findViewById(R.id.msg_edt_content);
		send.setOnClickListener(this);
		adapter = new MessageListAdapter(this);
		setListAdapter(adapter);
		Intent data = getIntent();
		msg = data.getStringExtra(Configure.KEY_MSG);
		msgId = data.getStringExtra(Configure.KEY_MSG_ID);
		phone_md5 = data.getStringExtra(Configure.KEY_PHONE_MD5);
		token = data.getStringExtra(Configure.KEY_TOKEN);
		tvMsg.setText(msg);
		getMessage();
	}

	@Override
	public void onClick(View v) {
		if (TextUtils.isEmpty(etSend.getText())) {
			Toast.makeText(MessageActivity.this, "please write someting",
					Toast.LENGTH_SHORT).show();
		} else {
			final ProgressDialog pd = ProgressDialog.show(this, "connection",
					"please wait a minute");
			new PubComment(MD5Util.MD5(Configure.getCachedPhoneNum(this)),
					token, etSend.getText().toString(), msgId,
					new PubComment.SuccessCallback() {

						@Override
						public void onSucccess() {
							pd.dismiss();
							etSend.setText("");
							getMessage();
						}
					}, new PubComment.FailCallback() {

						@Override
						public void onFail(int errorCode) {
							pd.dismiss();
							if (errorCode == Configure.RESULT_STATUS_INVALID_TOKEN) {
								startActivity(new Intent(MessageActivity.this,
										LoginActivity.class));
								finish();

							} else {
								Toast.makeText(MessageActivity.this,
										"send message failed",
										Toast.LENGTH_SHORT).show();

							}
						}
					});
		}
	}

	private void getMessage() {
		final ProgressDialog pd = ProgressDialog.show(this, "connection",
				"please wait a minute");
		new GetComment(phone_md5, token, msgId, 1, 20,
				new GetComment.SuccessCallback() {

					@Override
					public void onSuccess(String msgId, int page, int perpage,
							List<Comment> comments) {
						pd.dismiss();
						adapter.clean();
						adapter.addAll(comments);
					}
				}, new GetComment.FailCallback() {

					@Override
					public void onFail(int errorCode) {
						pd.dismiss();
						if (errorCode == Configure.RESULT_STATUS_INVALID_TOKEN) {
							startActivity(new Intent(MessageActivity.this,
									LoginActivity.class));
							finish();
						} else {
							Toast.makeText(MessageActivity.this,
									"get message failed", Toast.LENGTH_SHORT)
									.show();
						}

					}
				});
	}

}
