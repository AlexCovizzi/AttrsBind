package com.alexcovizzi.attrsbind;

import android.graphics.Color;

import com.alexcovizzi.typeconverter.Converter;
import com.alexcovizzi.typeconverter.exceptions.InvalidConversionException;

/**
 * Created by Alex on 24/12/2017.
 */

public class IntegerConverter implements Converter<Integer> {
    
    @Override
    public Integer convert(Object value) throws InvalidConversionException {
        if(value == null) return null;
        
        if(value instanceof Number) {
            return ((Number) value).intValue();
        } else if(value instanceof Boolean) {
            if(value.equals(true)) return 1;
            else return 0;
        } else {
            String s = value.toString();
            try {
                return Color.parseColor(s);
            } catch (Exception e) {}
            
            try {
                return Integer.decode(s);
            } catch (NumberFormatException e) {}
            
            try {
                return Double.valueOf(s).intValue();
            } catch (NumberFormatException nfe) {
                throw new InvalidConversionException(value, Integer.class);
            }
        }
    }
}
