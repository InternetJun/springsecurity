package com.example.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityLog {
    String username() default "";

    String startTime() default "";

    String endTime() default "";
}
