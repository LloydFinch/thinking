package cn.androidy.thinking.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rick Meng on 2015/6/17.
 */
public class DemoListBuilder {
    private static final IDemoEntry DEMO_ARRAY[] = new IDemoEntry[]{new ThreadPoolDemo(), new LyricDemo(),
            new SquareTimesDemo(), new RippleDemo(), new BannerDemo(), new DrawableDemo(), new PintuDemo(),
            new ScrollPickerDemo(), new MojiGuidepageDemo(), new CurveDemo(), new LuckyPanDemo(), new SectorProgressBarDemo(),
            new ProgressCylinderDemo(),new WaveViewDemo()};

    public static List<IDemoEntry> buildDemoList(IDemoEntry.DemoFamily demoFamily) {
        if (demoFamily == null || demoFamily == IDemoEntry.DemoFamily.ALL) {
            return Arrays.asList(DEMO_ARRAY);
        }
        ArrayList<IDemoEntry> result = new ArrayList<IDemoEntry>();
        int length = DEMO_ARRAY.length;
        for (int i = 0; i < length; i++) {
            if (DEMO_ARRAY[i].isMember(demoFamily)) {
                result.add(DEMO_ARRAY[i]);
            }
        }
        return result;
    }

    public static List<IDemoEntry> getDemoEntryList() {
        ArrayList<IDemoEntry> result = new ArrayList<IDemoEntry>();
        result.add(new AllDemo(IDemoEntry.DemoFamily.ALL));
        result.add(new AllDemo(IDemoEntry.DemoFamily.ME));
        result.add(new AllDemo(IDemoEntry.DemoFamily.SQUARE));
        result.add(new AllDemo(IDemoEntry.DemoFamily.RENYUGANG));
        result.add(new AllDemo(IDemoEntry.DemoFamily.ZHY));
        result.add(new AllDemo(IDemoEntry.DemoFamily.GITHUB));
        return result;
    }
}
