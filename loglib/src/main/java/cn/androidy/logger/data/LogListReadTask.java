package cn.androidy.logger.data;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.androidy.common.utils.FileUtils;

public class LogListReadTask extends AsyncTask<String, Void, List<ILog>> {
    private ILogListReadListener listener;
    private String dir;

    public LogListReadTask(String dir, ILogListReadListener listener) {
        super();
        this.dir = dir;
        this.listener = listener;
    }

    public static interface ILogListReadListener {
        public void onLogGet(List<ILog> result);
    }

    @Override
    protected List<ILog> doInBackground(String... params) {
        String logTag = params[0];

        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        File file;
        if (TextUtils.isEmpty(logTag)) {
            file = new File(dir);
        } else {
            file = new File(dir, logTag);
        }
        if (!file.exists()) {
            return null;
        }
        if (!file.isDirectory()) {
            return null;
        }
        List<ILog> result = new ArrayList<>();
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
    protected void onPostExecute(List<ILog> result) {
        if (listener != null) {
            listener.onLogGet(result);
        }
    }
}
