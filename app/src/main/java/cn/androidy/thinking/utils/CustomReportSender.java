package cn.androidy.thinking.utils;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.content.Context;

import com.example.android.common.logger.LogLocalFileManager;
import com.example.android.common.logger.LogManager;

public class CustomReportSender implements ReportSender {
    private String dir;
    private ExecutorService es = Executors.newFixedThreadPool(3);
    private static final int MAX_LOG_FILE_NUM = 50;

    public static CustomReportSender toDir(String dir) {
        CustomReportSender sender = new CustomReportSender();
        sender.dir = dir;
        return sender;
    }

    @Override
    public void send(Context context, final CrashReportData errorContent) throws ReportSenderException {
        es.execute(new Runnable() {

            @Override
            public void run() {
                if (StringUtils.isEmpty(dir)) {
                    return;
                }
                String fileName = LogLocalFileManager.getCurrentTimeTag();
                File dirF = new File(dir);
                if (dirF.listFiles() != null && dirF.listFiles().length >= MAX_LOG_FILE_NUM) {
                    FileUtils.deleteFile(dir);
                }
                File file = new File(dir, fileName);
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
                FileUtils.writeFile(file.getAbsolutePath(), sb.toString());
            }
        });
    }
}
