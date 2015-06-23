package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

import cn.androidy.thinking.R;

/**
 * 仿IOS底部滚轮选择器
 * Created by Rick Meng on 2015/6/23.
 */
public class ScrollPicker extends View {

    private List<Object> mDataList;
    private DisplayMetrics dm;
    private PickerLayerManager plm;
    private Paint mPaint;
    private int mColorText = 0xff333333;
    private boolean isDoingAnimation = false;
    private float lastDownY;
    private int[] SHADOWS_COLORS = new int[]{0xFF111111,
            0x00AAAAAA, 0x00AAAAAA};
    // Center Line
    private Drawable centerDrawable;

    // Wheel drawables
    private int wheelBackground = R.drawable.wheel_bg;
    private int wheelForeground = R.drawable.wheel_val;

    // Shadows drawables
    private GradientDrawable topShadow;
    private GradientDrawable bottomShadow;

    public ScrollPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollPicker(Context context) {
        super(context);
        init();
    }

    public ScrollPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dm = getResources().getDisplayMetrics();
        plm = new PickerLayerManager(dm.density);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColorText);
        mPaint.setTextSize(20 * dm.density);
    }

    public void bindData(List<Object> dataList) {
        mDataList = dataList;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initResourcesIfNecessary();
        int h = getHeight();
        int w = getWidth();
        plm.initCanvasTotalArea(w, h);
        plm.bindData(mDataList);
        plm.initLayerParams(mPaint, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        plm.doDraw(canvas, mPaint);
        drawCenterRect(canvas);
        drawShadows(canvas);
    }

    /**
     * Set the shadow gradient color
     *
     * @param start
     * @param middle
     * @param end
     */
    public void setShadowColor(int start, int middle, int end) {
        SHADOWS_COLORS = new int[]{start, middle, end};
    }

    /**
     * Sets the drawable for the wheel background
     *
     * @param resource
     */
    public void setWheelBackground(int resource) {
        wheelBackground = resource;
        setBackgroundResource(wheelBackground);
    }

    /**
     * Sets the drawable for the wheel foreground
     *
     * @param resource
     */
    public void setWheelForeground(int resource) {
        wheelForeground = resource;
        centerDrawable = getContext().getResources().getDrawable(wheelForeground);
    }

    public boolean show() {
        //正在执行动画时不响应点击操作
        if (isDoingAnimation) {
            return false;
        }
        ViewHelper.setTranslationY(this, 480);
        ViewPropertyAnimator.animate(this).translationY(0).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isDoingAnimation = true;
                if (View.VISIBLE != getVisibility()) {
                    setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isDoingAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isDoingAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isDoingAnimation = true;
            }
        }).start();
        return true;
    }

    public boolean close() {
        //正在执行动画时不响应点击操作
        if (isDoingAnimation) {
            return false;
        }
        ViewPropertyAnimator.animate(this).translationY(480).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isDoingAnimation = true;
                if (View.VISIBLE != getVisibility()) {
                    setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isDoingAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isDoingAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isDoingAnimation = true;
            }
        }).start();
        return true;
    }

    /**
     * Initializes resources
     */
    private void initResourcesIfNecessary() {
        if (centerDrawable == null) {
            centerDrawable = getContext().getResources().getDrawable(wheelForeground);
        }

        if (topShadow == null) {
            topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        }

        if (bottomShadow == null) {
            bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        }

        setBackgroundResource(wheelBackground);
    }


    /**
     * Draws rect for current value
     *
     * @param canvas the canvas for drawing
     */
    private void drawCenterRect(Canvas canvas) {
        int center = getHeight() / 2;
        int offset = getItemHeight();
        centerDrawable.setBounds(0, center - offset, getWidth(), center + offset);
        centerDrawable.draw(canvas);
    }

    /**
     * Draws shadows on top and bottom of control
     *
     * @param canvas the canvas for drawing
     */
    private void drawShadows(Canvas canvas) {
        int height = getItemHeight();
        topShadow.setBounds(0, 0, getWidth(), height);
        topShadow.draw(canvas);

        bottomShadow.setBounds(0, getHeight() - height, getWidth(), getHeight());
        bottomShadow.draw(canvas);
    }

    private int getItemHeight() {
        return plm.getItemHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                dispatchActionDownEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                dispatchActionMoveEvent(event);
                return true;
            case MotionEvent.ACTION_UP:
                dispatchActionUpEvent(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void dispatchActionDownEvent(MotionEvent event) {
        lastDownY = event.getY();
    }

    float transY = 0;

    private void dispatchActionMoveEvent(MotionEvent event) {
        transY += (event.getY() - lastDownY);
        plm.initLayerParams(mPaint, transY);
        invalidate();
        lastDownY = event.getY();
    }

    private void dispatchActionUpEvent(MotionEvent event) {
        plm.fitCenterItemPosition();
    }
}
