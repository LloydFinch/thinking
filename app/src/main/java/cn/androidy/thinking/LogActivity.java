package cn.androidy.thinking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import cn.androidy.logger.core.SupportLogger;
import justlog.fragments.LogDetailFragment;
import justlog.fragments.LogListFragment;


public class LogActivity extends AppCompatActivity {

    public static final String KEY_LOG_TAG = "KEY_LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_log);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        String tag = getIntent().getStringExtra(KEY_LOG_TAG);
        File f;
        if (!TextUtils.isEmpty(tag)) {
            f = new File(SupportLogger.getCommonLogDir(), tag);
        } else {
            f = new File(SupportLogger.getCommonLogDir());
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

    public static void checkLog(Context context, String tag) {
        Intent intent = new Intent(context, LogActivity.class);
        intent.putExtra(KEY_LOG_TAG, tag);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }
}
