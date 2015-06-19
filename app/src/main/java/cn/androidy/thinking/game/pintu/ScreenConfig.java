package cn.androidy.thinking.game.pintu;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 手机屏幕参数
 * 
 * @author mwping1324@163.com Created on 2015年1月8日 下午12:08:06
 */
public class ScreenConfig {

	private static ScreenConfig instance = null;
	private DisplayMetrics displayMetrics;
	private int width = 320;
	private int height = 480;

	private ScreenConfig() {

	}

	public synchronized static ScreenConfig getInstance(Context context) {
		if (instance == null) {
			instance = new ScreenConfig();
		}
		if (context != null) {
			instance.displayMetrics = context.getResources().getDisplayMetrics();
			instance.initScreenSize();
		}
		return instance;
	}

	private void initScreenSize() {
		if (displayMetrics != null) {
			width = displayMetrics.widthPixels;
			height = displayMetrics.heightPixels;
		}
	}

	public int getScreenWidthPixels() {
		return width;
	}

	public int getScreenHeightPixels() {
		return height;
	}

	public static float getDensity() {
		return (instance == null || instance.displayMetrics == null) ? 1.0f : instance.displayMetrics.density;
	}
}
