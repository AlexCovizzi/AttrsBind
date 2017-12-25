package com.alexcovizzi.attrsbind.compiler;

/**
 * Created by Alex on 03/12/2017.
 */

public class Utils {
    
    public static String getClassPackage(String classFullName) {
        return classFullName.substring(0, classFullName.lastIndexOf("."));
    }
    
    public static String getClassSimpleName(String classFullName) {
        return classFullName.substring(classFullName.lastIndexOf('.') + 1);
    }
}
