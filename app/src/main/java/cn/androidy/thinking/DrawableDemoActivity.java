package cn.androidy.thinking;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import cn.androidy.thinking.drawables.DrawableBuilder;


public class DrawableDemoActivity extends DemoDetailBaseActivity {
    private View view;
    private DisplayMetrics dm;
    private SeekBar radiusSeekbar;
    private SeekBar insetwidthSeekbar;
    private Drawable d;
    private int INIT_COLOR;
    private float INIT_INSETWIDTH;
    private float INIT_RADIUS;
    private int color;
    private float radius;
    private float insetWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = findViewById(R.id.view);
        radiusSeekbar = (SeekBar) findViewById(R.id.radiusSeekbar);
        insetwidthSeekbar = (SeekBar) findViewById(R.id.insetwidthSeekbar);
        dm = getResources().getDisplayMetrics();
        INIT_COLOR = Color.RED;
        INIT_RADIUS = 2 * dm.density;
        INIT_INSETWIDTH = 1 * dm.density;
        color = INIT_COLOR;
        radius = INIT_RADIUS;
        insetWidth = INIT_INSETWIDTH;
        d = DrawableBuilder.createRoundedRectDrawable(color, radius, insetWidth);
        view.setBackground(d);

        radiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius = INIT_RADIUS + progress * 0.3f * dm.density;
                d = DrawableBuilder.createRoundedRectDrawable(color, radius, insetWidth);
                view.setBackground(d);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        insetwidthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                insetWidth = INIT_INSETWIDTH + progress * 0.2f * dm.density;
                d = DrawableBuilder.createRoundedRectDrawable(color, radius, insetWidth);
                view.setBackground(d);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    boolean colorFlag = false;

    public void tabColor(View v) {
        if (colorFlag) {
            color = Color.RED;
            colorFlag = false;
        } else {
            color = Color.BLACK;
            colorFlag = true;
        }
        d = DrawableBuilder.createRoundedRectDrawable(color, radius, insetWidth);
        view.setBackground(d);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawable_demo;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawable_demo, menu);
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
}
