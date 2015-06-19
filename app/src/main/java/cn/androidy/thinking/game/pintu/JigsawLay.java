package cn.androidy.thinking.game.pintu;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class JigsawLay {
	private float mLeft;
	private float mTop;
	private float mWidth;
	private float mHeight;
	private Bitmap bitmap;
	private PaintHolder paintHolder;
	private Paint blankPaint;
	private BitmapShader shader;
	private int row;
	private int col;
	private JigsawConfig config;
	private boolean isBlank = false;

	public JigsawLay(Bitmap source, JigsawConfig config, int row, int col) {
		super();
		/**
		 * 大图的高
		 */
		int h = source.getHeight();
		/**
		 * 大图的宽
		 */
		int w = source.getWidth();
		/**
		 * config 是对魔板参数的简单包装，包括多少行、多少列等。这里是4行4列
		 */
		this.config = config;
		/**
		 * 单元格的长与宽
		 */
		mWidth = 1.0f * w / config.getColCount();
		mHeight = 1.0f * h / config.getRowCount();
		this.col = col;
		this.row = row;
		/**
		 * 单元格左上角在View的canvas中的坐标
		 */
		mLeft = mWidth * col;
		mTop = mHeight * row;
		/**
		 * 绘制单元格，取大图中相应位置的图片创建出画笔的着色器，用于绘制单元格到画布中
		 */
		bitmap = Bitmap.createBitmap(source, (int) (mLeft), (int) (mTop), (int) (mWidth), (int) (mHeight));
		/**
		 * TileMode.REPEAT为平铺重复模式
		 */
		shader = new BitmapShader(bitmap, BitmapShader.TileMode.REPEAT, BitmapShader.TileMode.REPEAT);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);
		/**
		 * paintHolder是为了交换当前单元格和空白单元格
		 */
		paintHolder = new PaintHolder(paint, this);
		/**
		 * 用于绘制空白单元格
		 */
		blankPaint = new Paint();
		blankPaint.setColor(0xffffffff);
	}

	public void exchangePaintAndPivot(JigsawLay jigsawLay) {

		boolean tempBlank = jigsawLay.isBlank();
		jigsawLay.setBlank(isBlank());
		setBlank(tempBlank);

		PaintHolder tempPaint = jigsawLay.getPaintHolder();
		jigsawLay.setPaintHolder(paintHolder);
		setPaintHolder(tempPaint);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	/**
	 * 绘制单元格
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		RectF rectf;
		int left = (int) (mLeft + 2);
		int top = (int) (mTop + 2);
		int right = (int) (left + mWidth - 4);
		int bottom = (int) (top + mHeight - 5);
		if (row == config.getRowCount() - 1) {
			rectf = new RectF(left, top, right, bottom);
		} else {
			rectf = new RectF(left, top, right, bottom + 1);
		}
		if (isBlank) {
			/**
			 * 空白单元格
			 */
			canvas.drawRoundRect(rectf, 12, 12, blankPaint);
		} else {
			/**
			 * 图片单元格，为了使图片看起来不会太生硬，对单元格进行了圆角处理
			 */
			canvas.drawRoundRect(rectf, 12, 12, paintHolder.getPaint());
		}
	}

	public void setBlank(boolean b) {
		isBlank = b;
	}

	public boolean isBlank() {
		return isBlank;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void onTouchEvent(MotionEvent event, OnLayTouchedListener listener, View v) {
		boolean isTouched = isTouched(event);
		if (isTouched && !isBlank) {
			if (listener != null) {
				listener.onTouched(this, v);
			}
		} else {

		}
	}

	public PaintHolder getPaintHolder() {
		return paintHolder;
	}

	public void setPaintHolder(PaintHolder paintHolder) {
		this.paintHolder = paintHolder;
	}

	private boolean isTouched(MotionEvent event) {
		// 得到触点的位置
		float x = event.getX();
		float y = event.getY();
		if (y > mTop && y < mTop + mHeight && x > mLeft && x < mLeft + mWidth) {
			return true;
		}
		return false;
	}

	public float getWidth() {
		return mWidth;
	}

	public float getHeight() {
		return mHeight;
	}

	public float getLeft() {
		return mLeft;
	}

	public float getTop() {
		return mTop;
	}

	public void setLeft(float mLeft) {
		this.mLeft = mLeft;
	}

	public void setTop(float mTop) {
		this.mTop = mTop;
	}
}
