package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.DrawableDemoActivity;

/**
 * Created by Rick Meng on 2015/6/19.
 */
public class DrawableDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "各式各样的Drawable";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, DrawableDemoActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL;
    }
}
