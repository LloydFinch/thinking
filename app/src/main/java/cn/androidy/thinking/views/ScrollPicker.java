package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

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
        int h = getHeight();
        int mh = getMeasuredHeight();
        int w = getWidth();
        plm.initCanvasTotalArea(w, h);
        plm.bindData(mDataList);
        plm.initLayerParams(mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        plm.doDraw(canvas, mPaint);
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
}
