package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.CurveActivity;

/**
 * Created by Rick Meng on 2015/6/26.
 */
public class CurveDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "各种各样的曲线";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, CurveActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
