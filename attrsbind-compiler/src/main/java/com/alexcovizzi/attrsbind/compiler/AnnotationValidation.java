package com.alexcovizzi.attrsbind.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

/**
 * Created by Alex on 23/11/2017.
 */

public class AnnotationValidation {
    
    public static boolean validateFieldElement(Element el) {
        if(el.getKind() != ElementKind.FIELD) {
            Log.w(AnnotationValidation.class,
                "Element "+el.toString()+" annotated is not a field.");
            return false;
        }
        if(el.getModifiers().contains(Modifier.PRIVATE) ||
            el.getModifiers().contains(Modifier.PROTECTED)) {
            Log.w(AnnotationValidation.class,
                "Element "+el.toString()+" annotated must be public or package-private.");
            return false;
        }
        if(el.getModifiers().contains(Modifier.FINAL)) {
            Log.w(AnnotationValidation.class,
                "Element "+el.toString()+" annotated can't be final.");
            return false;
        }
        return true;
    }
    
    public static boolean validateAttrName(String attrName) {
        if(attrName.isEmpty()) return false;
        return true;
    }
    
    public static boolean validateDefaultValue(String defValue) {
        // TODO check if the default value is compatible with the attribute format
        return true;
    }
}
