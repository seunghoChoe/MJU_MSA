package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	private String post_id;
	private String post_title;
	private String post_user_id;
	private String post_content;
	private LocalDateTime post_created_date;
	private LocalDateTime post_updated_date;
}