package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick Meng on 2015/6/25.
 */
public class MoonsetCurveView extends View {
    private Paint mPaint;

    private List<PointF> pointFList = new ArrayList<PointF>();

    public MoonsetCurveView(Context context) {
        super(context);
        init();
    }


    public MoonsetCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoonsetCurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        pointFList.clear();
        int mw = getMeasuredWidth();
        pointFList.add(new PointF(mw * 0.2f, 400));
        pointFList.add(new PointF(mw * 0.4f, 600));
        pointFList.add(new PointF(mw * 0.6f, 700));
        pointFList.add(new PointF(mw * 0.8f, 500));
        pointFList.add(new PointF(mw * 1f, 750));
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);
    }

    protected void onDraw(Canvas canvas) {};


}
