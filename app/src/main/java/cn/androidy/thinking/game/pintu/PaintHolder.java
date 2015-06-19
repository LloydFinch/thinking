package cn.androidy.thinking.game.pintu;

import android.graphics.Paint;

public class PaintHolder {
	private Paint paint;
	private JigsawLay mOwnerJigsawLay;

	public PaintHolder(Paint paint, JigsawLay mOwnerJigsawLay) {
		super();
		this.paint = paint;
		this.mOwnerJigsawLay = mOwnerJigsawLay;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public JigsawLay getmOwnerJigsawLay() {
		return mOwnerJigsawLay;
	}

	public void setmOwnerJigsawLay(JigsawLay mOwnerJigsawLay) {
		this.mOwnerJigsawLay = mOwnerJigsawLay;
	}

}
