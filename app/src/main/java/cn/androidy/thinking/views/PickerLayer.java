package cn.androidy.thinking.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Rick Meng on 2015/6/23.
 */
public class PickerLayer {
    private Rect mTextBound = new Rect();
    public Object text;
    public int color;
    public float startX;
    public float baseline;
    private int canvasHeight;
    private int canvasWidth;
    private float top;
    float density;

    public PickerLayer(Object text, float density) {
        this.text = text;
        this.density = density;
    }

    public String getText() {
        return text.toString();
    }

    public void initIndex(int index, int width, int height, Paint paint) {
        float w = measureText(text.toString(), paint);//必须先测量让mTextBound获取值。
        startX = width / 2 - w / 2;//文字居中
        canvasHeight = height;
        canvasWidth = width;
        top = 0;
        Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
        baseline = (float) (height / 2.0f - (fmi.bottom / 2.0 + fmi.top / 2.0) + index * mTextBound.height() * 1.5f);
    }

    private float measureText(String text, Paint paint) {
        float w = paint.measureText(text);
        mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
        return w;
    }

    public void doDraw(Canvas canvas, Paint paint) {
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(0, top, canvasWidth, canvasHeight);
        canvas.drawText(text.toString(), startX, baseline, paint);
        canvas.restore();
    }
}
