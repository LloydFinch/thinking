package cn.androidy.thinking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.common.activities.SampleActivityBase;
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
        ObjectAnimator.ofFloat(mColorTrackView, "progress", 0, 1).setDuration(2000)
                .start();
    }

    public void startRightChange(View view) {
        mColorTrackView.setDirection(1);
        ObjectAnimator.ofFloat(mColorTrackView, "progress", 0, 1).setDuration(2000)
                .start();
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
}
