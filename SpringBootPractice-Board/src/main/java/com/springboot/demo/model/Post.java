package com.springboot.demo.model;

import java.time.LocalDateTime;

public class Post {
	private int post_id;
	private String post_title;
	private String post_user_id;
	private String post_content;
	private String post_image;
	private LocalDateTime post_created_date;
	private LocalDateTime post_updated_date;
	
	
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_user_id() {
		return post_user_id;
	}
	public void setPost_user_id(String post_user_id) {
		this.post_user_id = post_user_id;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public String getPost_image() {
		return post_image;
	}
	public void setPost_image(String post_image) {
		this.post_image = post_image;
	}
	public LocalDateTime getPost_created_date() {
		return post_created_date;
	}
	public void setPost_created_date(LocalDateTime post_created_date) {
		this.post_created_date = post_created_date;
	}
	public LocalDateTime getPost_updated_date() {
		return post_updated_date;
	}
	public void setPost_updated_date(LocalDateTime post_updated_date) {
		this.post_updated_date = post_updated_date;
	}
	
	
	
	
}
