package com.springboot.demo.model;

import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	private int post_id;
	private String post_user_id;

	@Size(min = 1, max = 100, message = "게시글 제목은 1~100자 이내로 작성해야 합니다.") // 특수문자 등 필터링 필요 시, @Pattern 사용
	private String post_title;

	@Size(min = 1, max = 1000, message = "게시글 내용은 1~1000자 이내로 작성해야 합니다.") // 태그가 사용될 경우 상세한 필터링 필요
	private String post_content;

	private String post_image; // 썸네일 이미지

	private LocalDateTime post_created_date;
	private LocalDateTime post_updated_date;
}