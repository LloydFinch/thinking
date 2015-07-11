package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.BannerActivity;

/**
 * Created by Rick Meng on 2015/6/18.
 */
public class BannerDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "循环广告位";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, BannerActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.RENYUGANG || demoFamily == DemoFamily.ALL;
    }
}
