package com.example.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface InnerServer {
    String username() default "";

    String startTime() default "";

    String endTime() default "";
}
