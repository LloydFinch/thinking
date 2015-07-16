package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.LuckPanActivity;

/**
 * Created by Rick Meng on 2015/7/11.
 */
public class LuckyPanDemo extends ThirdParthDemo {
    @Override
    public String getDemoTitle() {
        return "抽奖转盘";
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ZHY;
    }

    @Override
    public String getOriginalUrl() {
        return "http://blog.csdn.net/lmj623565791/article/details/41722441";
    }

    @Override
    public Class<?> getTargetActivityClass() {
        return LuckPanActivity.class;
    }
}
