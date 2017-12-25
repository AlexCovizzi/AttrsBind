package com.alexcovizzi.attrsbind;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;


/**
 * Created by Alex on 21/11/2017.
 */

public class AttrsBinder {
    private static final String CLASSNAME_SUFFIX = "_AttrsBinder";
    
    public static void bind(@NonNull View view, @NonNull AttributeSet attrs) {
        bind(view, attrs, 0);
    }
    
    public static void bind(@NonNull View view, @NonNull AttributeSet attrs, int defStyleAttr) {
        bind(view, attrs, defStyleAttr, 0);
    }
    
    public static void bind(@NonNull View view, @NonNull AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if(view == null) throw new NullPointerException("Parameter view can't be null.");
        if(attrs == null) throw new NullPointerException("Parameter attrs can't be null.");
        
        try {
            Class<?> cls = view.getClass();
            
            Constructor<?> constructor;
            constructor = cls.getClassLoader().loadClass(cls.getCanonicalName() + CLASSNAME_SUFFIX)
                .getConstructor(cls, AttributeSet.class, int.class, int.class);
            
            constructor.newInstance(view, attrs, defStyleAttr, defStyleRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void bind(@NonNull View view, @NonNull TypedArray typedArray) {
        if(view == null) throw new NullPointerException("Parameter view can't be null.");
        if(typedArray == null) throw new NullPointerException("Parameter typedArray can't be null.");
        
        try {
            Class<?> cls = view.getClass();
            
            Constructor<?> constructor;
            constructor = cls.getClassLoader().loadClass(cls.getCanonicalName() + CLASSNAME_SUFFIX)
                .getConstructor(cls, TypedArray.class);
            
            constructor.newInstance(view, typedArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
