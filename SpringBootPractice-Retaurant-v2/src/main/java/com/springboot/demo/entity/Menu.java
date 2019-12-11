/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
 * Menu(메뉴) Entity 클래스 - Menu 테이블과 매칭될 메뉴 객체이다
 */

@Builder // builder를 사용할수 있게 합니다.
@Entity // JPA entity임을 알립니다.
@Setter	//menu 필드값의 setter를 자동으로 생성합니다.
@Getter // menu 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
public class Menu {
	@Id // primaryKey임을 알립니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// pk생성전략을 DB에 위임한다는 의미입니다. mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됩니다
	private int menu_index;
	@Column(nullable = true, length = 45) // menu_name column을 명시합니다. 필드의 길이는 45입니다
	private String menu_name;
	@Column(nullable = true, length = 45) 
	private int menu_price;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_index")	// restaurant 테이블과 조인합니다. FK는 res_index  
    private Restaurant restaurant; // 메뉴 - 식당의 관계 - N:1

    protected Restaurant getRestaurant() { 
        return restaurant;	// Join 테이블이 Json결과에 표시되지 않도록 처리합니다
    }
 
    public Menu(Restaurant restaurant, int menu_index, String menu_name, int menu_price) {
    	this.restaurant = restaurant;
    	this.menu_index = menu_index;
        this.menu_name = menu_name;
        this.menu_price = menu_price;
    }
 
    public Menu setUpdate(int menu_index, String menu_name, int menu_price) {
    	this.menu_index = menu_index;	// 수정시 데이터를 처리합니다 
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        return this;
    }
}