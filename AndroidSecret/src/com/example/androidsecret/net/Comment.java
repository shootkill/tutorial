package com.example.androidsecret.net;

public class Comment {

	private String phone_md5, comment;

	public String getPhone_md5() {
		return phone_md5;
	}

	public String getComment() {
		return comment;
	}

	public Comment(String phone_md5, String comment) {
		this.phone_md5 = phone_md5;
		this.comment = comment;
	}
}
