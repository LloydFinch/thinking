package cn.androidy.thinking.game.pintu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class JigsawResManager implements OnLayTouchedListener {
	private JigsawConfig jigsawConfig;
	private List<JigsawLay> jigsawLayList = new ArrayList<JigsawLay>();
	private int stepTaken = 0;
	private int mSourceBitmapHeight;
	private int mSourceBitmapWidth;
	private boolean isGameComplete = false;

	/**
	 * 
	 * @param bitmapDrawable
	 *            图片
	 * @param row
	 *            行数
	 * @param col
	 *            列数
	 */
	public JigsawResManager(Bitmap bitmap, int rowCount, int colCount) {
		if (bitmap == null || rowCount <= 0 || colCount <= 0) {
			return;
		}
		mSourceBitmapHeight = bitmap.getHeight();
		mSourceBitmapWidth = bitmap.getWidth();
		jigsawConfig = new JigsawConfig(rowCount, colCount);
		JigsawLay jigsawLay;
		/**
		 * 生成有序图片序列，拼成原图
		 */
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				jigsawLay = new JigsawLay(bitmap, jigsawConfig, row, col);
				jigsawLayList.add(jigsawLay);
				if (col == colCount - 1 && row == rowCount - 1) {
					jigsawLay.setBlank(true);
				}
			}
		}

		/**
		 * 打乱图片顺序
		 */
		List<PaintHolder> holdersList = new ArrayList<PaintHolder>();
		for (int i = 0; i < jigsawLayList.size() - 1; i++) {
			holdersList.add(jigsawLayList.get(i).getPaintHolder());
		}
		Collections.shuffle(holdersList);
		for (int i = 0; i < jigsawLayList.size() - 1; i++) {
			jigsawLayList.get(i).setPaintHolder(holdersList.get(i));
		}
	}

	public void draw(Canvas canvas) {
		if (jigsawLayList != null && !jigsawLayList.isEmpty()) {
			for (JigsawLay jigsawLay : jigsawLayList) {
				if (jigsawLay != null) {
					jigsawLay.draw(canvas);
				}
			}
		}
	}

	public void onTouchEvent(MotionEvent event, View v) {
		if (jigsawLayList != null && !jigsawLayList.isEmpty()) {
			for (JigsawLay jigsawLay : jigsawLayList) {
				if (jigsawLay != null) {
					jigsawLay.onTouchEvent(event, this, v);
				}
			}
		}
	}

	@Override
	public void onTouched(JigsawLay jigsawLay, View v) {
		if (jigsawLayList != null && !jigsawLayList.isEmpty()) {
			int index = jigsawLayList.indexOf(jigsawLay);
			int blankIndex = getLeftIndex(index);
			if (blankIndex != -1 && jigsawLayList.get(blankIndex).isBlank()) {
				exchangePivotAndDraw(jigsawLay, jigsawLayList.get(blankIndex), v);
				return;
			}
			blankIndex = getTopIndex(index);
			if (blankIndex != -1 && jigsawLayList.get(blankIndex).isBlank()) {
				exchangePivotAndDraw(jigsawLay, jigsawLayList.get(blankIndex), v);
				return;
			}
			blankIndex = getRightIndex(index);
			if (blankIndex != -1 && jigsawLayList.get(blankIndex).isBlank()) {
				exchangePivotAndDraw(jigsawLay, jigsawLayList.get(blankIndex), v);
				return;
			}
			blankIndex = getBottom(index);
			if (blankIndex != -1 && jigsawLayList.get(blankIndex).isBlank()) {
				exchangePivotAndDraw(jigsawLay, jigsawLayList.get(blankIndex), v);
				return;
			}
		}
	}

	/**
	 * 单元格之间交换的过程 ，根据游戏逻辑控制，jigsawLay1为有色单元格，jigsawLay2必然为空白单元格
	 * 
	 * @param jigsawLay1
	 * @param jigsawLay2
	 * @param v
	 *            单元格所在的视图（所有单元格处在同一个View）
	 */
	private synchronized void exchangePivotAndDraw(final JigsawLay jigsawLay1, final JigsawLay jigsawLay2, final View v) {
		final boolean horizonal = jigsawLay1.getTop() == jigsawLay2.getTop();
		final float mInitLeftOrTop1 = horizonal ? jigsawLay1.getLeft() : jigsawLay1.getTop();
		final float mInitLeftOrTop2 = horizonal ? jigsawLay2.getLeft() : jigsawLay2.getTop();
		/**
		 * 为了使有色单元格和空白单元格的交换过程看起来平滑，加上了平移的过渡动画
		 */
		ValueAnimator animator = ValueAnimator.ofInt(0, (int) (mInitLeftOrTop2 - mInitLeftOrTop1));
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				/**
				 * 动画只做临时渲染之用，动画结束后对坐标进行复位，以进行后续的游戏逻辑
				 */
				if (horizonal) {
					jigsawLay1.setLeft(mInitLeftOrTop1);
					jigsawLay2.setLeft(mInitLeftOrTop2);
				} else {
					jigsawLay1.setTop(mInitLeftOrTop1);
					jigsawLay2.setTop(mInitLeftOrTop2);
				}
				jigsawLay1.exchangePaintAndPivot(jigsawLay2);
				v.postInvalidate();
				checkResult();
				stepTaken++;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int temp = (Integer) animation.getAnimatedValue();
				if (horizonal) {
					jigsawLay1.setLeft(mInitLeftOrTop1 + temp);
					jigsawLay2.setLeft(mInitLeftOrTop2 - temp);
				} else {
					jigsawLay1.setTop(mInitLeftOrTop1 + temp);
					jigsawLay2.setTop(mInitLeftOrTop2 - temp);
				}
				v.postInvalidate();
			}
		});
		animator.setDuration(150);
		animator.start();
	}

	private int getLeftIndex(int index) {
		if (index % jigsawConfig.getColCount() == 0) {
			return -1;
		}
		return index - 1;
	}

	private int getRightIndex(int index) {
		if (index % jigsawConfig.getColCount() == jigsawConfig.getColCount() - 1) {
			return -1;
		}
		return index + 1;
	}

	private int getTopIndex(int index) {
		if (index / jigsawConfig.getRowCount() == 0) {
			return -1;
		}
		return index - jigsawConfig.getColCount();
	}

	private int getBottom(int index) {
		if (index / jigsawConfig.getRowCount() == jigsawConfig.getRowCount() - 1) {
			return -1;
		}
		return index + jigsawConfig.getColCount();
	}

	public boolean checkResult() {
		if (jigsawLayList != null && !jigsawLayList.isEmpty()) {
			for (JigsawLay jigsawLay : jigsawLayList) {
				if (jigsawLay.getPaintHolder().getmOwnerJigsawLay() != jigsawLay) {
					Log.d("mwp", "checkResult--->" + jigsawLay.getRow() + "," + jigsawLay.getRow()
							+ " is incorrect position");
					isGameComplete = false;
					return isGameComplete;
				}
			}
			Log.d("mwp", "checkResult--->success");
			isGameComplete = true;
			return isGameComplete;
		}
		isGameComplete = true;
		return isGameComplete;
	}

	public int getStepTaken() {
		return stepTaken;
	}

	public int getSourceBitmapHeight() {
		return mSourceBitmapHeight;
	}

	public int getSourceBitmapWidth() {
		return mSourceBitmapWidth;
	}

	public boolean isGameComplete() {
		return isGameComplete;
	}
}
