package cn.androidy.thinking.game.pintu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * @author mwping1324@163.com Created on 2015年1月20日 下午4:41:36
 */
public class JigsawView extends View {
	private JigsawResManager jigsawResManager;
	private Paint stepsTextPaint;
	private Paint samplePicPaint;
	private Bitmap sampleBitmap;
	private BitmapShader shader;
	private RectF samplePicRectF;

	public JigsawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public JigsawView(Context context) {
		super(context);
		init();
	}

	public void init() {
		ScreenConfig.getInstance(getContext());
		stepsTextPaint = new Paint();
		stepsTextPaint.setAntiAlias(true);
		stepsTextPaint.setColor(0xff000000);
		stepsTextPaint.setTextSize(16 * ScreenConfig.getDensity());

		samplePicPaint = new Paint();
		samplePicPaint.setAntiAlias(true);
		samplePicPaint.setColor(0xff000000);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (jigsawResManager == null) {
			super.onDraw(canvas);
			return;
		}
		canvas.drawColor(0xffc2d7c2);
		jigsawResManager.draw(canvas);
		if (jigsawResManager.isGameComplete()) {
			canvas.drawText("成功", 3 * ScreenConfig.getDensity(), jigsawResManager.getSourceBitmapHeight() + 100,
					stepsTextPaint);
		} else {
			canvas.drawText("已使用步数:" + jigsawResManager.getStepTaken(), 3 * ScreenConfig.getDensity(),
					jigsawResManager.getSourceBitmapHeight() + 20 * ScreenConfig.getDensity(), stepsTextPaint);
		}
		canvas.drawRoundRect(samplePicRectF, 12, 12, samplePicPaint);
	}

	public void setPicture(Bitmap decodeResource, int row, int col) {
		Bitmap bmp = Bitmap.createScaledBitmap(decodeResource, 900, 900, false);
		sampleBitmap = Bitmap.createScaledBitmap(decodeResource, 500, 500, false);
		jigsawResManager = new JigsawResManager(bmp, row, col);
		jigsawResManager.checkResult();
		shader = new BitmapShader(sampleBitmap, BitmapShader.TileMode.REPEAT, BitmapShader.TileMode.REPEAT);
		samplePicPaint.setShader(shader);
		int left = 0;
		int top = 1000;
		int right = left + 500;
		int bottom = top + 500;
		samplePicRectF = new RectF(left, top, right, bottom);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionIndex();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 得到触点的位置
			if (jigsawResManager == null) {
				return super.onTouchEvent(event);
			} else {
				jigsawResManager.onTouchEvent(event, this);
				return false;
			}
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

}
