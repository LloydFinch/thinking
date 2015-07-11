package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.SectorProgressBarActivity;

/**
 * Created by Rick Meng on 2015/7/11.
 */
public class SectorProgressBarDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "扇形进度条";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, SectorProgressBarActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
