package com.immymemine.kevin.skillshare.utility.communication_util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by quf93 on 2017-12-15.
 */

@Documented // doc 에 '어노테이션' 정보 저장
@Retention(RetentionPolicy.RUNTIME) // RUNTIME : '어노테이션' 이 class 파일에 저장되고 읽혀진다.

// @Retention(RetentionPolicy.CLASS) // 컴파일러가 클래스를 참조할 때까지 유효하다.
// @Retention(RetentionPolicy.SOURCE) // 어노테이션 정보는 컴파일 이후 없어진다.

@Target({ElementType.METHOD}) // 해당 '어노테이션' 을 붙일 element 타입 설정
public @interface Subscribe {
//    boolean isSubscribe() default true;
}
