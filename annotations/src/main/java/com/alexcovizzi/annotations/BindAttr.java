package com.alexcovizzi.annotations;

/**
 * Created by Alex on 29/11/2017.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface BindAttr {
    /**
     * The name of the attribute.
     * If no name is specified, it's used the name of the field annotated.
     */
    String name() default "";
    
    /**
     * The default value of the attribute.
     */
    String def() default "";
    
    
    //String[] args() default {"none"};
}
