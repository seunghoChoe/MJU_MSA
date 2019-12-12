/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.springboot.demo.entity.Menu;

/**
 * @author Hwamoc Kim
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Restaurant(식당) Entity 클래스 - Restaurant 테이블과 매칭될 평점 객체이다
 */

@Builder	// builder를 사용할수 있게 합니다
@Entity	// JPA entity임을 알립니다
@Getter // restaurant 필드값의 getter를 자동으로 생성합니다
@Setter	// restaurant 필드값의 setter를 자동으로 생성합니다
@NoArgsConstructor	// 인자없는 생성자를 자동으로 생성합니다
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다
@Table(name = "Restaurant") // 'user' 테이블과 매핑됨을 명시
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Post Entity에서 User와의 관계를 Json으로 변환시 오류 방지를 위한 코드
@Proxy(lazy = false)
public class Restaurant {
    @Id // primaryKey임을 알립니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //  pk생성전략을 DB에 위임한다는 의미입니다. mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됩니다
    private int res_index;
    @Column(nullable = true, length = 45) // res_name column을 명시합니다. 필드의 길이는 45입니다
    private String res_name;
    @Column(nullable = true, length = 45) 
    private String res_category;
    @Column(nullable = true, length = 11) 
    private Integer res_grade;
    @OneToMany(targetEntity=Menu.class, mappedBy="restaurant", fetch=FetchType.EAGER)	// restaurant 테이블과 조인합니다
    @Column(nullable = true, length = 200)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Menu> res_menues;
    @Column(nullable = true, length = 11) 
    private Integer res_expected_minutes;
    @Column(nullable = true, length = 500)
    private String res_content; 
    @Column(nullable = true, length = 200)
    private String res_image; 
}