package com.springboot.demo.model;

import lombok.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(User Service)와 통신을 위한 계정 Model 클래스
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	// ID: 영문 소문자, 숫자와 특수기호(_),(-)만 허용, 5~15자
	@Pattern(regexp="^[a-z0-9][a-z0-9_\\-]{4,16}$", message = "ID 형식이 맞지 않습니다.")
	private String user_id;

	// 비밀번호: 8~15자, 최소 하나의 문자, 최소 하나의 숫자, 최소 하나의 특수 문자
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", message = "비밀번호 형식이 맞지 않습니다.")
	private String user_password;

	// 이름: 한글만 허용(자음 또는 모음 비허용, 공백 비허용), 2~5자
	@Pattern(regexp="^[가-힣]{2,5}$", message = "이름 형식이 맞지 않습니다.")
	private String user_name;

	// 이메일: 이메일 형식 지정
	@Pattern(regexp="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 맞지 않습니다.")
	private String user_email;
}