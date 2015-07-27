package com.example.android.common.logger;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import cn.androidy.thinking.utils.Phrase;

public class LogLocalFileManager extends MessageOnlyLogFilter {
	private static LogLocalFileManager instance;
	private String tag;
	public static final String DIVIDOR = "=====================";

	private static LogLocalFileManager newInstance() {
		return new LogLocalFileManager();
	}

	public static final LogLocalFileManager getInstance() {
		if (instance == null) {
			synchronized (LogLocalFileManager.class) {
				if (instance == null) {
					instance = newInstance();
				}
			}
		}
		return instance;
	}

	@Override
	public void println(int priority, String tag, String msg, Throwable tr) {
		this.tag = tag;
		String priorityStr = null;

		// For the purposes of this View, we want to print the priority as readable text.
		switch (priority) {
		case android.util.Log.VERBOSE:
			priorityStr = "VERBOSE";
			break;
		case android.util.Log.DEBUG:
			priorityStr = "DEBUG";
			break;
		case android.util.Log.INFO:
			priorityStr = "INFO";
			break;
		case android.util.Log.WARN:
			priorityStr = "WARN";
			break;
		case android.util.Log.ERROR:
			priorityStr = "ERROR";
			break;
		case android.util.Log.ASSERT:
			priorityStr = "ASSERT";
			break;
		default:
			break;
		}

		// Handily, the Log class has a facility for converting a stack trace into a usable string.
		String exceptionStr = null;
		if (tr != null) {
			exceptionStr = android.util.Log.getStackTraceString(tr);
		}

		// Take the priority, tag, message, and exception, and concatenate as necessary
		// into one usable line of text.
		final StringBuilder outputBuilder = new StringBuilder();

		String delimiter = " ";
		appendIfNotNull(outputBuilder, DIVIDOR, "\n");
		appendIfNotNull(outputBuilder, getCurrentTimeTag(), ":\n");
		appendIfNotNull(outputBuilder, Phrase.from("[{priority}]").put("priority", priorityStr).format().toString(),
				delimiter);
		appendIfNotNull(outputBuilder, tag, delimiter + "\n");
		appendIfNotNull(outputBuilder, msg, delimiter);
		appendIfNotNull(outputBuilder, exceptionStr, delimiter);

		appendToLog(outputBuilder.toString());

		if (mNext != null) {
			mNext.println(priority, tag, msg, tr);
		}

	}

	/**
	 * Takes a string and adds to it, with a separator, if the bit to be added isn't null. Since the logger takes so
	 * many arguments that might be null, this method helps cut out some of the agonizing tedium of writing the same 3
	 * lines over and over.
	 * 
	 * @param source
	 *            StringBuilder containing the text to append to.
	 * @param addStr
	 *            The String to append
	 * @param delimiter
	 *            The String to separate the source and appended strings. A tab or comma, for instance.
	 * @return The fully concatenated String as a StringBuilder
	 */
	private StringBuilder appendIfNotNull(StringBuilder source, String addStr, String delimiter) {
		if (addStr != null) {
			if (addStr.length() == 0) {
				delimiter = "";
			}

			return source.append(addStr).append(delimiter);
		}
		return source;
	}

	/** Outputs the string as a new line of log data in the LogView. */
	public void appendToLog(String s) {
		LogWriteTask.saveLog(tag, "\n" + s);
	}

	public static String getCurrentTimeTag() {
		GregorianCalendar calendar = new GregorianCalendar();
		String month = String.format(Locale.US, "%02d", (calendar.get(Calendar.MONTH) + 1));
		String date = String.format(Locale.US, "%02d", (calendar.get(Calendar.DATE)));
		String hour = String.format(Locale.US, "%02d", (calendar.get(Calendar.HOUR_OF_DAY)));
		String min = String.format(Locale.US, "%02d", (calendar.get(Calendar.MINUTE)));
		String sec = String.format(Locale.US, "%02d", (calendar.get(Calendar.SECOND)));
		String time = month + "-" + date + "-" + hour + "-" + min + "-" + sec;
		return time;
	}
}
