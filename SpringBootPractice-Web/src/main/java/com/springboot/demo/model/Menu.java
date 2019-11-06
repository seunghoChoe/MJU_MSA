package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	private int menu_index;

	@Size(min = 1, max = 50, message = "메뉴 이름은 1~50자 이내로 작성해야 합니다.") // 특수문자 등 필터링 필요 시, @Pattern 사용
	private String menu_name;
	private int menu_price;
}