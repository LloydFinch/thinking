package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.androidy.thinking.charting.ChartingUtils;
import cn.androidy.thinking.charting.data.Entry;
import cn.androidy.thinking.charting.data.EntryScreen;

public class TemperatureCurveView extends View {
    List<Entry> entries = new ArrayList<>();
    private Paint limitLinePaint;
    /**
     * main paint object used for rendering
     */
    protected Paint mRenderPaint;
    protected Paint textPaint;
    protected Path cubicPath = new Path();
    protected Path cubicFillPath = new Path();
    protected int xIndexWidth;
    protected DisplayMetrics dm;
    protected float mRange;
    protected float maxVal;
    protected float minVal;

    public TemperatureCurveView(Context context) {
        this(context, null);
    }

    public TemperatureCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dm = getResources().getDisplayMetrics();
        mRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRenderPaint.setStyle(Paint.Style.FILL);
        mRenderPaint.setStrokeWidth(3);

        limitLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        limitLinePaint.setStyle(Paint.Style.FILL);
        limitLinePaint.setColor(Color.BLUE);
        limitLinePaint.setStrokeWidth(3);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(12 * dm.density);

        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            entries.add(new Entry(10 + random.nextInt(9) * 0.1f, i));
        }
        maxVal = ChartingUtils.getMaxVal(entries);
        minVal = ChartingUtils.getMinVal(entries);
        mRange = maxVal - minVal;
        xIndexWidth = dm.widthPixels / (entries.size());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setEntryScreen(new EntryScreen(maxVal, minVal, getTop(), getBottom()));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int left = getLeft();
        int right = getRight();
        int h = getHeight();
        canvas.drawLine(left, 2, right, 2, limitLinePaint);
        canvas.drawText("top", 0, 30, textPaint);
        canvas.drawLine(left, h / 2, right, h / 2, limitLinePaint);
        canvas.drawText("center", left, h / 2, textPaint);
        canvas.drawLine(left, h - 2, right, h - 2, limitLinePaint);
        canvas.drawText("bottom", left, h, textPaint);
        int minx = 0;
        int maxx = entries.size();

        int phaseX = 1;
        int phaseY = 1;

        float intensity = 0.2f;

        cubicPath.reset();

        int size = (int) Math.ceil((maxx - minx) * phaseX + minx);

        if (size - minx >= 2) {

            float prevDx = 0f;
            float prevDy = 0f;
            float curDx = 0f;
            float curDy = 0f;

            Entry prevPrev = entries.get(minx);
            Entry prev = entries.get(minx);
            Entry cur = entries.get(minx);
            Entry next = entries.get(minx + 1);

            // let the spline start
            cubicPath.moveTo(cur.getXIndex(), cur.findYCoordinate() * phaseY);

            prevDx = (cur.getXIndex() - prev.getXIndex()) * intensity;
            prevDy = (cur.findYCoordinate() - prev.findYCoordinate()) * intensity;

            curDx = (next.getXIndex() - cur.getXIndex()) * intensity;
            curDy = (next.findYCoordinate() - cur.findYCoordinate()) * intensity;

            // the first cubic
            cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.findYCoordinate() + prevDy) * phaseY,
                    xIndexWidth * (cur.getXIndex() - curDx),
                    (-curDy) * phaseY, cur.getXIndex() * xIndexWidth, cur.findYCoordinate() * phaseY);
            canvas.drawText(String.valueOf(cur.getVal()), cur.getXIndex() * xIndexWidth, cur.findYCoordinate() * phaseY, textPaint);
            for (int j = minx + 1, count = Math.min(size, entries.size() - 1); j < count; j++) {

                prevPrev = entries.get(j == 1 ? 0 : j - 2);
                prev = entries.get(j - 1);
                cur = entries.get(j);
                next = entries.get(j + 1);

                prevDx = (cur.getXIndex() - prevPrev.getXIndex()) * intensity;
                prevDy = (cur.findYCoordinate() - prevPrev.findYCoordinate()) * intensity;
                curDx = (next.getXIndex() - prev.getXIndex()) * intensity;
                curDy = (next.findYCoordinate() - prev.findYCoordinate()) * intensity;

                cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.findYCoordinate() + prevDy) * phaseY,
                        xIndexWidth * (cur.getXIndex() - curDx),
                        (cur.findYCoordinate() - curDy) * phaseY, cur.getXIndex() * xIndexWidth, cur.findYCoordinate() * phaseY);
                canvas.drawText(String.valueOf(cur.getVal()), cur.getXIndex() * xIndexWidth, cur.findYCoordinate() * phaseY, textPaint);
            }

            if (size > entries.size() - 1) {

                prevPrev = entries.get((entries.size() >= 3) ? entries.size() - 3
                        : entries.size() - 2);
                prev = entries.get(entries.size() - 2);
                cur = entries.get(entries.size() - 1);
                next = cur;

                prevDx = (cur.getXIndex() - prevPrev.getXIndex()) * intensity;
                prevDy = (cur.findYCoordinate() - prevPrev.findYCoordinate()) * intensity;
                curDx = (next.getXIndex() - prev.getXIndex()) * intensity;
                curDy = (next.findYCoordinate() - prev.findYCoordinate()) * intensity;

                // the last cubic
                cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.findYCoordinate() + prevDy) * phaseY,
                        xIndexWidth * (cur.getXIndex() - curDx),
                        (cur.findYCoordinate() - curDy) * phaseY, cur.getXIndex() * xIndexWidth, cur.findYCoordinate() * phaseY);
                canvas.drawText(String.valueOf(cur.getVal()), cur.getXIndex() * xIndexWidth, cur.findYCoordinate() * phaseY, textPaint);
            }
        }


        cubicFillPath.reset();
        cubicFillPath.addPath(cubicPath);
        // create a new path, this is bad for performance
        drawCubicFill(canvas, cubicFillPath, 0, size);

        mRenderPaint.setColor(0xffed145b);

        mRenderPaint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(cubicPath, mRenderPaint);

        mRenderPaint.setPathEffect(null);
    }

    protected void drawCubicFill(Canvas canvas, Path spline,
                                 int from, int to) {
        spline.lineTo((to - 1) * xIndexWidth, getHeight() / 2);
        spline.lineTo(from * xIndexWidth, getHeight() / 2);
        spline.close();

        mRenderPaint.setStyle(Paint.Style.FILL);

        mRenderPaint.setColor(Color.BLUE);
        // filled is drawn with less alpha
        mRenderPaint.setAlpha(80);

        canvas.drawPath(spline, mRenderPaint);

        mRenderPaint.setAlpha(255);
    }
}
