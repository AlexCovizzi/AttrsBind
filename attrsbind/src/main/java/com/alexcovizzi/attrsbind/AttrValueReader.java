package com.alexcovizzi.attrsbind;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.StyleableRes;
import android.util.TypedValue;

import com.alexcovizzi.typeconverter.TypeConverter;

/**
 * Created by Alex on 04/12/2017.
 */

public class AttrValueReader {
    private static final int DEFAULT_NUMBER = 0;
    private static final int DEFAULT_COLOR = 0xFFFFFF;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;
    
    private TypedArray typedArray;
    
    public AttrValueReader(@NonNull TypedArray typedArray) {
        if(typedArray == null) throw new NullPointerException("Argument typedArray can't be null.");
        this.typedArray = typedArray;
        
        setupTypeConverter();
    }
    
    private void setupTypeConverter() {
        // add custom types (added support for colors)
        TypeConverter.add(TypeConverter.TYPE_INTEGER, new IntegerConverter());
        TypeConverter.add(TypeConverter.TYPE_LONG, new LongConverter());
    }
    
    public <T>T readAndConvert(@StyleableRes int attrId, Object defValue, Class<T> resultCls) {
        
        TypedValue typedValue = typedArray.peekValue(attrId);
        if(typedValue == null) {
            // No attribute with the specified id was found, the default value is returned.
            return TypeConverter.convert(defValue).to(resultCls);
        } else {
            Object value = readValue(typedValue.type, typedArray, attrId, defValue);
            
            T result = TypeConverter.convert(value).to(resultCls);
    
            return result;
        }
    }
    
    private Object readValue(int type, TypedArray typedArray, int attrId, Object defValue) {
        Object value;
        switch(type) {
            case TypedValue.TYPE_REFERENCE:
            case TypedValue.TYPE_ATTRIBUTE:
                value = readResource(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_DIMENSION:
                value = readDimension(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_INT_COLOR_ARGB4:
            case TypedValue.TYPE_INT_COLOR_ARGB8:
            case TypedValue.TYPE_INT_COLOR_RGB4:
            case TypedValue.TYPE_INT_COLOR_RGB8:
                value = readColor(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_INT_DEC:
            case TypedValue.TYPE_INT_HEX:
                value = readInt(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_FLOAT:
                value = readFloat(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_FRACTION:
                value = readFraction(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_INT_BOOLEAN:
                value = readBoolean(typedArray, attrId, defValue);
                break;
            case TypedValue.TYPE_STRING:
                value = readString(typedArray, attrId, defValue);
                break;
            default:
                value = defValue;
                break;
        }
        return value;
    }
    
    private int readResource(TypedArray typedArray, int id, Object defValue) {
        int def = TypeConverter.convert(defValue).def(DEFAULT_NUMBER).toInteger();
        int value = typedArray.getResourceId(id, def);
        return value;
    }
    
    private int readDimension(TypedArray typedArray, int id, Object defValue) {
        int def = TypeConverter.convert(defValue).def(DEFAULT_NUMBER).toInteger();
        int value = typedArray.getDimensionPixelSize(id, def);
        return value;
    }
    
    private int readColor(TypedArray typedArray, int id, Object defValue) {
        int def = TypeConverter.convert(defValue).def(DEFAULT_COLOR).toInteger();
        int value = typedArray.getColor(id, def);
        return value;
    }
    
    private int readInt(TypedArray typedArray, int id, Object defValue) {
        int def = TypeConverter.convert(defValue).def(DEFAULT_NUMBER).toInteger();
        int value = typedArray.getInt(id, def);
        return value;
    }
    
    private float readFloat(TypedArray typedArray, int id, Object defValue) {
        float def = TypeConverter.convert(defValue).def(DEFAULT_NUMBER).toFloat();
        float value = typedArray.getFloat(id, def);
        return value;
    }
    
    private float readFraction(TypedArray typedArray, int id, Object defValue) {
        float def = TypeConverter.convert(defValue).def(DEFAULT_NUMBER).toFloat();
        float value = typedArray.getFraction(id, 1, 1, def);
        return value;
    }
    
    private boolean readBoolean(TypedArray typedArray, int id, Object defValue) {
        boolean def = TypeConverter.convert(defValue).def(DEFAULT_BOOLEAN).toBoolean();
        boolean value = typedArray.getBoolean(id, def);
        return value;
    }
    
    private String readString(TypedArray typedArray, int id, Object defValue) {
        String def = TypeConverter.convert(defValue).def(DEFAULT_STRING).toString();
        String value = typedArray.getString(id);
        if(value == null) value = def;
        return value;
    }
}
