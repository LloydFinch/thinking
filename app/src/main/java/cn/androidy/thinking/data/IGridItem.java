package cn.androidy.thinking.data;


public interface IGridItem {
    public static final int TYPE_2x1 = 0;
    public static final int TYPE_3x1 = 1;
    public static final int TYPE_4x1 = 2;
    public static final int TYPE_METRO1 = 3;
    public static final int TYPE_METRO3 = 4;
    public static final int TYPE_METRO4 = 5;
    public static final int TYPE_METRO6 = 6;
    public static final int TYPE_DIVIDOR = 7;
    public static final int TYPE_COUNT = 8;

    public int getType();
}
