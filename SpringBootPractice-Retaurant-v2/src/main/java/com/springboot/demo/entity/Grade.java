/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hwamoc Kim
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Grade (평점) entity 클래스 - Grade 테이블과 매칭될 평점 객체이다   
 */

@Builder // builder를 사용할수 있게 합니다
@Entity // JPA entity임을 알립니다
@Setter	// setter를 자동으로 생성합니다
@Getter // getter를 자동으로 생성합니다
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다
public class Grade {
	@Id // primaryKey임을 알립니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// pk생성전략을 DB에 위임한다는 의미입니다. mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됩니다
	private int grade_id;
	@Column(nullable = true, length = 11) // star column을 명시합니다. 필드의 길이는 11입니다
	private int star;
	@Column(nullable = true, length = 11) 
	private int res_index;
	@Column(nullable = true, length = 45) 
	private String user_id;

}