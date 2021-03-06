package cn.androidy.thinking;

import android.animation.ObjectAnimator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class CurveActivity extends DemoDetailBaseActivity {
    private View curveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curveView = findViewById(R.id.curveView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_curve;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curve, menu);
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
        } else if (id == R.id.action_anim_x) {
            ObjectAnimator.ofFloat(curveView, "phaseX", 0, 1).setDuration(2000).start();
        } else if (id == R.id.action_anim_y) {
            ObjectAnimator.ofFloat(curveView, "phaseY", 0, 1).setDuration(2000).start();
        } else if (id == R.id.action_anim_xy) {
            ObjectAnimator.ofFloat(curveView, "phaseY", 0, 1).setDuration(2000).start();
            ObjectAnimator.ofFloat(curveView, "phaseX", 0, 1).setDuration(2000).start();
        }

        return super.onOptionsItemSelected(item);
    }
}
