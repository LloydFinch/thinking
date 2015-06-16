package cn.androidy.thinking.demos;

import android.content.Context;
import android.content.Intent;

import cn.androidy.thinking.LyricViewActivity;

/**
 * Created by Rick Meng on 2015/6/16.
 */
public class LyricDemo implements IDemoEntry {
    @Override
    public String getDemoTitle() {
        return "歌词";
    }

    @Override
    public void demonstrate(Context context) {
        context.startActivity(new Intent(context, LyricViewActivity.class));
    }
}
