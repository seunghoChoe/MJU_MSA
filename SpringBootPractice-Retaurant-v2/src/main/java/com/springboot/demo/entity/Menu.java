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

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
public class Menu {
	@Id // primaryKey임을 알립니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// pk생성전략을 DB에 위임한다는 의미입니다. mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됩니다.
	private int menu_index;
	@Column(nullable = true, length = 45) // uid column을 명시합니다. 필수이고 유니크한 필드이며 길이는 30입니다.
	private String menu_name;
	@Column(nullable = true, length = 45) // uid column을 명시합니다. 필수이고 유니크한 필드이며 길이는 30입니다.
	private int menu_price;
	
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "res_index")
//    private Restaurant restaurant; // 메뉴 - 식당의 관계 - N:1
    
// // Join 테이블이 Json결과에 표시되지 않도록 처리.
//    protected Restaurant getRestaurant() {
//        return restaurant;
//    }
// 
//    // 생성자
//    public Menu(Restaurant restaurant, int menu_index, String menu_name, int menu_price) {
//    	this.restaurant = restaurant;
//    	this.menu_index = menu_index;
//        this.menu_name = menu_name;
//        this.menu_price = menu_price;
//    }
// 
//    // 수정시 데이터 처리
//    public Menu setUpdate(int menu_index, String menu_name, int menu_price) {
//    	this.menu_index = menu_index;
//        this.menu_name = menu_name;
//        this.menu_price = menu_price;
//        return this;
//    }
}