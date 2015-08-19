package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.DrawViewTreeActivity;

/**
 * Created by mwp on 2015/8/18.
 */
public class DrawViewTreeDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "演示View测量流程";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, DrawViewTreeActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ME || demoFamily == DemoFamily.ALL;
    }
}
