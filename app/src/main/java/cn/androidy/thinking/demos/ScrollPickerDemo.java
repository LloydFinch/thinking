package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.ScrollPickerActivity;

/**
 * Created by Rick Meng on 2015/6/23.
 */
public class ScrollPickerDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "底部滚轮选择器";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, ScrollPickerActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
