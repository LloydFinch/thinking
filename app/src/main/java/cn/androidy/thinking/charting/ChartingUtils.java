package cn.androidy.thinking.charting;

import java.util.List;

import cn.androidy.thinking.charting.data.Entry;

/**
 * Created by Rick Meng on 2015/7/14.
 */
public class ChartingUtils {
    public static float getMaxVal(List<Entry> list) {
        Entry maxE = null;
        for (Entry e : list) {
            if (maxE == null) {
                maxE = e;
            } else if (maxE.getVal() < e.getVal()) {
                maxE = e;
            }
        }
        return maxE.getVal();
    }

    public static float getMinVal(List<Entry> list) {
        Entry minE = null;
        for (Entry e : list) {
            if (minE == null) {
                minE = e;
            } else if (e.getVal() < minE.getVal()) {
                minE = e;
            }
        }
        return minE.getVal();
    }
}
