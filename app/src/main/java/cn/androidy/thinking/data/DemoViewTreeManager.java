package cn.androidy.thinking.data;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import cn.androidy.thinking.views.ITraceView;

/**
 * Created by mwp on 2015/8/18.
 */
public class DemoViewTreeManager {

    private static DemoViewTreeManager manager;
    private View topView;
    private Paint textPaint;
    private float density;
    private int width;
    private int height;
    private int topY = 0;

    public View getTopView() {
        return topView;
    }

    public void setTopView(View topView) {
        this.topView = topView;
    }

    private DemoViewTreeManager(float density) {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(14 * density);
        this.density = density;
    }

    public static DemoViewTreeManager getInstance(float desity) {
        if (manager == null) {
            manager = new DemoViewTreeManager(desity);
        }
        return manager;
    }

    public void reset() {
        setTopView(null);
    }

    public void draw(Canvas canvas, int w, int h) {
        if (topView == null) {
            return;
        }
        int level = 0;
        this.width = w;
        this.height = h;
        topY = (int) (40 * density);
        drawTree(canvas, topView, width / 2, topY, 0);
    }

    private void drawTree(Canvas canvas, View view, float x, float y, int level) {
        if (view instanceof ITraceView) {
            canvas.drawText(((ITraceView) view).getTraceName(), x, y, textPaint);
        } else {
            canvas.drawText(((ITraceView) view).getTraceName(), x, y, textPaint);
        }
        if (view instanceof ViewGroup && ((ViewGroup) view).getChildCount() > 0) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0, count = vg.getChildCount(); i < count; i++) {
                drawTree(canvas, vg.getChildAt(i), findX(i, count, level + 1), findY(i, count, level + 1), level + 1);
            }
        }
    }

    private float findY(int i, int count, int level) {
        return topY + level * 100;
    }

    private float findX(int i, int count, int level) {
        int rangeWidth = width / (1 << level);
        return rangeWidth * (i + 1) - rangeWidth / 2;
    }
}
