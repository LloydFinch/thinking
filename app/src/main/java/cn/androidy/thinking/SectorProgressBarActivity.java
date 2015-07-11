package cn.androidy.thinking;

import android.animation.ValueAnimator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;

import cn.androidy.thinking.views.SectorProgressBar;


public class SectorProgressBarActivity extends DemoDetailBaseActivity {
    SectorProgressBar sectorProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectorProgressBar = (SectorProgressBar) findViewById(R.id.sectorProgressBar);
        sectorProgressBar.setMax(1000);
        ValueAnimator animator = ValueAnimator.ofInt(0, 1000).setDuration(10 * 1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sectorProgressBar.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sector_progress_bar;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sector_progress_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickChangeProgressBarStyle(View v) {
        SectorProgressBar.SectorStyle style = sectorProgressBar.getSectorStyle();
        if (style == SectorProgressBar.SectorStyle.RING) {
            sectorProgressBar.setSectorStyle(SectorProgressBar.SectorStyle.WEDGE);
        } else {
            sectorProgressBar.setSectorStyle(SectorProgressBar.SectorStyle.RING);
        }
    }
}
