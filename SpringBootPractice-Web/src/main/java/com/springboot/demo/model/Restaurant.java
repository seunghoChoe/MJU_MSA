package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;

	private int res_index;
	private String res_name;
	private String res_category;
	private int res_grade;
	private List<Menu> res_menus; // res_menues -> res_menus
	private int res_expected_minutes;
}