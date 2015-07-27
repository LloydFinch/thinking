package cn.androidy.thinking.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class MenifestDataUtils {
	public static String getMetaData(Context context, String key) {
		Object value = "";
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			value = ai.metaData.get(key);

		} catch (Exception e) {
		}
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersionName(Activity activity) {
		String versionName = "1.0.0";
		try {
			PackageManager manager = activity.getPackageManager();
			PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
			versionName = info.versionName;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 获取version code
	 */
	public static int getVersionCode(Activity activity) {
		int versionCode = 1000;
		try {
			PackageManager manager = activity.getPackageManager();
			PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static String getChannelName(Context context) {
		String channelName = LocalStorage.getIntance(context).getItem("UMENG_CHANNEL");
		if (!TextUtils.isEmpty(channelName)) {
			return channelName;
		} else {
			channelName = getMetaData(context, "UMENG_CHANNEL");
			LocalStorage.getIntance(context).setItem("UMENG_CHANNEL", channelName);
			return channelName;
		}
	}
}
