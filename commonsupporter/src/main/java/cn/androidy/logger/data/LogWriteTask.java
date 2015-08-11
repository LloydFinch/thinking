package cn.androidy.logger.data;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.androidy.common.utils.FileUtils;
import cn.androidy.logger.core.SupportLogger;

public class LogWriteTask implements Runnable {
    private String dir;
    private String tag;
    private String logContent;

    public LogWriteTask(String dir, String tag, String logContent) {
        super();
        this.dir = dir;
        this.tag = tag;
        this.logContent = logContent;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(logContent) || TextUtils.isEmpty(dir)) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            tag = "defaultTag";
        }
        String fileName = new File(dir, tag).getAbsolutePath();
        FileUtils.writeFile(fileName, logContent, true);
    }
}
