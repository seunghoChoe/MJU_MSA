package com.springboot.demo.model;

import lombok.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(Restaurant Service)와 통신을 위한 식당 메뉴 Model 클래스
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	private int menu_index;

	// 특수문자 등 필터링 필요 시, @Pattern 사용
	@Size(min = 1, max = 50, message = "메뉴 이름은 1~50자 이내로 작성해야 합니다.")
	private String menu_name;
	private int menu_price;
}