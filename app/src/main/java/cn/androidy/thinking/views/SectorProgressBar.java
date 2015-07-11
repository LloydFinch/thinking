package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

/**
 * 扇形进度条
 * Created by Rick Meng on 2015/7/11.
 */
public class SectorProgressBar extends View {
    /**
     * 圆的直径
     */
    private int mRadius;
    /**
     * 圆的中心位置
     */
    private int mCenter;
    /**
     * 绘制进度提示文本
     */
    private Paint mTextPaint;
    /**
     * 绘制扇形区域的画笔
     */
    private Paint mArcPaint;
    /**
     * 整个扇形所在的圆的矩形范围
     */
    private RectF mRange = null;
    private int mMax = 100;
    private int mProgress;
    private SectorStyle sectorStyle = SectorStyle.WEDGE;
    private int defaultColor = 0xff000000;
    private int progressColor = 0xff4CAF50;
    private DisplayMetrics dm;
    private Rect mTextBound = new Rect();

    public static enum SectorStyle {
        WEDGE,//楔形
        RING//圆环
    }

    public SectorProgressBar(Context context) {
        this(context, null);
    }

    public SectorProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化绘制圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(progressColor);
        mTextPaint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
        dm = getResources().getDisplayMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // 获取圆形的直径
        mRadius = width - getPaddingLeft() - getPaddingRight();
        // 中心点
        mCenter = width / 2;
        setMeasuredDimension(width, width);
        if (mRange == null) {
            // 圆弧的绘制范围
            mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mRadius
                    + getPaddingLeft(), mRadius + getPaddingLeft());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (sectorStyle == SectorStyle.WEDGE) {
            mArcPaint.setColor(progressColor);
            mArcPaint.setStyle(Paint.Style.FILL); // 设置空心
            float endEdge = 360 * mProgress * 1.0f / mMax;
            canvas.drawArc(mRange, -90, endEdge, true,
                    mArcPaint);
        } else if (sectorStyle == SectorStyle.RING) {
            mArcPaint.setColor(defaultColor); // 设置圆环的颜色
            mArcPaint.setStrokeWidth(3 * dm.density); // 设置圆环的宽度
            mArcPaint.setStyle(Paint.Style.STROKE); // 设置空心
            canvas.drawCircle(mCenter, mCenter, mRadius / 2, mArcPaint); // 画出圆环
            mArcPaint.setColor(progressColor);
            float endEdge = 360 * mProgress * 1.0f / mMax;
            canvas.drawArc(mRange, -90, endEdge, false, mArcPaint); // 根据进度画圆弧
            String text = "123";
            float w = measureText(text.toString(), mTextPaint);//必须先测量让mTextBound获取值。
            int startX = (int) (mCenter - w / 2);//文字居中
            Paint.FontMetricsInt fmi = mTextPaint.getFontMetricsInt();
            float baseline = (float) (mCenter + (fmi.bottom / 2 + fmi.top / 2));
            canvas.drawText(text, startX, baseline, mTextPaint);
        }
    }

    private float measureText(String text, Paint paint) {
        float w = paint.measureText(text);
        mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
        return w;
    }

    public synchronized void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }

    public void setMax(int max) {
        mMax = max;
    }

    public void setSectorStyle(SectorStyle sectorStyle) {
        this.sectorStyle = sectorStyle;
    }

    public SectorStyle getSectorStyle() {
        return sectorStyle;
    }
}
