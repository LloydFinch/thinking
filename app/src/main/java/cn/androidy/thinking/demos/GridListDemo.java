package cn.androidy.thinking.demos;


import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.GridListActivity;

public class GridListDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "类List和Grid混合使用";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, GridListActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
