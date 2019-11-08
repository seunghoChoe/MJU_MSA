package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant implements Serializable {
	private static final long serialVersionUID = 1L;

	// 객체 검증 http://wonwoo.ml/index.php/post/520

	private int res_index;

	@Size(min = 1, max = 50, message = "식당 이름은 1~50자 이내로 작성해야 합니다.") // 특수문자 등 필터링 필요 시, @Pattern 사용
	private String res_name;

	@NotBlank(message = "식당 카테고리를 지정해야 합니다.")
	private String res_category;

	private int res_grade;

	@Min(value = 1, message = "식당 예상 시간은 최소 1분으로 지정해야 합니다.")
	private int res_expected_minutes;

	@Size(min = 1, max = 500, message = "식당 소개는 1~500자 이내로 작성해야 합니다.") // 특수문자 등 필터링 필요 시, @Pattern 사용
	private String res_content;
	private String res_image; // 썸네일 이미지

	private List<Menu> res_menues;
}