package cn.androidy.thinking.demos;

import cn.androidy.thinking.WaveViewActivity;

/**
 * Created by Rick Meng on 2015/7/16.
 */
public class WaveViewDemo extends ThirdParthDemo {
    @Override
    public String getDemoTitle() {
        return "水波纹进度条";
    }

    @Override
    public boolean isMember(IDemoEntry.DemoFamily demoFamily) {
        return demoFamily == IDemoEntry.DemoFamily.ALL || demoFamily == DemoFamily.GITHUB;
    }

    @Override
    public String getOriginalUrl() {
        return "https://github.com/john990/WaveView";
    }

    @Override
    public Class<?> getTargetActivityClass() {
        return WaveViewActivity.class;
    }
}
