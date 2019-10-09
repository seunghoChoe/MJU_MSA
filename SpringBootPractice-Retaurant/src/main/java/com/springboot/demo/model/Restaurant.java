package com.springboot.demo.model;

import java.util.List;

public class Restaurant {

	private int res_index;
	private String res_name;
	private String res_category;
	private int res_grade;
	private List<Menu> res_menues;
	private int res_expected_minutes;
	
	public int getRes_index() {
		return res_index;
	}
	public void setRes_index(int res_index) {
		this.res_index = res_index;
	}
	public String getRes_name() {
		return res_name;
	}
	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}
	public String getRes_category() {
		return res_category;
	}
	public void setRes_category(String res_category) {
		this.res_category = res_category;
	}
	public int getRes_grade() {
		return res_grade;
	}
	public void setRes_grade(int res_grade) {
		this.res_grade = res_grade;
	}
	public List<Menu> getRes_menues() {
		return res_menues;
	}
	public void setRes_menues(List<Menu> res_menues) {
		this.res_menues = res_menues;
	}
	public int getRes_expected_minutes() {
		return res_expected_minutes;
	}
	public void setRes_expected_minutes(int res_expected_minutes) {
		this.res_expected_minutes = res_expected_minutes;
	}
	
	
	
}
