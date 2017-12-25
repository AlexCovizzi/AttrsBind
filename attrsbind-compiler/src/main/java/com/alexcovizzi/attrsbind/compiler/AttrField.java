package com.alexcovizzi.attrsbind.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

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
    
    public CodeBlock getBindingCode(ClassName rClass) {
        CodeBlock.Builder builder = CodeBlock.builder();
        
        return builder.build();
    }
}
