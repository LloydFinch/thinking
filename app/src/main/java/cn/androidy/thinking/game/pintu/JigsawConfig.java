package cn.androidy.thinking.game.pintu;

public class JigsawConfig {
	private int mRowCount = 4;
	private int mColCount = 4;

	public JigsawConfig(int row, int col) {
		super();
		this.mRowCount = row;
		this.mColCount = col;
	}

	public int getRowCount() {
		return mRowCount;
	}

	public int getColCount() {
		return mColCount;
	}
}
