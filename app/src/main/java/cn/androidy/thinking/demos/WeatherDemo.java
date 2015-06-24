package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.WeatherActivity;

/**
 * Created by Rick Meng on 2015/6/17.
 */
public class WeatherDemo implements IDemoEntry {

    @Override
    public String getDemoTitle() {
        return "天气";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, WeatherActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
