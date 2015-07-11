package cn.androidy.thinking.demos;

import android.content.Context;

/**
 * Created by Rick Meng on 2015/6/16.
 */
public interface IDemoEntry {

    //demo标题
    public String getDemoTitle();

    //演示demo
    public void demonstrate(Context context);

    //划分属于哪一类demo
    public boolean isMember(DemoFamily demoFamily);

    public static enum DemoFamily {
        SQUARE("Square开源"), ALL("全部Demo"), RENYUGANG("任玉刚"), ME("原创Demo"), ZHY("张洪洋");
        private String name;

        private DemoFamily(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static DemoFamily getDemoFamilyFromKeyword(String keyword) {
            if (SQUARE.getName().equals(keyword)) {
                return SQUARE;
            } else if (RENYUGANG.getName().equals(keyword)) {
                return RENYUGANG;
            } else if (ZHY.getName().equals(keyword)) {
                return ZHY;
            } else if (ME.getName().equals(keyword)) {
                return ME;
            } else {
                return ALL;
            }
        }
    }
}
