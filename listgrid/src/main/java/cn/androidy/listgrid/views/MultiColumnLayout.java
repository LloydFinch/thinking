package cn.androidy.listgrid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 实现行列排布规则的视图，避免嵌套GridView或者ListView
 *
 * @author mwp Created on 2015年7月30日 下午3:07:47
 */
public abstract class MultiColumnLayout extends FixedHeightWidthRatiolayout {

    private float itemHeightRatio;

    public MultiColumnLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiColumnLayout(Context context, float heightWidthRatio, int widthInPx) {
        super(context, heightWidthRatio, widthInPx);
    }

    @Override
    protected void init() {
        super.init();
        itemHeightRatio = mHeightWidthRatio;
        trim(getColumn());
    }

    /**
     * 保留合适的子View数量
     *
     * @param itemSize
     */
    protected void trim(int itemSize) {
        int itemCount = getItemViewCount();
        int col = getColumn();
        int row = itemSize / col;
        if (mHeightWidthRatio != itemHeightRatio * row) {
            mHeightWidthRatio = itemHeightRatio * row;
            notifyHeightChanged();
        }
        float percentOfItemWidth = 1.0f / col;
        float percentOfItemHeight = 1.0f / row;
        if (itemCount > itemSize) {
            removeViews(itemSize, itemCount - itemSize);
        } else if (itemCount < itemSize) {
            for (int i = 0; i < itemSize - itemCount; i++) {
                View v = createChildView();
                addView(v, i % col * percentOfItemWidth, (itemCount + i) / col * percentOfItemHeight,
                        percentOfItemWidth, percentOfItemHeight);
            }
        }
        if (enableDividorLine()) {
            addDividorlines(row, col);
        }
    }

    protected void addDividorlines(int row, int col) {
        for (int i = 1; i < row; i++) {
            addHorizontalLine(getLineResId(), 0, i * 1.0f / row, getLineWidth(), 1.0f);
        }
        for (int i = 1; i < col; i++) {
            addVerticalLine(getLineResId(), i * 1.0f / col, 0, getLineWidth(), 1.0f);
        }
    }

    protected abstract int getLineResId();

    protected boolean enableDividorLine() {
        return true;
    }

    protected int getLineWidth() {
        return 2;
    }

    protected View getItemView(int index) {
        int childCount = getChildCount();
        int itemIndex = -1;
        View v;
        for (int i = 0; i < childCount; i++) {
            v = getChildAt(i);
            if (isItemView(v)) {
                itemIndex++;
            }
            if (itemIndex == index) {
                return v;
            }
        }
        return null;
    }

    protected abstract View createChildView();

    /**
     * 获取Item数目
     *
     * @return
     */
    protected int getItemViewCount() {
        int childCount = getChildCount();
        int itemCount = 0;
        View v;
        for (int i = 0; i < childCount; i++) {
            v = getChildAt(i);
            if (isItemView(v)) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public abstract int getColumn();

    /**
     * 为了区别同级别的其他非Item的子View
     *
     * @return
     */
    protected boolean isItemView(View v) {
        return v instanceof IMultiColumnItem;
    }

    /**
     * 所有每个子Item必须实现此接口
     *
     * @author mwp Created on 2015年7月30日 下午3:17:50
     */
    public static interface IMultiColumnItem {

    }
}
