package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.LuckPanActivity;

/**
 * Created by Rick Meng on 2015/7/11.
 */
public abstract class ThirdParthDemo extends SimpleDemo {
    public static final String KEY_ORIGINAL_URL = "key_original_url";

    @Override
    public String getDemoTitle() {
        return null;
    }

    @Override
    public void demonstrate(Context context) {
        Intent intent = new Intent(context, getTargetActivityClass());
        intent.putExtra(KEY_ORIGINAL_URL, getOriginalUrl());
        context.startActivity(intent);
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return false;
    }

    public abstract String getOriginalUrl();

    public abstract Class<?> getTargetActivityClass();
}
