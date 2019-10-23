package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	private int menu_index;
	private String menu_name;
	private int menu_price;
}