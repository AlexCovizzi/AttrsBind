package com.alexcovizzi.attrsbind.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.alexcovizzi.attrsbind.compiler.Utils.getClassPackage;
import static com.alexcovizzi.attrsbind.compiler.Utils.getClassSimpleName;

/**
 * Created by Alex on 03/12/2017.
 */

public class AttrsBinderSource {
    private static final String CLASS_SUFFIX = "_AttrsBinder";
    
    private static final ClassName CLASS_ATTR_SET = ClassName.get("android.util","AttributeSet");
    private static final ClassName CLASS_TYPED_ARRAY = ClassName.get("android.content.res","TypedArray");
    private static final ClassName CLASS_ATTR_VALUE_READER = ClassName.get("com.alexcovizzi.attrsbind","AttrValueReader");
    
    private static final String VAR_TYPED_ARRAY = "typedArray";
    private static final String VAR_ATTR_SET = "attrs";
    private static final String VAR_VIEW = "view";
    private static final String VAR_DEF_STYLE_ATTR = "defStyleAttr";
    private static final String VAR_DEF_STYLE_RES = "defStyleRes";
    private static final String VAR_ATTR_VALUE_READER = "reader";
    
    private static final String METHOD_BIND = "bindAttrs";
    
    private String classPackage;
    private TypeSpec typeSpec;
    
    private ClassName classR;
    private ClassName classView;
    
    public AttrsBinderSource(String projPackage, String classFullName, Set<AttrField> attrFieldSet) {
        this.classPackage = getClassPackage(classFullName);
        String className = getClassSimpleName(classFullName);
        classView = ClassName.get(classPackage, className);
        classR = ClassName.get(projPackage, "R");
    
        TypeSpec.Builder classBuilder = classBuilder(className+CLASS_SUFFIX);
        MethodSpec ctorAttributeSet = constructorAttributeSet(className).build();
        MethodSpec ctorTypedArray = constructorTypedArray().build();
        MethodSpec methodBind = methodBindAttrs(className, attrFieldSet).build();
        classBuilder.addMethods(Arrays.asList(ctorAttributeSet, ctorTypedArray, methodBind));
        
        typeSpec = classBuilder.build();
    }
    
    private TypeSpec.Builder classBuilder(String name) {
        return TypeSpec
            .classBuilder(name)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }
    
    
    private MethodSpec.Builder constructorAttributeSet(String className) {
        return MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            
            .addParameter(classView, VAR_VIEW)
            .addParameter(CLASS_ATTR_SET, VAR_ATTR_SET)
            .addParameter(Integer.TYPE, VAR_DEF_STYLE_ATTR)
            .addParameter(Integer.TYPE, VAR_DEF_STYLE_RES)
            
            .addStatement("$T $L = $L.getContext().getTheme().obtainStyledAttributes($L, $T.styleable.$L, $L, $L)",
                CLASS_TYPED_ARRAY, VAR_TYPED_ARRAY, VAR_VIEW, VAR_ATTR_SET,
                classR, className, VAR_DEF_STYLE_ATTR, VAR_DEF_STYLE_RES)
            
            .addStatement("$T $L = new $T($L)", CLASS_ATTR_VALUE_READER, VAR_ATTR_VALUE_READER,
            CLASS_ATTR_VALUE_READER, VAR_TYPED_ARRAY)
            
            .addStatement("$L($L, $L)", METHOD_BIND, VAR_VIEW, VAR_ATTR_VALUE_READER)
            
            .addStatement("$L.recycle()", VAR_TYPED_ARRAY);
    }
    
    private MethodSpec.Builder constructorTypedArray() {
        return MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            
            .addParameter(classView, VAR_VIEW)
            .addParameter(CLASS_TYPED_ARRAY, VAR_TYPED_ARRAY)
        
            .addStatement("$T $L = new $T($L)", CLASS_ATTR_VALUE_READER, VAR_ATTR_VALUE_READER,
            CLASS_ATTR_VALUE_READER, VAR_TYPED_ARRAY)
            
            .addStatement("$L($L, $L)", METHOD_BIND, VAR_VIEW, VAR_ATTR_VALUE_READER);
    }
    
    private MethodSpec.Builder methodBindAttrs(String className, Set<AttrField> attrFieldSet) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(METHOD_BIND)
            .addModifiers(Modifier.PRIVATE)
            
            .addParameter(classView, VAR_VIEW)
            .addParameter(CLASS_ATTR_VALUE_READER, VAR_ATTR_VALUE_READER);
        
        for(AttrField attrField : attrFieldSet) {
            methodBuilder.addStatement("$L.$L = $L.readAndConvert($T.styleable.$L, $S, $L.class)",
                VAR_VIEW, attrField.getFieldName(), VAR_ATTR_VALUE_READER, classR,
                className+"_"+attrField.getAttrName(), attrField.getDefValue(), attrField.getFieldType());
        }
        
        return methodBuilder;
    }
    
    public void generateClass(Filer filer) {
        try {
            JavaFile.builder(classPackage, typeSpec)
                .build()
                .writeTo(filer);
        } catch (IOException e) {
            //
        }
    }
}
