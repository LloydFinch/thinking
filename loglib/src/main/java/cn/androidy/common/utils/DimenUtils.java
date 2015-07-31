package cn.androidy.common.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 像素工具类
 * 
 * @author mwp Created on 2015年7月16日 下午8:03:23
 */
public class DimenUtils {

	/*将sp转化为px*/
	public static float spTopx(Context context, int sp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
	}
}
