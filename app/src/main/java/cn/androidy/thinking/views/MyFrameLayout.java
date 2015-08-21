package cn.androidy.thinking.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.androidy.logger.core.Log;
import cn.androidy.thinking.R;
import cn.androidy.thinking.constant.Constants;

/**
 * Created by mwp on 2015/8/12.
 */
public class MyFrameLayout extends RelativeLayout implements ITraceView {
    private String traceName;

    public MyFrameLayout(Context context) {
        this(context, null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MyTraceView);
        traceName = attributes.getString(R.styleable.MyTraceView_trace_name);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        StringBuilder sb = new StringBuilder(traceName + "  onMeasure start...");
        sb.append("\nparent=" + getParent() + "\n");
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
