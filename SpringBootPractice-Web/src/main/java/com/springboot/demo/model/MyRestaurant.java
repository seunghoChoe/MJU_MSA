package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyRestaurant implements Serializable {
    private static final long serialVersionUID = 1L;
    private int index;
    private String user_id;
    private String res_name;
    private String res_category;
    private int res_grade;
    private int res_expected_minutes;
}