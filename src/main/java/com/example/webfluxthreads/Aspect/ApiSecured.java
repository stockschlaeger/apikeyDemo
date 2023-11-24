package com.example.webfluxthreads.Aspect;

import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiSecured {
    // in der aktuellen Version nur als default gebraucht
    String apiKey() default "mykey";
}
