package com.springboot.demo.model;

import lombok.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(Board Service)와 통신을 위한 게시글 Model 클래스
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	private int post_id;
	private String post_user_id;

	// 특수문자 등 필터링 필요 시, @Pattern 사용
	@Size(min = 1, max = 100, message = "게시글 제목은 1~100자 이내로 작성해야 합니다.")
	private String post_title;

	// 태그가 사용될 경우 상세한 필터링 필요
	@Size(min = 1, max = 1000, message = "게시글 내용은 1~1000자 이내로 작성해야 합니다.")
	private String post_content;

	private String post_image; // 썸네일 이미지

	private LocalDateTime post_created_date;
	private LocalDateTime post_updated_date;

	public Post(String post_user_id, String post_title, String post_content, String post_image) {
		this.post_user_id = post_user_id;
		this.post_title = post_title;
		this.post_content = post_content;
		this.post_image = post_image;
	}
}