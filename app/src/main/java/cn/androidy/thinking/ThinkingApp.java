package cn.androidy.thinking;

import android.app.Application;
import android.os.Environment;
import android.webkit.CookieSyncManager;

import cn.androidy.common.utils.CommonUtils;

public class ThinkingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CookieSyncManager.createInstance(getApplicationContext());

    }

    public String getAppFileRootDirectory() {
        if (CommonUtils.isSDCardMounted()) {
            return Environment.getExternalStorageDirectory() + "/thinking";
        } else {
            return getFilesDir().toString();
        }
    }
}
