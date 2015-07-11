package cn.androidy.thinking.demos;

import android.content.Context;

/**
 * Created by Rick Meng on 2015/7/11.
 */
public abstract class SimpleDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return null;
    }

    @Override
    public void demonstrate(Context context) {

    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return false;
    }
}
