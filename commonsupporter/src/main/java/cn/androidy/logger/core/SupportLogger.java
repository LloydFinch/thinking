package cn.androidy.logger.core;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import org.acra.ACRA;
import org.acra.sender.StorageReportSender;

import java.io.File;
import java.util.logging.Logger;

import cn.androidy.common.utils.FileUtils;
import cn.androidy.logger.data.LogListReadTask;
import cn.androidy.logger.data.LogReadTask;

/**
 * @author mwp 2015-7-28 15:35:48
 */
public class SupportLogger {
    private static boolean ENABLE_LOG = true;
    private static SupportLogger instance;
    private StorageLogPrinter storageLogPrinter;
    private LoggerSettings settings;

    private SupportLogger() {
        storageLogPrinter = new StorageLogPrinter();
        settings = new LoggerSettings();
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


    public static void init(Application application) {
        init(application, null);
    }

    public static StorageLogPrinter getPrinter() {
        return getInstance().getStorageLogPrinter();
    }

    public static void init(Application application, LoggerSettings settings) {
        ACRA.init(application);
        StorageReportSender sender = new StorageReportSender(application);
        ACRA.getErrorReporter().setReportSender(sender);
        Context context = application.getApplicationContext();
        String dir;
        if (settings != null && !TextUtils.isEmpty(settings.getDir())) {
            dir = settings.getDir();
        } else {
            dir = FileUtils.getDiskCacheDir(context, "log").getAbsolutePath();
        }
        getPrinter().setLogDir(dir);
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);
        logWrapper.setNext(getPrinter());
    }

    public static void readLog(String tag, LogReadTask.ILogReadListener listener) {
        new LogReadTask(SupportLogger.getCommonLogDir(), listener).execute(tag);
    }

    public static void readLogList(String tag, LogListReadTask.ILogListReadListener listener) {
        new LogListReadTask(SupportLogger.getCommonLogDir(), listener).execute(tag);
    }

    /**
     * @param enable if false is set,no log will be print, true otherwise
     */
    public static void enable(boolean enable) {
        ENABLE_LOG = enable;
    }

    public static String getCommonLogDir() {
        return getPrinter().getLogDir();
    }

    public static boolean isEnable() {
        return ENABLE_LOG;
    }

    public static class LoggerSettings {
        private String dir;

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }
}
