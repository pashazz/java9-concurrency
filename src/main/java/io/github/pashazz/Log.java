package io.github.pashazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingFormatArgumentException;


public class Log {

    public static boolean to_sysout = false;
    public static String getCallerCallerClassName() {
    StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
     String callerClass = null;
    for (int i=1; i<stElements.length; i++) {
        StackTraceElement ste = stElements[i];
        if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
            if (callerClass == null) {
                callerClass = format("%s::%s:%s", ste.getClassName(), ste.getMethodName(), ste.getLineNumber());
            }
        }
    }
    return callerClass;
 }
 public static void info(String format, Object... args) {
        if (to_sysout) {
            sysout(format, args);
        } else {
            get().info(format(format, args));
        }

 }

    private static String format(String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (MissingFormatArgumentException e) {
            System.err.println("Unable to find format argument for: "  + e.getFormatSpecifier() + " in: " + format);
            return format;
        }
    }

    private static void sysout(String format, Object... args) {
        try {
            System.out.printf(format + "\n", args);
        } catch (MissingFormatArgumentException e) {
            System.err.println("Unable to find format argument for string: " + format);
        }
 }

 public static void debug(String format, Object... args) {
        if(to_sysout) {
            sysout(format, args);
        } else
        get().debug(format(format, args));

 }

 public static void warn(String format, Object... args) {
        if (to_sysout) {
            sysout(format, args);
        } else
            get().warn(format(format, args));

 }

 public static void error(String format, Object... args) {
        if (to_sysout){
            sysout(format, args);
        } else
            get().error(format(format, args));

 }


 public static Logger get() {
        String caller = getCallerCallerClassName();
        if (caller == null) {
            return LoggerFactory.getLogger("DEFAULT");
        }
        return LoggerFactory.getLogger(caller);
 }
}
