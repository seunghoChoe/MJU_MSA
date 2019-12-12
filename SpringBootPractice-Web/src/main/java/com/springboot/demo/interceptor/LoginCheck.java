package com.springboot.demo.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 프로젝트 내에서 사용자 인증(로그인) 검사를 위해 공통적으로 사용되는 Annotation 인터페이스
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface LoginCheck {

    // 검사 타입 T/F
    enum Type {
        TRUE, FALSE;
    }

    // 기본 검사 타입(T)
    Type type() default Type.TRUE;
}