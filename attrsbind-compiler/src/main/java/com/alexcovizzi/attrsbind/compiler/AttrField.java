package com.alexcovizzi.attrsbind.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import static com.alexcovizzi.attrsbind.compiler.AnnotationValidation.validateAttrName;

/**
 * Created by Alex on 03/12/2017.
 */

public class AttrField {
    private String fieldName;
    private String fieldType;
    private String attrName;
    private String defValue;
    
    public AttrField(String fieldName, String fieldType, String attrName, String defValue) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.attrName = attrName;
        this.defValue = defValue;
        
        if(!validateAttrName(attrName)) this.attrName = fieldName;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getFieldType() {
        return fieldType;
    }
    
    public String getAttrName() {
        return attrName;
    }
    
    public String getDefValue() {
        return defValue;
    }
}
