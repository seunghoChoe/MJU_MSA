package com.springboot.demo.model;

import lombok.*;
import java.io.Serializable;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Seungho Choe, Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(User Service)와 통신을 위한 내 식당(즐겨찾기) Model 클래스
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyRestaurant implements Serializable {
    private static final long serialVersionUID = 1L;
    private int res_index;
    private String user_id;
}