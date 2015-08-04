package cn.androidy.logger.data;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;

import cn.androidy.common.utils.FileUtils;
import cn.androidy.logger.core.SupportLogger;

public class LogReadTask extends AsyncTask<String, Void, LogEntity> {
    private ILogReadListener listener;
    private String dir;

    public LogReadTask(String dir, ILogReadListener listener) {
        super();
        this.dir = dir;
        this.listener = listener;
    }

    public static interface ILogReadListener {
        public void onLogGet(LogEntity entity);
    }

    @Override
    protected LogEntity doInBackground(String... params) {
        String tag = params[0];
        if (TextUtils.isEmpty(tag)) {
            tag = "defaultTag";
        }
        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        StringBuilder sb = FileUtils.readFile(new File(dir, tag).getAbsolutePath(), "utf-8");
        if (sb != null && sb.length() > 0) {
            return new LogEntity(tag, sb.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LogEntity result) {
        if (listener != null) {
            listener.onLogGet(result);
        }
    }
}
