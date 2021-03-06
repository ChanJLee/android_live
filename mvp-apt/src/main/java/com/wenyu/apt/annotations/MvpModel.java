package com.wenyu.apt.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiacheng.li on 17/2/28.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Target(ElementType.FIELD)
public @interface MvpModel {
    String tag() default "";
}
