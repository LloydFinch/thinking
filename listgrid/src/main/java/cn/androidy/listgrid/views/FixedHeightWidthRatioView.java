package cn.androidy.listgrid.views;

import android.content.Context;
import android.view.View;

public class FixedHeightWidthRatioView extends View {

	protected float mHeightWidthRatio;
	protected int mWidthInPx;

	public FixedHeightWidthRatioView(Context context, float ratio, int midthInPx) {
		super(context);
		mHeightWidthRatio = ratio;
		mWidthInPx = midthInPx;
	}

	public float getmHeightWidthRatio() {
		return mHeightWidthRatio;
	}

	public void setmHeightWidthRatio(float mHeightWidthRatio) {
		this.mHeightWidthRatio = mHeightWidthRatio;
	}

	public int getmWidthInPx() {
		return mWidthInPx;
	}

	public void setmWidthInPx(int mWidthInPx) {
		this.mWidthInPx = mWidthInPx;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mWidthInPx <= 0 || mHeightWidthRatio <= 0) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			setMeasuredDimension(mWidthInPx, (int) (mWidthInPx * mHeightWidthRatio));
		}
	}
}
