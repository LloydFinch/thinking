package com.example.android.common.logger;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.androidy.thinking.utils.FileUtils;

public class LogListReadTask extends AsyncTask<String, Void, List<LogItem>> {
    private ILogListReadListener listener;

    public LogListReadTask(ILogListReadListener listener) {
        super();
        this.listener = listener;
    }

    public static interface ILogListReadListener {
        public void onLogGet(List<LogItem> result);
    }

    @Override
    protected List<LogItem> doInBackground(String... params) {
        String logTag = params[0];

        if (TextUtils.isEmpty(LogManager.logFileDir)) {
            return null;
        }
        File file;
        if (TextUtils.isEmpty(logTag)) {
            file = new File(LogManager.logFileDir);
        } else {
            file = new File(LogManager.logFileDir, logTag);
        }
        if (!file.exists()) {
            return null;
        }
        if (!file.isDirectory()) {
            return null;
        }
        List<LogItem> result = new ArrayList<>();
        for (File f : file.listFiles()) {
            if (f != null && f.isFile()) {
                StringBuilder sb = FileUtils.readFile(f.getAbsolutePath(), "utf-8");
                if (sb != null && sb.length() > 0) {
                    LogEntity logEntity = new LogEntity(f.getName(), sb.toString());
                    result.add(logEntity);
                }
            } else if (f != null && f.isDirectory()) {
                LogCategory logCategory = new LogCategory(f.getName());
                result.add(logCategory);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<LogItem> result) {
        if (listener != null) {
            listener.onLogGet(result);
        }
    }
}
