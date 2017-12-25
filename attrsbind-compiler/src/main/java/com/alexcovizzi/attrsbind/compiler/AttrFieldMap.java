package com.alexcovizzi.attrsbind.compiler;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Alex on 23/11/2017.
 */

public class AttrFieldMap extends LinkedHashMap<String, Set<AttrField>> {
    
    public AttrFieldMap() {
    
    }
    
    public void put(TypeMirror enclosingClassType, AttrField attrField) {
        put(enclosingClassType.toString(), attrField);
    }
    
    public void put(String enclosingClass, AttrField attrField) {
        Set<AttrField> set = get(enclosingClass);
        if(set == null) {
            set = new LinkedHashSet<>();
            put(enclosingClass, set);
        }
        set.add(attrField);
    }
    
    public Set<AttrField> get(TypeMirror classType) {
        return get(classType.toString());
    }
}
