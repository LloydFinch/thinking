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
        Log.d(LOG_TAG, "init");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(LOG_TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(LOG_TAG, "onDetachedFromWindow");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(LOG_TAG, "onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(LOG_TAG, "onMeasure");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.d(LOG_TAG, "layout l=" + l + ";t=" + t + ";r=" + r + "b=" + b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(LOG_TAG, "onLayout changed=" + changed + "; left=" + left + ";top=" + top + ";right=" + right + "bottom=" + bottom);
    }
}
