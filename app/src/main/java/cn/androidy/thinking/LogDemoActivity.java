package cn.androidy.thinking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.androidy.logger.core.Log;

public class LogDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_demo);
    }

    public void checkAllLog(View v) {
        LogActivity.checkLog(this, null);
    }

    public void checkExceptionLog(View v) {
        LogActivity.checkLog(this, "Exception");
    }

    public void checkCrashLog(View v) {
        LogActivity.checkLog(this, "Crash");
    }

    public void createNormalLog(View v) {
        Log.d("MyTag", "这是一条普通的日志.");
    }

    public void createExceptionLog(View v) {
        try {
            Object o = null;
            o.toString();
        } catch (Exception e) {
            Log.e("Exception", "发生了一个异常,e:" + e.getMessage(), e);
        }
    }

    public void createCrashLog(View v) {
        Object o = null;
        o.toString();
    }
}
