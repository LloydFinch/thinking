package com.example.android.common.logger;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;

import cn.androidy.thinking.utils.FileUtils;

public class LogReadTask extends AsyncTask<String, Void, LogEntity> {
	private ILogReadListener listener;

	public LogReadTask(ILogReadListener listener) {
		super();
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
		if (TextUtils.isEmpty(LogManager.logFileDir)) {
			return null;
		}
		StringBuilder sb = FileUtils.readFile(new File(LogManager.logFileDir, tag).getAbsolutePath(), "utf-8");
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
