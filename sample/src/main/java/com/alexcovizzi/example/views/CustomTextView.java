package com.alexcovizzi.example.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.alexcovizzi.attrsbind.AttrValueReader;
import com.alexcovizzi.attrsbind.AttrsBinder;
import com.alexcovizzi.attrsbind.annotations.BindAttr;

/**
 * Created by Alex on 03/12/2017.
 */

public class CustomTextView extends TextView {
    
    @BindAttr(def="0x001") int rotation;
    
    public CustomTextView(Context context) {
        super(context);
    }
    
    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        AttrsBinder.bind(this, attrs);
        Log.e(getClass().toString(), "rotation: "+rotation);
    }
    
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AttrsBinder.bind(this, attrs, defStyleAttr);
    }
}
