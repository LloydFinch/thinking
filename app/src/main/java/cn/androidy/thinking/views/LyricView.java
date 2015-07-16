package cn.androidy.thinking.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

import cn.androidy.thinking.R;

/**
 * Created by Rick Meng on 2015/6/18.
 */
public class LyricView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    private Paint mPaint;
    private int mTextSize = sp2px(30);
    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;
    private int mMaxTextWidth;
    private float mProgress;
    private LyricManager lyricManager;
    private ValueAnimator animator;
    private boolean isPlaying = false;

    public LyricView(Context context) {
        super(context, null);
        init(context, null);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.Lyric);
            mTextSize = ta.getDimensionPixelSize(
                    R.styleable.Lyric_text_size, mTextSize);
            mTextOriginColor = ta.getColor(
                    R.styleable.Lyric_text_origin_color,
                    mTextOriginColor);
            mTextChangeColor = ta.getColor(
                    R.styleable.Lyric_text_change_color,
                    mTextChangeColor);
            mProgress = ta.getFloat(R.styleable.Lyric_progress, 0);
            ta.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);

        List<String> list = new ArrayList<String>();
        list.add("风吹雨成花");
        list.add("时间追不上白马");
        list.add("你年少掌心的梦话");
        list.add("依然紧握着吗");
        list.add("云翻涌成夏");
        list.add(" 眼泪被岁月蒸发");
        list.add("这条路上的你我她");
        list.add(" 有谁迷路了吗");
        lyricManager = new LyricManager(list, getResources().getDisplayMetrics());
    }

    public void startOrPause() {
        if (isPlaying) {
            animator.cancel();
            isPlaying = false;
        } else {
            animator = ValueAnimator.ofFloat(mProgress, 1).setDuration((long) (10 * 1000 * (1 - mProgress)));
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(this);
            animator.addListener(this);
            animator.start();
        }
    }

    //测试文字剪切
    private String testClipRectText = "这段文字会被切成两半";
    private int mTestTextStartX;
    private int mTestTextWidth;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        mMaxTextWidth = width - getPaddingLeft() - getPaddingRight();
        mTestTextWidth = Math.min(mMaxTextWidth, measureText(testClipRectText, mPaint));
        mTestTextStartX = mMaxTextWidth / 2 - mTestTextWidth / 2;
        lyricManager.confirmLyricState(getMeasuredWidth(), getMeasuredHeight(), mTextOriginColor, mTextChangeColor, mMaxTextWidth, mPaint);
    }

    private int measureText(String text, Paint paint) {
        int w = (int) paint.measureText(text);
        return w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        lyricManager.dispatchDraw(canvas, mPaint, mProgress);

        mPaint.setColor(mTextOriginColor);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        int divPosition = mTestTextStartX + mTestTextWidth / 3;
        canvas.clipRect(mTestTextStartX, 0, divPosition, getMeasuredHeight());
        canvas.drawText(testClipRectText, mTestTextStartX, 120, mPaint);
        canvas.restore();

        mPaint.setColor(mTextChangeColor);
        canvas.clipRect(divPosition, 0, mTestTextStartX + mTestTextWidth, getMeasuredHeight());
        canvas.drawText(testClipRectText, mTestTextStartX, 240, mPaint);
        canvas.restore();
    }

    private int sp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getResources().getDisplayMetrics());
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        LyricView.this.mProgress = (float) animation.getAnimatedValue();
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        isPlaying = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isPlaying = false;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        isPlaying = false;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        isPlaying = true;
    }
}
