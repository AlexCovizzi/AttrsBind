package com.alexcovizzi.attrsbind.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alex on 29/11/2017.
 */


@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BindStyleable {
    String name() default "";
    
    String projectPackage() default "";
}
