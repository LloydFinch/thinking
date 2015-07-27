package cn.androidy.thinking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.common.logger.LogDetailFragment;
import com.example.android.common.logger.LogListFragment;
import com.example.android.common.logger.LogManager;

import java.io.File;


public class LogActivity extends DemoDetailBaseActivity {

    public static final String KEY_LOG_TAG = "KEY_LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        FragmentManager fm = getSupportFragmentManager();
        String tag = getIntent().getStringExtra(KEY_LOG_TAG);
        File f;
        if (!TextUtils.isEmpty(tag)) {
            f = new File(LogManager.logFileDir, tag);
        } else {
            f = new File(LogManager.logFileDir);
        }
        if (f.isDirectory()) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.logfragmentContainer, LogListFragment.newInstance(tag));
            ft.commit();
        } else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.logfragmentContainer, LogDetailFragment.newInstance(tag));
            ft.commit();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_log;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    public static void checkLog(Context context, String tag) {
        Intent intent = new Intent(context, LogActivity.class);
        intent.putExtra(KEY_LOG_TAG, tag);
        context.startActivity(intent);
    }

    public void clickBack(View v) {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_crash_app) {
            Object o = null;
            o.toString();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
