package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.game.pintu.PintuActivity;

/**
 * Created by Rick Meng on 2015/6/19.
 */
public class PintuDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "拼图游戏";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, PintuActivity.class));
    }

    @Override
    public boolean isMember(DemoFamily demoFamily) {
        return demoFamily == DemoFamily.ALL || demoFamily == DemoFamily.ME;
    }
}
