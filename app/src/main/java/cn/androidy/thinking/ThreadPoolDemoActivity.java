package cn.androidy.thinking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;
import com.example.android.common.activities.SampleActivityBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import cn.androidy.logger.core.Log;
import cn.androidy.logger.core.LogWrapper;
import cn.androidy.logger.core.MessageOnlyLogFilter;
import cn.androidy.logger.view.LogFragment;
import cn.androidy.thinking.concurrent.PrioritizedRunnable;
import cn.androidy.thinking.concurrent.ThreadJob;
import cn.androidy.thinking.concurrent.ThreadResultConsumer;

/**
 * Created by Rick Meng on 2015/6/16.
 */
public class ThreadPoolDemoActivity extends SampleActivityBase implements View.OnClickListener, ThreadResultConsumer, View.OnLongClickListener {
    public static final String TAG = "ThreadPoolDemoActivity";
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private ActionBar mActionBar;
    private FloatingActionButton mFloatingActionButton;
    private ArrayList<Integer> mFloatingActionButtonImageResIdList;
    private int mCurrentColorIndex = 0;
    private FifoPriorityThreadPoolExecutor fifoPriorityThreadPoolExecutor;
    private Random r;
    private ThreadJob mLastJob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threadpooldemo);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(this);
        mFloatingActionButton.setOnLongClickListener(this);
        mFloatingActionButtonImageResIdList = new ArrayList<Integer>();
        mFloatingActionButtonImageResIdList.add(R.drawable.ic_add_white_48dp);
        mFloatingActionButton.setImageResource(mFloatingActionButtonImageResIdList.get(mCurrentColorIndex));
        mCurrentColorIndex = (mCurrentColorIndex + 1) % mFloatingActionButtonImageResIdList.size();
        r = new Random();
        final int cores = Math.max(1, getAvailableProcessors());
        fifoPriorityThreadPoolExecutor = new FifoPriorityThreadPoolExecutor(cores);
    }

    private String createThreadName() {
        GregorianCalendar calendar = new GregorianCalendar();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        int ms = calendar.get(Calendar.MILLISECOND);
        return h + ":" + m + ":" + s + "." + ms;
    }

    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Create a chain of targets that will receive log data
     */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());
        Log.i(TAG, "Ready");
        Log.i(TAG, "系统支持最大线程数：" + String.valueOf(ThreadJob.getAvailableProcessors()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.floatingActionButton:
                mLastJob = new ThreadJob(fifoPriorityThreadPoolExecutor, this);
                mLastJob.start(new PrioritizedRunnable(mLastJob, createThreadName(), r.nextInt(100)));
                mCurrentColorIndex = (mCurrentColorIndex + 1) % mFloatingActionButtonImageResIdList.size();
                break;
        }
    }

    @Override
    public void onJobComplete(ThreadJob job) {
        Log.i(TAG, job.printRunnableInfo());
    }

    @Override
    public void onJobCanceled(ThreadJob job) {
        Log.i(TAG, job.printRunnableInfo() + "--->取消");
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLastJob != null) {
            mLastJob.cancel();
            return true;
        }
        return false;
    }
}
