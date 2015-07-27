package cn.androidy.thinking;

import android.app.Application;
import android.os.Environment;
import android.webkit.CookieSyncManager;

import com.example.android.common.logger.LogManager;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import cn.androidy.thinking.utils.CommonUtils;
import cn.androidy.thinking.utils.QfqReportSender;

@ReportsCrashes(mode = ReportingInteractionMode.TOAST, forceCloseDialogAfterToast = false, // optional,
// default
// false
        resToastText = R.string.crash_toast_text)
public class ThinkingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CookieSyncManager.createInstance(getApplicationContext());
        // logger模块初始化
        // 崩溃日志收集模块初始化
        ACRA.init(this);
        /**
         * 初始化Log目录
         */
        String logDir = getAppFileRootDirectory() + "/log";
        LogManager.logFileDir = logDir;
        QfqReportSender sender = QfqReportSender.toDir(logDir + "/CrashLog");
        ACRA.getErrorReporter().setReportSender(sender);
        LogManager.enableLogLocal(enableSaveLog());
    }

    public String getAppFileRootDirectory() {
        if (CommonUtils.isSDCardMounted()) {
            return Environment.getExternalStorageDirectory() + "/thinking";
        } else {
            return getFilesDir().toString();
        }
    }

    public boolean enableSaveLog() {
        return true;
    }
}
