package cn.androidy.thinking.views;

import android.graphics.Canvas;
import android.graphics.Paint;

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
    public void initLayerParams(Paint paint) {
        int i = 0;
        for (PickerLayer layer : mPickerLayerList) {
            layer.initIndex(i++, mWidth, mHeight, paint);
        }
    }

    public void doDraw(Canvas canvas, Paint paint) {
        for (PickerLayer layer : mPickerLayerList) {
            layer.doDraw(canvas, paint);
        }
    }
}
