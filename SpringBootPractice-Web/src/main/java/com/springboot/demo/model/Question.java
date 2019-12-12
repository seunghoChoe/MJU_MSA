package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Seungho Choe, Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(Board Service)와 통신을 위한 문의사항 Model 클래스
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	private int qna_id;
	private String qna_user_id;
	private String qna_manager;
	private String qna_category;
	private String qna_question;
	private String qna_answer;
}