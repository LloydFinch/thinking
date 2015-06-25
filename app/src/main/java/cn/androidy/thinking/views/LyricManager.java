package cn.androidy.thinking.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick Meng on 2015/6/18.
 */
public class LyricManager {
    private List<Lyric> mLyricList;
    private Rect mTextBound = new Rect();
    private DisplayMetrics dm;
    private Lyric mCurrentLyric;
    private float currentStartY;
    private int mWidth;
    private int mHeight;
    private int mNormalColor;
    private int mChangeColor;
    private int mMaxTextWidth;
    private Paint mPaint;

    public LyricManager(List<String> textList, DisplayMetrics displayMetrics) {
        mLyricList = new ArrayList<Lyric>();
        dm = displayMetrics;
        if (textList != null && !textList.isEmpty()) {
            for (String s : textList) {
                mLyricList.add(new Lyric(s));
            }
        }

    }

    public void confirmLyricState(int width, int height, int normalColor, int changeColor, int maxTextWidth, Paint paint) {
        if (mLyricList != null && !mLyricList.isEmpty()) {
            measureText(mLyricList.get(0).text, paint);
            currentStartY = height / 2 - mTextBound.height() / 2;
            confirmLyricState(width, height, normalColor, changeColor, maxTextWidth, paint, currentStartY);
        }
    }

    public void confirmLyricState(int width, int height, int normalColor, int changeColor, int maxTextWidth, Paint paint, float startY) {
        mWidth = width;
        mHeight = height;
        mNormalColor = normalColor;
        mChangeColor = changeColor;
        mMaxTextWidth = maxTextWidth;
        mPaint = paint;
        currentStartY = startY;
        if (mLyricList != null && !mLyricList.isEmpty()) {
            //将正在渲染的歌词居中处理。
            measureText(mLyricList.get(0).text, paint);
            float startProgress = 0;
            float tempStartY = currentStartY;
            for (Lyric lyric : mLyricList) {
                String text = lyric.text;
                measureText(text, paint);
                int textWidth = Math.min(maxTextWidth, measureText(text, paint));
                lyric.textWidth = textWidth;
                lyric.startY = tempStartY += (mTextBound.height() * 1.5f);
                lyric.startX = width / 2 - textWidth / 2;
                lyric.color = normalColor;
                lyric.colorChange = changeColor;
                lyric.height = height;
                lyric.startProgress = startProgress;
                startProgress += lyric.calculateProgressArea(getTotalWidth());
            }
        }
    }

    private int measureText(String text, Paint paint) {
        int w = (int) paint.measureText(text);
        mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
        return w;
    }


    public void dispatchDraw(Canvas canvas, Paint paint, float progress) {
        if (mLyricList.isEmpty()) {
            return;
        }

        for (Lyric lyric : mLyricList) {
            boolean isCurrent = lyric.onDraw(canvas, paint, progress);
            if (isCurrent) {
                //如果换了一句歌词
                if (mCurrentLyric != lyric) {
                    if(mCurrentLyric != null){
                        nextLyric();
                    }
                    mCurrentLyric = lyric;
                }
            }
        }
    }

    //调整歌词整体上移一句
    private void nextLyric() {
        float targetStartY = currentStartY - (mTextBound.height() * 1.5f);
        ValueAnimator animator = ValueAnimator.ofFloat(currentStartY, targetStartY).setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                confirmLyricState(mWidth, mHeight, mNormalColor, mChangeColor, mMaxTextWidth, mPaint, currentStartY);
                currentStartY = (float) ((ValueAnimator) animation).getAnimatedValue();
            }
        });
        animator.start();
    }


    private int getTotalWidth() {
        if (mLyricList.isEmpty()) {
            return 0;
        }
        int result = 0;
        for (Lyric lyric : mLyricList) {
            result += lyric.textWidth;
        }
        return result;
    }
}
