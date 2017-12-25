package com.alexcovizzi.attrsbind;

import android.graphics.Color;

import com.alexcovizzi.typeconverter.Converter;
import com.alexcovizzi.typeconverter.exceptions.InvalidConversionException;

/**
 * Created by Alex on 24/12/2017.
 */

public class LongConverter implements Converter<Long> {
    
    @Override
    public Long convert(Object value) throws InvalidConversionException {
        if(value == null) return null;
    
        if(value instanceof Number) {
            return ((Number) value).longValue();
        } else if(value instanceof Boolean) {
            if(value.equals(true)) return 1L;
            else return 0L;
        } else {
            String s = value.toString();
            try {
                return (long) Color.parseColor(s);
            } catch (Exception e) {}
            
            try {
                return Long.decode(s);
            } catch (NumberFormatException e) {}
            
            try {
                return Double.valueOf(s).longValue();
            } catch (NumberFormatException nfe) {
                throw new InvalidConversionException(value, Long.class);
            }
        }
    }
}
