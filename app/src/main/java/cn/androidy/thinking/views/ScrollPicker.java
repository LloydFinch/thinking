package cn.androidy.thinking.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;


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
    private float motionTransY;
    private float autoTransY;
    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;
    private static final int FLING_SPEED = 1000;

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
        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see android.view.VelocityTracker#obtain()
     * @see android.view.VelocityTracker#addMovement(MotionEvent)
     */

    private void acquireVelocityTracker(final MotionEvent event) {

        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

    }

    /**
     * 释放VelocityTracker
     *
     * @see android.view.VelocityTracker#clear()
     * @see android.view.VelocityTracker#recycle()
     */

    private void releaseVelocityTracker() {

        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }

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
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofInt(this, "translationY", 480, 0).setDuration(300);
        objectAnimator.addListener(new Animator.AnimatorListener() {
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
        });
        objectAnimator.start();
        return true;
    }

    public boolean close() {
        //正在执行动画时不响应点击操作
        if (isDoingAnimation) {
            return false;
        }
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofInt(this, "translationY", 0, 480).setDuration(300);
        objectAnimator.addListener(new Animator.AnimatorListener() {
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
        });
        objectAnimator.start();
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
        acquireVelocityTracker(event);
        final VelocityTracker verTracker = mVelocityTracker;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                dispatchActionDownEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                verTracker.computeCurrentVelocity(1000, mMaxVelocity);
                dispatchActionMoveEvent(event);
                return true;
            case MotionEvent.ACTION_UP:
                dispatchActionUpEvent(event);
                releaseVelocityTracker();
                return true;
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 记录当前速度
     *
     * @param velocityX x轴速度
     * @param velocityY y轴速度
     */
    private static final String sFormatStr = "velocityX=%f   velocityY=%f";

    private String recodeInfo(final float velocityX, final float velocityY) {
        final String info = String.format(sFormatStr, velocityX, velocityY);
        return info;
    }

    private void dispatchActionDownEvent(MotionEvent event) {
        lastDownY = event.getY();
    }

    private void dispatchActionMoveEvent(MotionEvent event) {
        if (autoTransY != 0) {
            motionTransY = autoTransY;
            autoTransY = 0;
        } else {
            motionTransY += (event.getY() - lastDownY);
        }
        plm.initLayerParams(mPaint, motionTransY);
        invalidate();
        lastDownY = event.getY();
    }

    private void dispatchActionUpEvent(MotionEvent event) {
        float yspeed = mVelocityTracker.getYVelocity();
        if (Math.abs(yspeed) > FLING_SPEED) {
            onFling(mVelocityTracker.getYVelocity());
        } else {
            fitSelection();
        }
    }

    private void fitSelection() {
        float targetTransY = -plm.getSelectedIndex() * plm.getItemHeight() * PickerLayer.ITEM_SCALE;
        ValueAnimator animator = ValueAnimator.ofFloat(motionTransY, targetTransY).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                autoTransY = (Float) animation.getAnimatedValue();
                plm.initLayerParams(mPaint, autoTransY);
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                motionTransY = autoTransY;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void onFling(float yVelocity) {
        float distance = Math.min(100 * dm.density, Math.abs(yVelocity * 0.3f));
        if (yVelocity < 0) {
            distance = -distance;
        }
        float targetTransY = motionTransY + distance;
        ValueAnimator animator = ValueAnimator.ofFloat(motionTransY, targetTransY).setDuration((long) (Math.abs(distance / yVelocity) * 1000));
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                autoTransY = (Float) animation.getAnimatedValue();
                plm.initLayerParams(mPaint, autoTransY);
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                motionTransY = autoTransY;
                fitSelection();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void selectIndex(int index) {
        float targetTransY = -index * plm.getItemHeight() * PickerLayer.ITEM_SCALE;
        ValueAnimator animator = ValueAnimator.ofFloat(motionTransY, targetTransY).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                autoTransY = (Float) animation.getAnimatedValue();
                plm.initLayerParams(mPaint, autoTransY);
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                motionTransY = autoTransY;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
