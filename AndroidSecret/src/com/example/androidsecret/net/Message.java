package com.example.androidsecret.net;

public class Message {

	private String msgId;
	private String phone_md5;
	private String msg;
	
	public String getMsgId() {
		return msgId;
	}

	public String getPhone_md5() {
		return phone_md5;
	}

	public String getMsg() {
		return msg;
	}

	public Message(String msgId,String phone_md5,String msg){
		this.msgId=msgId;
		this.msg=msg;
		this.phone_md5=phone_md5;
	}
}
