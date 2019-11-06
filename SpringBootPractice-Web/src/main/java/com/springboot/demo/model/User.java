package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Pattern(regexp="^[a-z0-9][a-z0-9_\\-]{4,16}$", message = "ID 형식이 맞지 않습니다.") // ID: 영문 소문자, 숫자와 특수기호(_),(-)만 허용, 5~15자
	private String user_id;

	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", message = "비밀번호 형식이 맞지 않습니다.") // 비밀번호: 8~15자, 최소 하나의 문자, 최소 하나의 숫자, 최소 하나의 특수 문자
	private String user_password;

	@Pattern(regexp="^[가-힣]{2,5}$", message = "이름 형식이 맞지 않습니다.") // 이름: 한글만 허용(자음 또는 모음 비허용, 공백 비허용), 2~5자
	private String user_name;

	@Pattern(regexp="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 맞지 않습니다.")
	private String user_email;
}