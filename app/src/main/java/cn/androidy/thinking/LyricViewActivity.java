package cn.androidy.thinking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

import cn.androidy.thinking.views.ColorTrackView;

/**
 * Created by Rick Meng on 2015/6/16.
 */
public class LyricViewActivity extends SampleActivityBase implements View.OnClickListener {
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private ActionBar mActionBar;
    private FloatingActionButton mFloatingActionButton;
    private ArrayList<Integer> mFloatingActionButtonImageResIdList;
    private int mCurrentColorIndex = 0;
    private ColorTrackView mColorTrackView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric_view);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(this);
        mFloatingActionButtonImageResIdList = new ArrayList<Integer>();
        mFloatingActionButtonImageResIdList.add(R.drawable.ic_favorite_border_white_48dp);
        mFloatingActionButtonImageResIdList.add(R.drawable.ic_favorite_white_48dp);
        mFloatingActionButton.setImageResource(mFloatingActionButtonImageResIdList.get(mCurrentColorIndex));
        mCurrentColorIndex = (mCurrentColorIndex + 1) % mFloatingActionButtonImageResIdList.size();
        mColorTrackView = (ColorTrackView) findViewById(R.id.colorTrackView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lyric_view, menu);
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

    public void startLeftChange(View view) {
        mColorTrackView.setDirection(0);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mColorTrackView, "progress", 0, 1).setDuration(30 * 1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.floatingActionButton:
                mFloatingActionButton.setImageResource(mFloatingActionButtonImageResIdList.get(mCurrentColorIndex));
                mCurrentColorIndex = (mCurrentColorIndex + 1) % mFloatingActionButtonImageResIdList.size();
                break;
        }
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
    }
}
