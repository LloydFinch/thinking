package cn.androidy.thinking.demos;

import cn.androidy.thinking.ColorPhraseActivity;
import cn.androidy.thinking.WaveViewActivity;

/**
 * Created by Rick Meng on 2015/7/16.
 */
public class ColorPhraseDemo extends ThirdParthDemo {
    @Override
    public String getDemoTitle() {
        return "字符串格式化";
    }

    @Override
    public boolean isMember(IDemoEntry.DemoFamily demoFamily) {
        return demoFamily == IDemoEntry.DemoFamily.ALL || demoFamily == IDemoEntry.DemoFamily.GITHUB;
    }

    @Override
    public String getOriginalUrl() {
        return "https://github.com/THEONE10211024/ColorPhrase";
    }

    @Override
    public Class<?> getTargetActivityClass() {
        return ColorPhraseActivity.class;
    }
}
