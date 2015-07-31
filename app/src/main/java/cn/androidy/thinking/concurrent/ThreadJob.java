package cn.androidy.thinking.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Rick Meng on 2015/6/16.
 */
public class ThreadJob implements ThreadJobCallback {
    private final ExecutorService executorService;
    private PrioritizedRunnable runnable;
    private volatile Future<?> future;
    private static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper(), new MainThreadCallback());
    private static final int MSG_COMPLETE = 1;
    private static final int MSG_EXCEPTION = 2;
    private ThreadResultConsumer consumer;
    private boolean isCancelled;

    public ThreadJob(ExecutorService es) {
        executorService = es;
    }

    public ThreadJob(ExecutorService es, ThreadResultConsumer consumer) {
        executorService = es;
        this.consumer = consumer;
    }

    public String printRunnableInfo() {
        return runnable.getName() + "    priority:" + runnable.getPriority();
    }

    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public void start(PrioritizedRunnable runnable) {
        this.runnable = runnable;
        future = executorService.submit(runnable);
    }

    @Override
    public void onJobComplete() {
        MAIN_THREAD_HANDLER.obtainMessage(MSG_COMPLETE, this).sendToTarget();
    }

    private static class MainThreadCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message message) {
            if (MSG_COMPLETE == message.what || MSG_EXCEPTION == message.what) {
                ThreadJob job = (ThreadJob) message.obj;
                if (MSG_COMPLETE == message.what) {
                    job.handleResultOnMainThread();
                } else {
                    job.handleExceptionOnMainThread();
                }
                return true;
            }

            return false;
        }
    }

    private void handleResultOnMainThread() {
        consumer.onJobComplete(this);
    }

    private void handleExceptionOnMainThread() {

    }

    public void cancel() {
        runnable.cancel();
        consumer.onJobCanceled(this);
        Future currentFuture = future;
        if (currentFuture != null) {
            currentFuture.cancel(true);
        }
        isCancelled = true;
    }
}
