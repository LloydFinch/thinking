package cn.androidy.thinking.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Rick Meng on 2015/6/23.
 */
public class PickerLayer {
    public static float ITEM_SCALE = 1.8f;
    private Rect mTextBound = new Rect();
    public Object text;
    public int color;
    public float startX;
    public float baseline;
    public int canvasHeight;
    private int canvasWidth;
    private float top;
    float density;
    private int mIndex;


    private float mMaxTextAlpha = 255;
    private float mMinTextAlpha = 120;
    private float transY;

    public PickerLayer(Object text, float density) {
        this.text = text;
        this.density = density;
    }

    public String getText() {
        return text.toString();
    }

    public void initIndex(int index, int width, int height, Paint paint, float transY) {
        this.transY = transY;
        mIndex = index;
        float w = measureText(text.toString(), paint);//必须先测量让mTextBound获取值。
        startX = width / 2 - w / 2;//文字居中
        canvasHeight = height;
        canvasWidth = width;
        top = 0;
        Paint.FontMetricsInt fmi = paint.getFontMetricsInt();
        baseline = (float) (height / 2.0f - (fmi.bottom / 2.0 + fmi.top / 2.0) + index * mTextBound.height() * ITEM_SCALE) + transY;
    }

    private float measureText(String text, Paint paint) {
        float w = paint.measureText(text);
        mTextBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), mTextBound);
        return w;
    }

    public void doDraw(Canvas canvas, Paint paint, PickerLayer layer) {
        if (layer == this) {
            paint.setColor(0xffed145b);
        } else {
            paint.setColor(0xff333333);
        }
        float d = Math.abs(layer.baseline - baseline);
        float scale = getScaleTextSize(canvasHeight / 4, d);
        paint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(0, top, canvasWidth, canvasHeight);
        float w = measureText(text.toString(), paint);//必须先测量让mTextBound获取值。
        startX = canvasWidth / 2 - w / 2;//文字居中
        canvas.drawText(text.toString(), startX, baseline, paint);
        canvas.restore();
    }

    private float getScaleTextSize(float base, float transY) {
        float f = (float) (1 - Math.pow(transY / base, 2));
        return f < 0 ? 0 : f;
    }

    public int getItemHeight() {
        return (int) (mTextBound.height() * 1.0f);
    }
}
