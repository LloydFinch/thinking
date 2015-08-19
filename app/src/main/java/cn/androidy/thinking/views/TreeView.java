package cn.androidy.thinking.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import cn.androidy.thinking.data.DemoViewTreeManager;

/**
 * Created by mwp on 2015/8/18.
 */
public class TreeView extends View {
    private float density;

    public TreeView(Context context) {
        super(context);
        init();
    }

    public TreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        density = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        DemoViewTreeManager.getInstance(density).draw(canvas, getWidth(), getHeight());
    }
}
