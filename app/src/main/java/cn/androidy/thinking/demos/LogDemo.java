package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.LogActivity;

/**
 * Created by Rick Meng on 2015/7/27.
 */
public class LogDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "查看日志";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, LogActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
