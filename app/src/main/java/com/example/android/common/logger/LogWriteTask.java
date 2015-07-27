package com.example.android.common.logger;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.androidy.thinking.utils.FileUtils;

public class LogWriteTask implements Runnable {
	private String tag;
	private String logContent;
	private static ExecutorService es = Executors.newSingleThreadExecutor();

	public static void saveLog(String tag, String logContent) {
		LogWriteTask logWriteTask = new LogWriteTask(tag, logContent);
		es.execute(logWriteTask);
	}

	public LogWriteTask(String tag, String logContent) {
		super();
		this.tag = tag;
		this.logContent = logContent;
	}

	@Override
	public void run() {
		if (TextUtils.isEmpty(logContent) || TextUtils.isEmpty(LogManager.logFileDir)) {
			return;
		}
		if (TextUtils.isEmpty(tag)) {
			tag = "defaultTag";
		}
		String tmpTag = tag + "tmp";
		String fileName = new File(LogManager.logFileDir, tag).getAbsolutePath();
		String tmpFileName = new File(LogManager.logFileDir, tmpTag).getAbsolutePath();
		try {
			FileUtils.copyFile(fileName, tmpFileName);
			FileUtils.writeFile(fileName, logContent);
			FileUtils.writeFile(fileName, new FileInputStream(tmpFileName), true);
			FileUtils.deleteFile(tmpFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
