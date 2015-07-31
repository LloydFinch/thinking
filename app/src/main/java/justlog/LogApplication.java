package justlog;

import android.app.Application;
import android.os.Environment;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.StorageReportSender;

import cn.androidy.common.utils.CommonUtils;
import cn.androidy.logger.core.SupportLogger;
import cn.androidy.thinking.R;

/**
 * Created by Rick Meng on 2015/7/28.
 */
@ReportsCrashes(mode = ReportingInteractionMode.TOAST, forceCloseDialogAfterToast = false, // optional,
// default
// false
        resToastText = R.string.crash_toast_text)
public class LogApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        String logDir = getAppFileRootDirectory() + "/log";
        StorageReportSender sender = StorageReportSender.toDir(logDir + "/CrashLog");
        ACRA.getErrorReporter().setReportSender(sender);
        SupportLogger.intoDir(logDir);
        SupportLogger.start();
    }


    public String getAppFileRootDirectory() {
        if (CommonUtils.isSDCardMounted()) {
            return Environment.getExternalStorageDirectory() + "/supportlog";
        } else {

            return getFilesDir().toString();
        }
    }
}
