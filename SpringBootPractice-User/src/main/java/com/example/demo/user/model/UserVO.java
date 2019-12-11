/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo.user.model;
 
/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * User ValueObject(VO) 클래스
 *
 */

public class UserVO {
 
	String user_id;
	String user_password;
	String user_name;
	String user_email;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	
 
}