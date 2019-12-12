package com.springboot.demo.model;

import lombok.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(Restaurant Service)와 통신을 위한 평점 Model 클래스
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    private int grade_id;

    @Min(value = 1, message = "평점은 1~5점이어야 합니다.")
    @Max(value = 5, message = "평점은 1~5점이어야 합니다.")
    private int star;

    private int res_index;
    private String user_id;
}
