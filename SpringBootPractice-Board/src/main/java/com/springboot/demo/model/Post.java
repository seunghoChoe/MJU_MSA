package com.springboot.demo.model;

import java.time.LocalDateTime;

public class Post {
	private int post_index;
	private String post_title;
	private String post_content;
	private LocalDateTime post_created_date;
	public int getPost_index() {
		return post_index;
	}
	public void setPost_index(int post_index) {
		this.post_index = post_index;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public LocalDateTime getPost_created_date() {
		return post_created_date;
	}
	public void setPost_created_date(LocalDateTime post_created_date) {
		this.post_created_date = post_created_date;
	}
	
	
}
