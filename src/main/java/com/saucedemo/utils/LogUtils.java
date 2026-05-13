package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    // Initialize Log4j logs
    private static final Logger Log = LogManager.getLogger(LogUtils.class);

    public static void info(String message) {
        Log.info(message);
    }

    public static void error(String message) {
        Log.error(message);
    }

    public static void warn(String message) {
        Log.warn(message);
    }

    public static void debug(String message) {
        Log.debug(message);
    }
}