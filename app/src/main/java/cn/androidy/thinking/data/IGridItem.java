package cn.androidy.thinking.data;


public interface IGridItem {
    public static final int TYPE_2x1 = 0;
    public static final int TYPE_3x1 = 1;
    public static final int TYPE_4x1 = 2;
    public static final int TYPE_DIVIDOR = 3;
    public static final int TYPE_COUNT = 4;

    public int getType();
}
