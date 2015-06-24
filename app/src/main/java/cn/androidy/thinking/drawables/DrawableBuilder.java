package cn.androidy.thinking.drawables;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
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

    public static AnimationDrawable createAnimationDrawable(Context context, IDrawableFrame[] drawableResIdArray, boolean oneShot) {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (IDrawableFrame frame : drawableResIdArray) {
            animationDrawable.addFrame(context.getResources().getDrawable(frame.getResId()), frame.getDuration());
        }
        animationDrawable.setOneShot(oneShot);
        return animationDrawable;
    }

    public static interface IDrawableFrame {
        public int getResId();

        public int getDuration();
    }

    public static class SimpleFrame implements IDrawableFrame {
        private int resid;
        private int duration;

        public SimpleFrame(int resid, int duration) {
            this.duration = duration;
            this.resid = resid;
        }

        @Override
        public int getResId() {
            return resid;
        }

        @Override
        public int getDuration() {
            return duration;
        }
    }
}
