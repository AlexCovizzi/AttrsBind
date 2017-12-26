package com.alexcovizzi.attrsbind.compiler;

import com.alexcovizzi.attrsbind.annotations.BindAttr;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.alexcovizzi.attrsbind.compiler.AnnotationValidation.validateFieldElement;

public class Processor extends AbstractProcessor {
    
    private Filer filer;
    private Messager messager;
    private Elements elUtils;
    private String projPackage;
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        
        annotationTypes.add(BindAttr.class.getCanonicalName());
        
        return annotationTypes;
    }
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elUtils = processingEnvironment.getElementUtils();
        Log.getInstance().setMessager(messager);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        
        AttrFieldMap attrFieldMap = processBindAttr(env);
        for(Map.Entry<String, Set<AttrField>> entry : attrFieldMap.entrySet()) {
            String classFullName = entry.getKey();
            Set<AttrField> attrFieldSet = entry.getValue();
            AttrsBinderSource attrsBinderSource = new AttrsBinderSource(projPackage, classFullName, attrFieldSet);
            attrsBinderSource.generateClass(filer);
        }
        
        return true;
    }
    
    
    private AttrFieldMap processBindAttr(RoundEnvironment env) {
        AttrFieldMap attrFieldMap = new AttrFieldMap();
        
        for (Element element : env.getElementsAnnotatedWith(BindAttr.class)) {
            if(!validateFieldElement(element)) continue;
            
            Element enclosingElement = element.getEnclosingElement();
            
            // if not found continue to search for the R class
            if(projPackage == null) {
                projPackage = ProjectPackageFinder.search(elUtils, (PackageElement)enclosingElement.getEnclosingElement());
            }
            
            String classFullName = enclosingElement.asType().toString();
            
            BindAttr annotation = element.getAnnotation(BindAttr.class);
            String fieldName = element.toString();
            String fieldType = element.asType().toString();
            String attrName = annotation.name();
            String defValue = annotation.def();
            
            AttrField attrField = new AttrField(fieldName, fieldType, attrName, defValue);
            
            attrFieldMap.put(classFullName, attrField);
        }
        
        if(projPackage == null) {
            projPackage = ProjectPackageFinder.search(elUtils, env);
            if(projPackage == null) Log.e(getClass(), "Project package not found.");
        }
        
        return attrFieldMap;
    }
}
