package cn.androidy.logger.core;

import cn.androidy.logger.data.LogListReadTask;
import cn.androidy.logger.data.LogReadTask;

/**
 * @author mwp 2015-7-28 15:35:48
 */
public class SupportLogger {
    private static boolean ENABLE_LOG = false;
    private static SupportLogger instance;
    private StorageLogPrinter storageLogPrinter;

    private SupportLogger() {
        storageLogPrinter = new StorageLogPrinter();
    }

    public StorageLogPrinter getStorageLogPrinter() {
        return storageLogPrinter;
    }

    private static SupportLogger newInstance() {
        SupportLogger supportLogger = new SupportLogger();
        return supportLogger;
    }

    private static SupportLogger getInstance() {
        if (instance == null) {
            instance = newInstance();
        }
        return instance;
    }

    /**
     * the file directory to save log files
     */
    public static void intoDir(String dir) {
        SupportLogger.getInstance().getStorageLogPrinter().setLogDir(dir);
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);
        logWrapper.setNext(getInstance().getStorageLogPrinter());
    }

    public static void readLog(String tag, LogReadTask.ILogReadListener listener) {
        new LogReadTask(SupportLogger.getCommonLogDir(), listener).execute(tag);
    }

    public static void readLogList(String tag, LogListReadTask.ILogListReadListener listener) {
        new LogListReadTask(SupportLogger.getCommonLogDir(), listener).execute(tag);
    }

    public static void start() {
        ENABLE_LOG = true;
    }

    public static void stop() {
        ENABLE_LOG = false;
    }

    /**
     * @param enable if false is set,no log will be print, true otherwise
     */
    public static void enable(boolean enable) {
        ENABLE_LOG = enable;
    }

    public static String getCommonLogDir() {
        return getInstance().getStorageLogPrinter().getLogDir();
    }

    public static boolean isEnable() {
        return ENABLE_LOG;
    }
}
