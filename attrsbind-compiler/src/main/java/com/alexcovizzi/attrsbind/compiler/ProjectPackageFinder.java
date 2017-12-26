package com.alexcovizzi.attrsbind.compiler;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;

/**
 * Created by Alex on 03/12/2017.
 */

public class ProjectPackageFinder {
    
    /* search for the package that has both the files R and BuildConfig */
    public static String search(Elements elUtils, RoundEnvironment env) {
        Set<String> rSet = new LinkedHashSet<>();
        for(Element element : env.getRootElements()) {
            String pck = elUtils.getPackageOf(element).getQualifiedName().toString();
            if(element.getSimpleName().toString().equals("R")) {
                rSet.add(pck);
            } else if(element.getSimpleName().toString().equals("BuildConfig")) {
                if(rSet.contains(pck)) {
                    return pck;
                }
            }
        }
        return null;
    }
    
    
    /* search in the current package if there is the class R, if not found repeat in  the parent package */
    public static String search(Elements elUtils, PackageElement packageElement) {
        if(packageElement == null) return null;
        String packageName = packageElement.getQualifiedName().toString();
        for(Element el : packageElement.getEnclosedElements()) {
            String clsName = el.getSimpleName().toString();
            if(clsName.equals("R")) {
                return packageElement.getQualifiedName().toString();
            }
        }
        String parentPackage = getParentPackage(packageName);
        return search(elUtils, elUtils.getPackageElement(parentPackage));
    }
    
    private static String getParentPackage(String packageName) {
        int lastIdx  = packageName.lastIndexOf(".");
        if(lastIdx > 0) return  packageName.substring(0, lastIdx);
        else return "";
    }
}
