package cn.androidy.thinking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.androidy.thinking.demos.ThirdParthDemo;
import cn.androidy.thinking.views.WaveView;


public class WaveViewActivity extends DemoDetailBaseActivity {
    private String originalUrl;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.wave_view)
    WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        originalUrl = getIntent().getStringExtra(ThirdParthDemo.KEY_ORIGINAL_URL);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wave_view;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wave_view, menu);
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
        } else if (id == R.id.action_original_link) {
            Uri uri = Uri.parse(originalUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
