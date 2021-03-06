package org.acra.sender;

import android.content.Context;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.androidy.common.utils.FileUtils;
import cn.androidy.common.utils.StringUtils;
import cn.androidy.logger.core.Log;
import cn.androidy.logger.core.StorageLogPrinter;
import cn.androidy.logger.core.SupportLogger;

public class StorageReportSender implements ReportSender {
    private String dir;
    private ExecutorService es = Executors.newFixedThreadPool(3);

    public StorageReportSender(Context context) {
        this.dir = FileUtils.getDiskCacheDir(context, "log/Crash").getAbsolutePath();
    }

    @Override
    public void send(Context context, final CrashReportData errorContent) throws ReportSenderException {
        es.execute(new Runnable() {

            @Override
            public void run() {
                if (StringUtils.isEmpty(dir)) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("********Android系统版本********\n" + errorContent.getProperty(ReportField.ANDROID_VERSION)
                        + "\n");
                sb.append("************手机品牌***********\n" + errorContent.getProperty(ReportField.BRAND) + "\n");
                sb.append("********PACKAGE_NAME********\n" + errorContent.getProperty(ReportField.PACKAGE_NAME) + "\n");
                sb.append("******APP_VERSION_CODE******\n" + errorContent.getProperty(ReportField.APP_VERSION_CODE)
                        + "\n");
                sb.append("******APP_VERSION_NAME******\n" + errorContent.getProperty(ReportField.APP_VERSION_NAME)
                        + "\n");
                sb.append("**********App启动时间**********\n" + errorContent.getProperty(ReportField.USER_APP_START_DATE)
                        + "\n");
                sb.append("**********App闪退时间**********\n" + errorContent.getProperty(ReportField.USER_CRASH_DATE)
                        + "\n");
                sb.append("************闪退Log***********\n" + errorContent.getProperty(ReportField.STACK_TRACE) + "\n");
                SupportLogger.getPrinter().appendToLog(SupportLogger.getCommonLogDir()+"/Crash", StorageLogPrinter.getCurrentTimeTag(),sb.toString());
            }
        });
    }
}
