package cn.androidy.thinking.views;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick Meng on 2015/6/23.
 */
public class PickerLayerManager {
    private List<PickerLayer> mPickerLayerList = new ArrayList<PickerLayer>();
    private int mWidth;
    private int mHeight;
    private float density = 1.0f;
    private int itemHeight;
    private int selectedPosition = 0;
    public float transY;
    public boolean hasAutoChanged = false;
    //初始化绑定数据
    public PickerLayerManager(float density) {
        this.density = density;
    }

    public void bindData(List<Object> dataList) {
        mPickerLayerList.clear();
        for (Object o : dataList) {
            mPickerLayerList.add(new PickerLayer(o, density));
        }
    }

    //初始化画布的整体宽高
    public void initCanvasTotalArea(int width, int height) {
        mWidth = width;
        mHeight = height;

    }

    //初始化各个Layer的初始位置
    public void initLayerParams(Paint paint, float transY) {
        this.transY = transY;
        int i = 0;
        for (PickerLayer layer : mPickerLayerList) {
            layer.initIndex(i++, mWidth, mHeight, paint, transY);
        }
    }

    public void doDraw(Canvas canvas, Paint paint) {
        for (PickerLayer layer : mPickerLayerList) {
            layer.doDraw(canvas, paint, findSelectedLayer());
        }
    }

    //根据距离中线的最小距离确定当前选中的item，确保同一时间只有一个item被选中。
    private PickerLayer findSelectedLayer() {
        float minDistance = mHeight;
        for (int i = 0; i < mPickerLayerList.size(); i++) {
            PickerLayer layer = mPickerLayerList.get(i);
            float distance = Math.abs(layer.canvasHeight / 2 - layer.baseline);
            if (distance < minDistance) {
                minDistance = distance;
                selectedPosition = i;
                itemHeight = mPickerLayerList.get(selectedPosition).getItemHeight();
            }
        }
        return mPickerLayerList.get(selectedPosition);
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public int getSelectedIndex() {
        return selectedPosition;
    }
}
