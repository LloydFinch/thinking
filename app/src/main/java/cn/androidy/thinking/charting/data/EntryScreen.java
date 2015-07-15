package cn.androidy.thinking.charting.data;

/**
 * Created by Rick Meng on 2015/7/15.
 */
public class EntryScreen {
    protected float maxVal;
    protected float minVal;
    protected float top;
    protected float bottom;

    public EntryScreen(float maxVal, float minVal, float top, float bottom) {
        this.maxVal = maxVal;
        this.minVal = minVal;
        this.top = top;
        this.bottom = bottom;
    }

    public float getBottom() {
        return bottom;
    }

    public float getMaxVal() {
        return maxVal;
    }

    public float getMinVal() {
        return minVal;
    }

    public float getTop() {
        return top;
    }
}
