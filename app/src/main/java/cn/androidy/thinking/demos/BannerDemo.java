package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.BannerActivity;

/**
 * Created by Rick Meng on 2015/6/18.
 */
public class BannerDemo extends ThirdParthDemo {
    @Override
    public String getDemoTitle() {
        return "循环广告位";
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.RENYUGANG || demoFamily == DemoFamily.ALL;
    }

    @Override
    public String getOriginalUrl() {
        return "http://blog.csdn.net/singwhatiwanna/article/details/46541225";
    }

    @Override
    public Class<?> getTargetActivityClass() {
        return BannerActivity.class;
    }
}
