package cn.androidy.logger.core;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;

import cn.androidy.common.utils.Phrase;
import cn.androidy.logger.data.LogWriteTask;

/**
 * print log into local files
 *
 * @author mwp 2015-7-28 15:35:33
 */
public class StorageLogPrinter extends MessageOnlyLogFilter {
    private String logDir;
    public static final String DIVIDOR = "=====================";

    public String getLogDir() {
        return logDir;
    }

    private static ExecutorService es = Executors.newSingleThreadExecutor();

    public StorageLogPrinter() {
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        if (!SupportLogger.isEnable()) {
            return;
        }
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

        appendToLog(tag, outputBuilder.toString());

        if (mNext != null) {
            mNext.println(priority, tag, msg, tr);
        }

    }

    /**
     * Takes a string and adds to it, with a separator, if the bit to be added isn't null. Since the logger takes so
     * many arguments that might be null, this method helps cut out some of the agonizing tedium of writing the same 3
     * lines over and over.
     *
     * @param source    StringBuilder containing the text to append to.
     * @param addStr    The String to append
     * @param delimiter The String to separate the source and appended strings. A tab or comma, for instance.
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

    /**
     * Outputs the string as a new line of log data in the LogView.
     */
    public void appendToLog(String tag, String s) {
        appendToLog(logDir, tag, s);
    }

    /**
     * Outputs the string as a new line of log data in the LogView.
     */
    public void appendToLog(String dir, String tag, String s) {
        LogWriteTask logWriteTask = new LogWriteTask(dir, tag, "\n" + s);
        es.execute(logWriteTask);
    }

    public static String getCurrentTimeTag() {
        GregorianCalendar calendar = new GregorianCalendar();
        String month = String.format(Locale.US, "%02d", (calendar.get(Calendar.MONTH) + 1));
        String date = String.format(Locale.US, "%02d", (calendar.get(Calendar.DATE)));
        String hour = String.format(Locale.US, "%02d", (calendar.get(Calendar.HOUR_OF_DAY)));
        String min = String.format(Locale.US, "%02d", (calendar.get(Calendar.MINUTE)));
        String sec = String.format(Locale.US, "%02d", (calendar.get(Calendar.SECOND)));
        String milis = String.format(Locale.US, "%02d", (calendar.get(Calendar.MILLISECOND)));
        String time = month + "-" + date + " " + hour + ":" + min + ":" + sec + "." + milis;
        return time;
    }
}
