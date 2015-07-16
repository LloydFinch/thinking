package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.androidy.thinking.charting.ChartingUtils;
import cn.androidy.thinking.charting.data.Entry;
import cn.androidy.thinking.charting.data.EntryScreen;

public class ProgressCylinderView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    List<Entry> entries = new ArrayList<>();
    /**
     * main paint object used for rendering
     */
    protected Paint mRenderPaint;
    protected Path cubicPath = new Path();
    protected Path cubicFillPath = new Path();
    protected int xIndexWidth;
    protected DisplayMetrics dm;
    protected float mRange;
    protected float maxVal;
    protected float minVal;
    float scale = 1;
    int j = 0;
    /**
     * 与SurfaceHolder绑定的Canvas
     */
    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    Random random = new Random();
    /**
     * 线程的控制开关
     */
    private boolean isRunning;
    private int progress;
    Thread t;

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }


    public ProgressCylinderView(Context context) {
        this(context, null);
    }

    public ProgressCylinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dm = getResources().getDisplayMetrics();
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setEntryScreen(new EntryScreen(maxVal, minVal, getTop(), getBottom()));
        }
        scale = 10 / mRange;
    }

    private float getPhaseY() {
        return progress * 1.0f / 100;
    }

    private void draw() {
        // 获得canvas
        mCanvas = mHolder.lockCanvas();
        if (mCanvas != null) {
            mCanvas.drawColor(Color.WHITE);
            int minx = 0;
            int maxx = entries.size();
            float intensity = 0.2f;
            float phaseY = getPhaseY();
            cubicPath.reset();
            float h = getHeight();
            int size = (int) Math.ceil((maxx - minx) + minx);

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
                cubicPath.moveTo(cur.getXIndex(), cur.findYCoordinate(phaseY, scale, -h / 2));

                prevDx = (cur.getXIndex() - prev.getXIndex()) * intensity;
                prevDy = (cur.findYCoordinate(phaseY, scale, -h / 2) - prev.findYCoordinate(phaseY, scale, -h / 2)) * intensity;

                curDx = (next.getXIndex() - cur.getXIndex()) * intensity;
                curDy = (next.findYCoordinate(phaseY, scale, -h / 2) - cur.findYCoordinate(phaseY, scale, -h / 2)) * intensity;

                // the first cubic
                cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.findYCoordinate(phaseY, scale, -h / 2) + prevDy),
                        xIndexWidth * (cur.getXIndex() - curDx),
                        (-curDy), cur.getXIndex() * xIndexWidth, cur.findYCoordinate(phaseY, scale, -h / 2));
                for (int j = minx + 1, count = Math.min(size, entries.size() - 1); j < count; j++) {

                    prevPrev = entries.get(j == 1 ? 0 : j - 2);
                    prev = entries.get(j - 1);
                    cur = entries.get(j);
                    next = entries.get(j + 1);

                    prevDx = (cur.getXIndex() - prevPrev.getXIndex()) * intensity;
                    prevDy = (cur.findYCoordinate(phaseY, scale, -h / 2) - prevPrev.findYCoordinate(phaseY, scale, -h / 2)) * intensity;
                    curDx = (next.getXIndex() - prev.getXIndex()) * intensity;
                    curDy = (next.findYCoordinate(phaseY, scale, -h / 2) - prev.findYCoordinate(phaseY, scale, -h / 2)) * intensity;

                    cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.findYCoordinate(phaseY, scale, -h / 2) + prevDy),
                            xIndexWidth * (cur.getXIndex() - curDx),
                            (cur.findYCoordinate(phaseY, scale, -h / 2) - curDy), cur.getXIndex() * xIndexWidth, cur.findYCoordinate(phaseY, scale, -h / 2));
                }

                if (size > entries.size() - 1) {

                    prevPrev = entries.get((entries.size() >= 3) ? entries.size() - 3
                            : entries.size() - 2);
                    prev = entries.get(entries.size() - 2);
                    cur = entries.get(entries.size() - 1);
                    next = cur;

                    prevDx = (cur.getXIndex() - prevPrev.getXIndex()) * intensity;
                    prevDy = (cur.findYCoordinate(phaseY, scale, -h / 2) - prevPrev.findYCoordinate(phaseY, scale, -h / 2)) * intensity;
                    curDx = (next.getXIndex() - prev.getXIndex()) * intensity;
                    curDy = (next.findYCoordinate(phaseY, scale, -h / 2) - prev.findYCoordinate(phaseY, scale, -h / 2)) * intensity;

                    // the last cubic
                    cubicPath.cubicTo(xIndexWidth * (prev.getXIndex() + prevDx), (prev.findYCoordinate(phaseY, scale, -h / 2) + prevDy),
                            xIndexWidth * (cur.getXIndex() - curDx),
                            (cur.findYCoordinate(phaseY, scale, -h / 2) - curDy), cur.getXIndex() * xIndexWidth, cur.findYCoordinate(phaseY, scale, -h / 2));
                }
            }


            cubicFillPath.reset();
            cubicFillPath.addPath(cubicPath);
            // create a new path, this is bad for performance
            drawCubicFill(mCanvas, cubicFillPath, 0, size);

            mRenderPaint.setColor(0xffed145b);

            mRenderPaint.setStyle(Paint.Style.STROKE);

            mCanvas.drawPath(cubicPath, mRenderPaint);

            mRenderPaint.setPathEffect(null);

            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    protected void drawCubicFill(Canvas canvas, Path spline,
                                 int from, int to) {
        spline.lineTo((to - 1) * xIndexWidth, getHeight());
        spline.lineTo(from * xIndexWidth, getHeight());
        spline.close();

        mRenderPaint.setStyle(Paint.Style.FILL);

        mRenderPaint.setColor(Color.BLUE);
        // filled is drawn with less alpha
        mRenderPaint.setAlpha(80);

        canvas.drawPath(spline, mRenderPaint);

        mRenderPaint.setAlpha(255);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRenderPaint.setStyle(Paint.Style.FILL);
        mRenderPaint.setStrokeWidth(3);

        for (int i = 0; i < 5; i++) {
            entries.add(new Entry(1 + random.nextInt(9) * 0.1f, i));
        }
        maxVal = ChartingUtils.getMaxVal(entries);
        minVal = ChartingUtils.getMinVal(entries);
        mRange = maxVal - minVal;
        xIndexWidth = dm.widthPixels / (entries.size() - 1);

        // 开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 通知关闭线程
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            for (int i = 0, count = entries.size(); i < count; i++) {
                Entry e = entries.get(i);
                float newVal = (float) (1.0 * Math.sin(j++));
                e.setVal(newVal);
            }
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 150) {
                    Thread.sleep(150 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
