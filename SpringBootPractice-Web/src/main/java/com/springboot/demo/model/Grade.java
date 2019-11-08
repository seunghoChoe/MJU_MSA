package com.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter
@Setter
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
