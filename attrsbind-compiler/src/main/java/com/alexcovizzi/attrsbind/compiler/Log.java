package com.alexcovizzi.attrsbind.compiler;


import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by Alex on 23/11/2017.
 */

class Log {
    private static final int DEBUG = 0;
    private static final int WARNING = 1;
    private static final int ERROR = 2;
    
    private static final Log ourInstance = new Log();
    
    private Messager messager;
    private int level = DEBUG;
    
    static Log getInstance() {
        return ourInstance;
    }
    
    private Log() {
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public Log setMessager(Messager messager) {
        this.messager = messager;
        return this;
    }
    
    public static void d(Class cls, Object msg) {
        getInstance().log(DEBUG, cls, msg);
    }
    
    public static void d(String tag, Object msg) {
        getInstance().log(DEBUG, tag, msg);
    }
    
    public static void w(Class cls, Object msg) {
        getInstance().log(WARNING, cls, msg);
    }
    
    public static void w(String tag, Object msg) {
        getInstance().log(WARNING, tag, msg);
    }
    
    public static void e(Class cls, Object msg) {
        getInstance().log(ERROR, cls, msg);
    }
    
    public static void e(String tag, Object msg) {
        getInstance().log(ERROR, tag, msg);
    }
    
    private void log(int level, Class cls, Object msg) {
        log(level, cls.getCanonicalName(), msg);
    }
    
    private void log(int level, String tag, Object msg) {
        if(messager == null) throw new NullPointerException("Log : Messager must be set.");
        String tg = tag+" : ";
        Diagnostic.Kind kind = Diagnostic.Kind.ERROR;
        if(level == 1) kind = Diagnostic.Kind.WARNING;
        if(level == 0) kind = Diagnostic.Kind.NOTE;
        
        if(this.level <= level) {
            messager.printMessage(kind, tg + msg.toString());
        }
    }
}
