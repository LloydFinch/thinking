package cn.androidy.thinking.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.androidy.logger.core.Log;
import cn.androidy.thinking.R;
import cn.androidy.thinking.constant.Constants;

/**
 * @author mwp 2015-8-10 18:18:20
 */
public class MyView extends View implements ITraceView {
    private String traceName;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MyTraceView);
        traceName = attributes.getString(R.styleable.MyTraceView_trace_name);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        StringBuilder sb = new StringBuilder(traceName + "  onMeasure start...");
        String parentName;
        if (getParent() instanceof ITraceView) {
            parentName = ((ITraceView) getParent()).getTraceName();
        } else {
            parentName = getParent().toString();
        }
        sb.append("\nparent=" + parentName + "\n");
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp != null) {
            if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                sb.append("\nLayoutParams width=WRAP_CONTENT");
            } else if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                sb.append("\nLayoutParams width=MATCH_PARENT");
            } else {
                sb.append("\nLayoutParams width=" + lp.width);
            }

            if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                sb.append("\nLayoutParams height=WRAP_CONTENT");
            } else if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                sb.append("\nLayoutParams height=MATCH_PARENT");
            } else {
                sb.append("\nLayoutParams height=" + lp.height);
            }
        }
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            sb.append("\nwidthMode=UNSPECIFIED,widthSize=" + widthSize);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            sb.append("\nwidthMode=AT_MOST,widthSize=" + widthSize);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            sb.append("\nwidthMode=EXACTLY,widthSize=" + widthSize);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            sb.append("\nheightMode=UNSPECIFIED,heightSize=" + heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            sb.append("\nheightMode=AT_MOST,heightSize=" + heightSize);
        } else if (heightMode == MeasureSpec.EXACTLY) {
            sb.append("\nheightMode=EXACTLY,heightSize=" + heightSize);
        }

        Log.d(Constants.LOG_TAG_VIEW_LAYOUT, sb.toString());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(Constants.LOG_TAG_VIEW_LAYOUT, traceName + ">>>>>>>>>>>>\nonLayout start...\n" +
                "changed=" + changed + "; left=" + left + ";top=" + top + ";right=" + right + ";bottom=" + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }


    public String getTraceName() {
        return traceName;
    }
}
