package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.androidy.thinking.charting.data.Entry;

public class TemperatureCurveView extends View {
    List<Entry> entries = new ArrayList<>();
    private Paint paint;
    /**
     * main paint object used for rendering
     */
    protected Paint mRenderPaint;
    protected Path cubicPath = new Path();
    protected Path cubicFillPath = new Path();
    protected int xIndexWidth;

    public TemperatureCurveView(Context context) {
        this(context, null);
    }

    public TemperatureCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRenderPaint.setStyle(Paint.Style.FILL);
        mRenderPaint.setStrokeWidth(3);
        Random random = new Random();
        int baseY = getResources().getDisplayMetrics().heightPixels / 2;
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(baseY + 10 * random.nextInt(10), i));
        }
        xIndexWidth = getResources().getDisplayMetrics().widthPixels / entries.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int minx = 0;
        int maxx = entries.size();

        float phaseX = 1;
        float phaseY = 1;

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
            cubicPath.moveTo(cur.getXIndex(), cur.getVal() * phaseY);

            prevDx = (cur.getXIndex() - prev.getXIndex()) * intensity;
            prevDy = (cur.getVal() - prev.getVal()) * intensity;

            curDx = (next.getXIndex() - cur.getXIndex()) * intensity;
            curDy = (next.getVal() - cur.getVal()) * intensity;

            // the first cubic
            cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.getVal() + prevDy) * phaseY,
                    xIndexWidth * (cur.getXIndex() - curDx),
                    (cur.getVal() - curDy) * phaseY, cur.getXIndex() * xIndexWidth, cur.getVal() * phaseY);

            for (int j = minx + 1, count = Math.min(size, entries.size() - 1); j < count; j++) {

                prevPrev = entries.get(j == 1 ? 0 : j - 2);
                prev = entries.get(j - 1);
                cur = entries.get(j);
                next = entries.get(j + 1);

                prevDx = (cur.getXIndex() - prevPrev.getXIndex()) * intensity;
                prevDy = (cur.getVal() - prevPrev.getVal()) * intensity;
                curDx = (next.getXIndex() - prev.getXIndex()) * intensity;
                curDy = (next.getVal() - prev.getVal()) * intensity;

                cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.getVal() + prevDy) * phaseY,
                        xIndexWidth * (cur.getXIndex() - curDx),
                        (cur.getVal() - curDy) * phaseY, cur.getXIndex() * xIndexWidth, cur.getVal() * phaseY);
            }

            if (size > entries.size() - 1) {

                prevPrev = entries.get((entries.size() >= 3) ? entries.size() - 3
                        : entries.size() - 2);
                prev = entries.get(entries.size() - 2);
                cur = entries.get(entries.size() - 1);
                next = cur;

                prevDx = (cur.getXIndex() - prevPrev.getXIndex()) * intensity;
                prevDy = (cur.getVal() - prevPrev.getVal()) * intensity;
                curDx = (next.getXIndex() - prev.getXIndex()) * intensity;
                curDy = (next.getVal() - prev.getVal()) * intensity;

                // the last cubic
                cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.getVal() + prevDy) * phaseY,
                        xIndexWidth * (cur.getXIndex() - curDx),
                        (cur.getVal() - curDy) * phaseY, cur.getXIndex() * xIndexWidth, cur.getVal() * phaseY);
            }
        }
        mRenderPaint.setColor(0xffed145b);

        mRenderPaint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(cubicPath, mRenderPaint);

        mRenderPaint.setPathEffect(null);
    }
}
