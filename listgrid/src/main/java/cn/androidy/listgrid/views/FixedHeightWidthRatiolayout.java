package cn.androidy.listgrid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import cn.androidy.listgrid.R;

/**
 * 固定宽高比的RelativeLayout
 *
 * @author mwp Created on 2015年7月17日 上午9:50:34
 */
public class FixedHeightWidthRatiolayout extends RelativeLayout {

    protected DisplayMetrics dm;
    protected float mHeightWidthRatio;// 高：宽
    protected int mWidthInPx;// 改视图在当前屏幕的实际宽度
    protected FixedHeightWidthRatioView occupyingView;// 占位view

    public FixedHeightWidthRatiolayout(Context context, float heightWidthRatio, int widthInPx) {
        super(context);
        dm = getResources().getDisplayMetrics();
        mWidthInPx = widthInPx;
        mHeightWidthRatio = heightWidthRatio;
        init();
    }

    public FixedHeightWidthRatiolayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        float mHeightBaseValue = 0;// 高：宽 中高的基数
        float mWidthBaseValue = 0;// 高：宽 中宽的基数
        float mWidthIn720P = 0;// 在宽为720px的屏幕中该视图的宽度
        dm = getResources().getDisplayMetrics();
        if (attrs != null) {
            // 获得自定义属性
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioView);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int itemId = ta.getIndex(i); // 获取某个属性的Id值
                if (itemId == R.styleable.RatioView_height_base_value) {
                    mHeightBaseValue = ta.getFloat(itemId, 0);
                } else if (itemId == R.styleable.RatioView_width_base_value) {
                    mWidthBaseValue = ta.getFloat(itemId, 0);
                } else if (itemId == R.styleable.RatioView_screen_width_base_value) {
                    mWidthIn720P = ta.getFloat(itemId, 0);
                }
            }
            if (mWidthBaseValue != 0) {
                mHeightWidthRatio = mHeightBaseValue / mWidthBaseValue;
            }
            if (mWidthIn720P == 0) {
                mWidthInPx = (int) (mWidthIn720P / 720 * dm.widthPixels);
            } else {
                mWidthInPx = dm.widthPixels;
            }
            ta.recycle();
        }
        init();
    }

    protected void init() {
        addOccupyingView();
    }

    protected int getOccupyingViewId() {
        return occupyingView.getId();
    }

    /**
     * 通知layout整体高度发生了变化
     */
    protected void notifyHeightChanged() {
        occupyingView.setmHeightWidthRatio(mHeightWidthRatio);
        invalidate();
    }

    private void addOccupyingView() {
        occupyingView = new FixedHeightWidthRatioView(getContext(), mHeightWidthRatio, mWidthInPx);
        occupyingView.setId(R.id.id_occupy_view);
        LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p.addRule(CENTER_HORIZONTAL);
        addView(occupyingView, p);
    }

    /**
     * 按照百分比位置在该布局中添加各种子View
     *
     * @param v           被添加的子View
     * @param leftPercent 左边距/总宽度
     * @param topPercent  上边距/高度
     * @param wRatio      子View宽度/总宽度
     * @param hRatio      子View高度/总高度
     * @author mwp 2015-7-17 14:40:40
     */
    protected void addView(View v, float leftPercent, float topPercent, float wRatio, float hRatio) {
        LayoutParams p = new LayoutParams((int) (wRatio * mWidthInPx), (int) (hRatio * mHeightWidthRatio * mWidthInPx));
        p.addRule(ALIGN_TOP, getOccupyingViewId());
        p.addRule(ALIGN_LEFT, getOccupyingViewId());
        int leftMargin = (int) (leftPercent * mWidthInPx);
        int topMargin = (int) (topPercent * mHeightWidthRatio * mWidthInPx);
        p.setMargins(leftMargin, topMargin, 0, 0);
        addView(v, p);
    }

    protected void addHorizontalLine(int resId, float leftPercent, float topPercent, int height, float wRatio) {
        View v = new View(getContext());
        v.setBackgroundResource(resId);
        LayoutParams p = new LayoutParams((int) (wRatio * mWidthInPx), height);
        p.addRule(ALIGN_TOP, getOccupyingViewId());
        p.addRule(ALIGN_LEFT, getOccupyingViewId());
        int leftMargin = (int) (leftPercent * mWidthInPx);
        int topMargin = (int) (topPercent * mHeightWidthRatio * mWidthInPx);
        p.setMargins(leftMargin, topMargin, 0, 0);
        addView(v, p);
    }

    protected void addVerticalLine(int resId, float leftPercent, float topPercent, int width, float hRatio) {
        View v = new View(getContext());
        v.setBackgroundResource(resId);
        LayoutParams p = new LayoutParams(width, (int) (hRatio * mHeightWidthRatio * mWidthInPx));
        p.addRule(ALIGN_TOP, getOccupyingViewId());
        p.addRule(ALIGN_LEFT, getOccupyingViewId());
        int leftMargin = (int) (leftPercent * mWidthInPx);
        int topMargin = (int) (topPercent * mHeightWidthRatio * mWidthInPx);
        p.setMargins(leftMargin, topMargin, 0, 0);
        addView(v, p);
    }
}
