package com.example.android.common.logger;

public class LogManager {
    public static String logFileDir = "";
    public static final String TAG_CRASH = "CrashLog";
    public static final String TAG_EXCEPTION = "EXCEPTION";

    public static LogLocalFileManager enableLogLocal(boolean enable) {
        // 非debug模式下不允许任何log输出
        if (!enable) {
            return null;
        }
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);
        LogLocalFileManager logLocalFileManager = LogLocalFileManager.getInstance();
        logWrapper.setNext(logLocalFileManager);
        return logLocalFileManager;
    }
}
