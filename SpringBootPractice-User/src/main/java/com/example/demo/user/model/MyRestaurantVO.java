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
 * MyRestaurant ValueObject(VO) 클래스
 *
 */

public class MyRestaurantVO {
	String user_id;
	int res_index;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getRes_index() {
		return res_index;
	}
	public void setRes_index(int res_index) {
		this.res_index = res_index;
	}
	
}
