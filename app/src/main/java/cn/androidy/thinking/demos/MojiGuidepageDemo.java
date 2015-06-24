package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.MojiGuidepageActivity;

/**
 * Created by Rick Meng on 2015/6/24.
 */
public class MojiGuidepageDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "仿墨迹天气引导页";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, MojiGuidepageActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL;
    }
}
