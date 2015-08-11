package cn.androidy.viewstudy.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import cn.androidy.logger.core.Log;

/**
 * @author mwp 2015-8-10 18:18:20
 */
public class MyView extends View {

    private static final String LOG_TAG = "MyView";

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(LOG_TAG, "init start...");
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(LOG_TAG, "onAttachedToWindow start...");
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(LOG_TAG, "onDetachedFromWindow start...");
        super.onDetachedFromWindow();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(LOG_TAG, "onDraw start...");
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(LOG_TAG, "onMeasure start...");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(LOG_TAG, "layout start...\nl=" + l + ";t=" + t + ";r=" + r + ";b=" + b);
        super.layout(l, t, r, b);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(LOG_TAG, "onLayout start...\n" +
                "changed=" + changed + "; left=" + left + ";top=" + top + ";right=" + right + ";bottom=" + bottom);
        super.onLayout(changed, left, top, right, bottom);

    }
}
