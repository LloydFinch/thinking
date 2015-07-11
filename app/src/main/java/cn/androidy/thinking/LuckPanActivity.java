package cn.androidy.thinking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import cn.androidy.thinking.demos.ThirdParthDemo;
import cn.androidy.thinking.views.LuckyPanView;


public class LuckPanActivity extends DemoDetailBaseActivity {
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;
    private String originalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        originalUrl = getIntent().getStringExtra(ThirdParthDemo.KEY_ORIGINAL_URL);
        mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {
                    mStartBtn.setImageResource(R.drawable.stop);
                    mLuckyPanView.luckyStart(1);
                } else {
                    if (!mLuckyPanView.isShouldEnd())

                    {
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_luck_pan;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_luck_pan, menu);
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
