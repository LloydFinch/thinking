package cn.androidy.thinking;

import android.app.Application;
import android.os.Environment;
import android.webkit.CookieSyncManager;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.StorageReportSender;

import cn.androidy.common.utils.CommonUtils;
import cn.androidy.logger.core.SupportLogger;

/**
 * Created by Rick Meng on 2015/7/28.
 */
@ReportsCrashes(mode = ReportingInteractionMode.TOAST, forceCloseDialogAfterToast = false, // optional,
// default
// false
        resToastText = R.string.crash_toast_text)
public class ThinkingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SupportLogger.init(this);
        SupportLogger.enable(true);
    }
}
