package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;

	private int res_index;

	@Size(min = 1, max = 50, message = "식당 이름은 1~50자 이내로 작성해야 합니다.") // 특수문자 등 필터링 필요 시, @Pattern 사용
	private String res_name;
	private String res_category;
	private int res_grade;
	private int res_expected_minutes;
}