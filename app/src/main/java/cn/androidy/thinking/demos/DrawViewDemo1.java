package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.DrawViewActivity1;

/**
 * Created by mwp on 2015/8/11.
 */
public class DrawViewDemo1 implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "View绘制流程一";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, DrawViewActivity1.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
