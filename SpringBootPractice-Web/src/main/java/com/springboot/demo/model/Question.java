package com.springboot.demo.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
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
