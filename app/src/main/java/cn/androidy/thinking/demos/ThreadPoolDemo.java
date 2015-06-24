package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.DemoListActivity;
import cn.androidy.thinking.ThreadPoolDemoActivity;

/**
 * Created by Rick Meng on 2015/6/16.
 */
public class ThreadPoolDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "使用ThreadPool实现线程队列";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, ThreadPoolDemoActivity.class));
    }
    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
