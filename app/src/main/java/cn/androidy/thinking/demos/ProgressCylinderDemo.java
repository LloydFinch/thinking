package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.ProgressCylinderActivity;

/**
 * Created by Rick Meng on 2015/7/15.
 */
public class ProgressCylinderDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "染缸进度条";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, ProgressCylinderActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
