package cn.androidy.thinking.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.androidy.listgrid.views.MultiColumnLayout;
import cn.androidy.thinking.R;
import cn.androidy.thinking.constant.Constants;

public class GridListView3x1 extends MultiColumnLayout {
    public GridListView3x1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridListView3x1(Context context, float heightWidthRatio, int widthInPx) {
        super(context, heightWidthRatio, widthInPx);
    }

    public void bindData(int itemSize) {
        if (itemSize == 0) {
            return;
        }
        trim(itemSize);
    }

    @Override
    protected int getLineResId() {
        return Constants.color_dividor;
    }

    @Override
    protected View createChildView() {
        return new ItemLayout(getContext());
    }

    @Override
    public int getColumn() {
        return 3;
    }

    public class ItemLayout extends RelativeLayout implements IMultiColumnItem {

        public ItemLayout(Context context) {
            super(context);
            RelativeLayout rlInner = new RelativeLayout(getContext());

            ImageView icon = new ImageView(getContext());
            icon = new ImageView(getContext());
            icon.setId(cn.androidy.listgrid.R.id.baseIconViewId);
            icon.setImageResource(R.drawable.ic_launcher);

            TextView title = new TextView(getContext());
            title.setId(cn.androidy.listgrid.R.id.baseTitleViewId);
            title.setTextColor(0xff3e3e3e);
            title.setText("abcd");

            LayoutParams p = new LayoutParams((int) (dm.density * 100), (int) (dm.density * 120));
            p.addRule(CENTER_HORIZONTAL);
            rlInner.addView(icon, p);

            p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            p.addRule(BELOW, icon.getId());
            p.addRule(CENTER_HORIZONTAL);
            p.setMargins(0, (int) (dm.density * 9), 0, 0);
            rlInner.addView(title, p);

            p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            p.addRule(CENTER_IN_PARENT);
            addView(rlInner, p);
        }

    }
}
