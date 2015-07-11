package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.LuckPanActivity;

/**
 * Created by Rick Meng on 2015/7/11.
 */
public class LuckyPanDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "抽奖转盘";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, LuckPanActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ZHY;
    }
}
