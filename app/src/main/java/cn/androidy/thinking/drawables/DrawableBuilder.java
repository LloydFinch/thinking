package cn.androidy.thinking.drawables;

import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by Rick Meng on 2015/6/19.
 */
public class DrawableBuilder {
    public static ShapeDrawable createRoundedRectDrawable(int color, float radius, float insetWidth) {
        float[] outerRadii = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        float[] innerRadii = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RectF inset = new RectF(insetWidth, insetWidth, insetWidth, insetWidth);
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
}
