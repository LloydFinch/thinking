package cn.androidy.thinking.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.StatFs;
import android.provider.MediaStore.MediaColumns;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 框架工具类，和业务无关的工具
 * 
 * @author lib
 * @version 1.0.0
 */
public class CommonUtils {
	public static void hideSoftInputFromWindow(View v) {
		if (v == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	public static String formatDouble(double d) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return String.valueOf(decimalFormat.format(d));
	}

	/**
	 * dp(dip): device independent pixels(设备独立像素).
	 * 不同设备有不同的显示效果,这个和设备硬件有关，一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
	 * dp也就是dip，这个和sp基本类似。如果设置表示长度、高度等属性时可以使用dp
	 * 或sp。但如果设置字体，需要使用sp。dp是与密度无关，sp除了与密度无关外，还与scale无关。如果屏幕密度为160，这时dp和sp和px是一
	 * 样的。1dp=1sp=1px，但如果使用px作单位，如果屏幕大小不变（假设还是3.2寸），而屏幕密度变成了320。那么原来TextView的宽度
	 * 设成160px，在密度为320的3.2寸屏幕里看要比在密度为160的3.2寸屏幕上看短了一半。但如果设置成160dp或160sp的话。系统会自动
	 * 将width属性值设置成320px的。也就是160 * 320 / 160。其中320 /
	 * 160可称为密度比例因子。也就是说，如果使用dp和sp，系统会根据屏幕密度的变化自动进行转换。 px: pixels(像素).
	 * 不同设备显示效果相同，一般我们HVGA代表320x480像素，这个用的比较多。 pt:
	 * point，是一个标准的长度单位，1pt＝1/72英寸，用于印刷业，非常简单易用； sp: scaled pixels(放大像素).
	 * 主要用于字体显示best for textsize。
	 */

	/**
	 * dp、sp 转换为 px 的工具类
	 * 
	 * @author fxsky 2012.11.12
	 *
	 */

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static boolean saveURLAs(String photoURL, String fileName) {
		File file = new File(fileName);
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		try {
			URL URL = new URL(photoURL);
			HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();
			in.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String saveBmpToSDByAbsFilepath(String absFilepath, Bitmap bitmap) {
		final String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED) && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)
				|| TextUtils.isEmpty(absFilepath) || bitmap == null) {
			return "";
		}
		try {
			File file = new File(absFilepath);
			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
			return absFilepath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static ConnectivityManager connManager = null;// 网络连接服务
	private static TelephonyManager telephonyManager = null;// 电话服务
	private static NetworkInfo ni = null;
	public static int TYPE_MOBILE_CMNET = 1;
	public static int TYPE_MOBILE_CMWAP = 2;
	public static int TYPE_WIFI = 3;
	public static int TYPE_NO = 0;
	public static String imsi = null;

	String[] phone_d = new String[8];

	/**
	 * 获取 cotent的MD5值
	 *
	 * @param content
	 * @return
	 */
	public static String getMD5(String content) {

		String plaintext = content;
		String hashtext = null;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			hashtext = hashtext.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (hashtext == null) {
			return "null";
		}

		return hashtext;
	}

	/**
	 * @param context
	 * @return 返回联网类型
	 */
	public static String getNetType(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
			if (!"wifi".equals(typeName)) {
				// 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
				typeName = info.getExtraInfo().toLowerCase();
			}
			// LogUtils.d("nettype", "typeName===" + typeName);
			return typeName;
		} catch (Exception e) {
			return null;
		}
	}

	private static Bitmap normalPoint; // gallery指示点
	private static Bitmap selectPoint; // gallery指示点

	/**
	 * 绘制Gallery滚动时的分页点
	 * 
	 * @param totalNum
	 *            总页数
	 * @param position
	 *            当前页号
	 * @param a
	 *            默认的分页点图片
	 * @param b
	 *            选择时的分页点图片
	 * @param size
	 *            分页点的大小
	 * @return Bitmap 分页点图片
	 */
	public static Bitmap drawPoint(int totalNum, int position, Context context, int a, int b, int size) {
		if (normalPoint == null) {
			normalPoint = ((BitmapDrawable) context.getResources().getDrawable(a)).getBitmap();
		}
		if (selectPoint == null) {
			selectPoint = ((BitmapDrawable) context.getResources().getDrawable(b)).getBitmap();
		}
		Bitmap bitmap = Bitmap.createBitmap(totalNum * size, size, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		int x = 0;

		for (int i = 0; i < totalNum; i++) {
			if (i == position) {
				canvas.drawBitmap(selectPoint, x, 0, null);
			} else {
				canvas.drawBitmap(normalPoint, x, 0, null);
			}
			x += size;
		}

		return bitmap;
	}

	/**
	 * 处理时间字符串"xxxx-xx-xx xx:xx:xx   ==>    xxxx年xx月xx日 xx:xx:xx"
	 * 
	 * @param source
	 *            原时间字符串
	 * @param haveTime
	 *            是否要显示具体时间
	 * @return
	 */
	public static String formatTimeString(String source, boolean haveTime) {
		if (!textIsNull(source)) {
			if (source.contains(" ")) {
				String[] all = source.split(" ");
				String[] dobs = all[0].split("-");
				String time = " " + all[1];
				String result = dobs[0] + "年" + dobs[1] + "月" + dobs[2] + "日";
				if (haveTime) {
					result += time;
				}
				return result;
			} else {
				String[] dobs = source.split("-");
				return dobs[0] + "年" + dobs[1] + "月" + dobs[2] + "日";
			}
		} else {
			return "";
		}
	}

	/**
	 * 获取操作系统版本
	 * 
	 * @return
	 */
	public static String getOS() {
		return Build.VERSION.RELEASE;// Firmware/OS 版本号
	}

	/**
	 * 判断手机是否可以加截屏安全标识
	 * 
	 * @return boolean
	 */
	public static boolean accessToScreenShotsForThisPhone() {
		if ("MI-ONE Plus".equals(getModel())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获得运营商
	 * 
	 * @param cont
	 * @return
	 */
	public static String getOperators(Context cont) {
		String operators = null;
		TelephonyManager phoneMgr = (TelephonyManager) cont.getSystemService(Context.TELEPHONY_SERVICE);

		imsi = phoneMgr.getSubscriberId();

		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				operators = "cm";
			} else if (imsi.startsWith("46001")) {
				operators = "cu";
			} else if (imsi.startsWith("46003")) {
				operators = "ct";
			}
		} else if (imsi == null) {
			imsi = phoneMgr.getSimOperator();
			if (imsi != null) {
				if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
					operators = "cm";
				} else if (imsi.startsWith("46001")) {
					operators = "cu";
				} else if (imsi.startsWith("46003")) {
					operators = "ct";
				}
			}
		}
		return operators;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getMobileName() {
		return Build.DEVICE;
	}

	/**
	 * 获取手机的MAC地址
	 * 
	 * @param con
	 * @return
	 */
	public static String getMacAddress(Context con) {
		WifiManager wifi = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	public static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	// 根据Uri获取实际文件名
	public static String getRealPathFromURI(Uri contentUri, Context mContext) {
		String[] proj = { MediaColumns.DATA };
		Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	/**
	 * 察看网络类型 wifi,3g, 2g
	 * 
	 * @param context
	 * @return
	 */
	public static String net2gOR3g(Context context) {
		String nettype = getNetType(context);
		if ("wifi".equals(nettype)) {
			return "WIFI";
		} else if ("3gnet".equals(nettype)) {
			return "3G";
		} else if ("3gwap".equals(nettype)) {
			return "3G";
		} else {
			return "2G";
		}
	}

	/**
	 * 检查string是否为空
	 * 
	 * @param string
	 * @return boolean true:不为空 false 为空
	 */
	public static boolean textIsNull(String string) {
		if (null == string || string.length() == 0 || string.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static int bytes2int(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	public static byte[] int2bytes(int i) {
		byte[] b = new byte[4];

		b[0] = (byte) (0xff & i);
		b[1] = (byte) ((0xff00 & i) >> 8);
		b[2] = (byte) ((0xff0000 & i) >> 16);
		b[3] = (byte) ((0xff000000 & i) >> 24);
		return b;
	}

	/**
	 * bytes转换成bitmap
	 * 
	 * @param data
	 *            需要转换的字节数据
	 * @return Bitmap
	 */
	public static Bitmap Bytes2BimapWithOption2(byte[] data) {
		if (data.length != 0) {
			Options option = new Options();
			Bitmap temBmp;
			option.inSampleSize = data.length / 1024 / 150;
			temBmp = BitmapFactory.decodeByteArray(data, 0, data.length, option);
			return temBmp;
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * bytes转换成bitmap
	 * 
	 * @param data
	 *            需要转换的字节数据
	 * @return Bitmap
	 */
	public static Bitmap bytes2Bimap(byte[] data) {
		if (data.length != 0) {
			Bitmap temBmp;
			temBmp = BitmapFactory.decodeByteArray(data, 0, data.length, null);
			return temBmp;
		} else {
			throw new NullPointerException();
		}
	}

	public static byte[] Is2Bytes(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(100);
		int n;
		try {
			while ((n = in.read()) != -1) {
				out.write(n);
			}
			in.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * Bitmap转换成bytes[]
	 * 
	 * @param drawable
	 *            Bitmap对象
	 * @return byte[]
	 */
	public static byte[] Bitmap2Bytes(Bitmap drawable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		drawable.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 *      * Bitmap转化为drawable      * @param bitmap      * @return     
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	/**
	 *      * Drawable 转 bitmap      * @param drawable      * @return     
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
					drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * 读取图片连接地址的文件名
	 */
	public static String getFileName(String url) {

		String fileName = url.substring(url.lastIndexOf("/") + 1);
		return fileName;
	}

	/**
	 * 获取屏幕管理类
	 * 
	 * @return DisplayMetrics 屏幕管理对象
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics displayMetrics = null;
		if (displayMetrics == null) {
			displayMetrics = new DisplayMetrics();
		}
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	// 建立一个MIME类型与文件后缀名的匹配表
	private static final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" }, { ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".bat", "text/plain" }, { ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".doc", "application/msword" }, { ".exe", "application/octet-stream" }, { ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" }, { ".h", "text/plain" },
			{ ".htm", "text/html" }, { ".html", "text/html" }, { ".jar", "application/java-archive" },
			{ ".java", "text/plain" }, { ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" }, { ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
			{ ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" }, { ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" },
			// {".xml", "text/xml"},
			{ ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/zip" }, { "", "*/*" } };

	/**
	 * 截取字符串（截取后的字符串尾部加“...”效果）
	 * 
	 * @param str
	 *            源字符串
	 * @param cutOutCount
	 *            截取源字符串的前几位
	 * @return String 截取后的字符串
	 */
	public static String resetString(String str, int cutOutCount) {
		if (str != null) {
			if (str.length() > cutOutCount) {
				return str.substring(0, cutOutCount - 1) + "...";
			} else {
				return str;
			}
		}
		return "";
	}

	/**
	 * 删除指定文件
	 * 
	 * @param pathName
	 *            图片目录路径
	 * @param ImageUrl
	 *            数据库图片路径
	 */
	public static void deleteFile(final String pathName, final String ImageUrl) {
		File file = new File(getFileName(ImageUrl));
		// 判断文件是否存在
		if (!file.exists()) {
			return;
		}
		// 删除指定文件
		file.delete();
	}

	/**
	 * 删除指定文件
	 * 
	 * @param pathName
	 *            路径
	 */
	public static void deleteFile(String pathName) {
		File file = new File(pathName);
		// 判断文件是否存在
		if (!file.exists()) {
			return;
		}
		// 删除指定文件
		file.delete();
	}

	/**
	 * 删除指定目录下所有文件
	 * 
	 * @param filePath
	 *            删除文件路径
	 */
	public static void deleteAllFile(final String filePath) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File display = new File(filePath);
					if (!display.exists()) {
						return;
					}
					File[] items = display.listFiles();
					int i = display.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (items[j].isFile()) {
							items[j].delete();// 删除文件
						} else {
							// 迭代删除
							deleteAllFile(items[j].getAbsolutePath());
							// 删除目录
							items[j].delete();
						}
					}
				} catch (Exception ex) {
					// ex.printStackTrace();
				}

			}
		});
		t.start();
	}

	/**
	 * 保存图片到本地
	 * 
	 * @param map
	 * @param path
	 * @param fileName
	 */
	public static boolean saveFile(Bitmap map, String path, String fileName) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			File picPath = new File(path + "/" + fileName);
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(picPath));
				if (map != null) {
					map.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					bos.flush();
					bos.close();
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 读取本地图片
	 * 
	 * @param fileName
	 *            图片的本地文件名称
	 * @return Bitmap 图片对应的Bitmap对象
	 */
	public static Bitmap readPicFromLocal(String fileName) {
		return BitmapFactory.decodeFile(fileName);
	}

	/**
	 * 读取本地文件返回字节数组
	 * 
	 * @param fileName
	 *            文件名
	 * @return byte[]
	 * @param handler
	 *            通知ui加载进度
	 */
	public static byte[] readBytesFromLocal(String fileName, Handler handler) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(fileName);
			if (0 == file.length() && null != file) {
				file.delete();
				return null;
			}
			InputStream in = null;
			byte[] bytes = null;
			try {
				in = new FileInputStream(file);
				bytes = getBytes(file.length(), in, handler);
				if (null != bytes && 0 == bytes.length && null != file) {
					file.delete();
					return null;
				}
			} catch (FileNotFoundException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} finally {
				// 关闭流
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
			}

			return bytes;
		}
		return null;
	}

	/**
	 * 获取SD卡剩余空间的大小
	 * 
	 * @return long SD卡剩余空间的大小（单位：byte）
	 */
	public static long getSDSize() {
		String str = Environment.getExternalStorageDirectory().getPath();
		StatFs localStatFs = new StatFs(str);
		long blockSize = localStatFs.getBlockSize();
		return localStatFs.getAvailableBlocks() * blockSize;
	}

	/**
	 * 获取运营商信息
	 * 
	 * @param con
	 *            上下文
	 * @return String 运营商信息
	 */
	public static String getCarrier(Context con) {
		TelephonyManager telManager = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telManager.getSubscriberId();
		if (imsi != null && imsi.length() > 0) {
			// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				return "China Mobile";
			} else if (imsi.startsWith("46001")) {
				return "China Unicom";
			} else if (imsi.startsWith("46003")) {
				return "China Telecom";
			}
		}
		return "未能识别";
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 *            上下文
	 * @return String 返回网络类型
	 */
	public static String getAccessNetworkType(Context context) {
		try {
			connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connManager == null) {
				return "";
			}
			if (connManager.getActiveNetworkInfo() == null) {
				return "";
			}
			int netType = connManager.getActiveNetworkInfo().getType();
			if (netType == ConnectivityManager.TYPE_WIFI) {
				return "wifi";
			} else {
				return "gprs";
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return "";
	}

	/**
	 * 是否是wifi网络
	 * 
	 * @param context
	 *            上下文
	 * @return true 是wifi false 不是wifi
	 */
	public static boolean isWifiNet(Context context) {
		String netType = getAccessNetworkType(context);
		if (netType.length() > 0 && netType.equals("wifi")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 读取手机串号
	 * 
	 * @param con
	 *            上下文
	 * @return String 手机串号IMEI
	 */
	public static String readTelephoneSerialNum(Context con) {
		telephonyManager = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * 获取当前操作系统的语言
	 * 
	 * @return String 系统语言
	 */
	public static String getSysLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return String 手机型号
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * 获取操作系统的版本号
	 * 
	 * @return String 系统版本号
	 */
	public static String getSysRelease() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 读取sim卡序列号
	 */
	public static String readSimSerialNum(Context con) {
		if (con == null) {
			return "";
		}
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager.getSimSerialNumber();
	}

	/**
	 * 下载网络图片
	 * 
	 * @param imageUrl
	 *            网络图片地址
	 * @return Bitmap 返回bitmap
	 * @throws Exception
	 */
	public static Bitmap loadImageFromUrl(String imageUrl, String savePath) throws Exception {
		URL url = null;// 网络请求
		Bitmap bitmap = null;// 用于返回
		// 网络获取输入流
		url = new URL(imageUrl);
		if (url == null || url.getContent() == null) {
			return null;
		}
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(30 * 1000);
		conn.connect();

		InputStream in = conn.getInputStream();
		bitmap = BitmapFactory.decodeStream(in);
		// 关闭流
		if (in != null) {
			in.close();
		}

		return bitmap;
	}

	/**
	 * 获取图片的字节数组
	 * 
	 * @param is
	 *            图片内容输入流
	 * @return byte[] 图片的字节数组
	 * @param handler
	 *            通知ui下载进度
	 */
	public static byte[] getBytes(long length, InputStream is, Handler handler) throws IOException {
		length = length > Integer.MAX_VALUE ? Integer.MAX_VALUE : length;
		ByteArrayOutputStream baos = new ByteArrayOutputStream((int) length);
		byte[] b = new byte[1024];
		int len = 0;
		int templength = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
			if (null != handler) {
				templength += len;
				Message msg = new Message();
				msg.arg1 = (int) (templength * 100 / length);
				handler.sendMessage(msg);
			}
		}
		byte[] bytes = baos.toByteArray();

		return bytes;
	}

	/**
	 * 获取图片的字节数组
	 * 
	 * @param is
	 *            图片内容输入流
	 * @return byte[] 图片的字节数组
	 */
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;

		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();

		return bytes;
	}

	/**
	 * 保存字节流到文件
	 * 
	 * @param data
	 *            字节数据
	 * @param path
	 *            保存路径
	 * @param fileName
	 *            保存文件名
	 */
	public static void saveBytes(byte[] data, String path, String fileName) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			File picPath = new File(path + "/" + fileName);
			BufferedOutputStream bufferedOutputStream;
			try {
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(picPath));
				bufferedOutputStream.write(data);
				bufferedOutputStream.close();
			} catch (FileNotFoundException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * 截取字符串的长度
	 * 
	 * @param s
	 *            原始字符串
	 * @param length
	 *            需要截取的长度
	 * @return String 处理后的字符串
	 * @throws Exception
	 *             异常对象
	 */
	public static String subString(String s, int length) throws Exception {

		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		length += i;
		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1)

		{
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}

		return new String(bytes, 0, i, "Unicode");
	}

	/**
	 * 将null转换成""
	 * 
	 * @param content
	 *            源内容信息
	 * @return String
	 */
	public static String getEmptyString(String content) {
		if (content == null) {
			return "";
		}
		return content;
	}

	/**
	 * 计算指定时间是否在结束时间之后
	 * 
	 * @param starttime
	 *            指定时间
	 * @param endtime
	 *            结束时间
	 * @return boolean
	 */
	public static boolean isAfterTime(String starttime, String endtime) {
		int syear = Integer.parseInt(starttime.split(" ")[0].split("-")[0]);
		int smonth = Integer.parseInt(starttime.split(" ")[0].split("-")[1]);
		int sday = Integer.parseInt(starttime.split(" ")[0].split("-")[2]);
		int smin = Integer.parseInt(starttime.split(" ")[1].split("-")[0]);
		int ssec = Integer.parseInt(starttime.split(" ")[1].split("-")[1]);

		int eyear = Integer.parseInt(endtime.split(" ")[0].split("-")[0]);
		int emonth = Integer.parseInt(endtime.split(" ")[0].split("-")[1]);
		int eday = Integer.parseInt(endtime.split(" ")[0].split("-")[2]);
		int emin = Integer.parseInt(endtime.split(" ")[1].split("-")[0]);
		int esec = Integer.parseInt(endtime.split(" ")[1].split("-")[1]);
		Date startTime = new Date(syear, smonth, sday, smin, ssec);
		Date endTime = new Date(eyear, emonth, eday, emin, esec);
		if (null == startTime || null == endTime) {
			return false;
		} else if (startTime.after(endTime)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将时间字符串变成date
	 * 
	 * @param timeString
	 * @return
	 */
	public static Date getDate(String timeString) {
		Date d = null;
		if (textIsNull(timeString)) {
			return null;
		}
		if (timeString.length() <= 16) {
			timeString = timeString + ":01";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 日期格式化类
		try {
			d = simpleDateFormat.parse(timeString);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
			return d;
		}
	}

	/**
	 * 判断网络
	 * 
	 * @param context
	 *            上下文
	 * @return boolean true: 网络可用；false: 网络不可用
	 */
	public static boolean isAccessNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable()) {
			return true;
		}
		return false;
	}

	/**
	 * 检测网络 wifi等 如果用户设置了代理。就是使用代理来连接
	 */
	public static Proxy detectProxy(Context mContext) {
		try {
			ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			// 获得当前网络信息
			NetworkInfo ni = cm.getActiveNetworkInfo();
			// ni != null && ni.isAvailable() && ni.getType() ==
			// ConnectivityManager.TYPE_MOBILE &&
			// ni.getExtraInfo().equals("cmwap")
			if (ni != null && ni.isAvailable() && ni.getType() == ConnectivityManager.TYPE_MOBILE) {

				Proxy mProxy = null; // 代理
				String proxyHost = android.net.Proxy.getDefaultHost(); // 代理主机
				int port = android.net.Proxy.getDefaultPort(); // 代理端口
				if (proxyHost != null) {
					final InetSocketAddress sa = new InetSocketAddress(proxyHost, port);
					mProxy = new Proxy(Proxy.Type.HTTP, sa);
					return mProxy;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * 获得当前网络类型
	 * 
	 * @param mContext
	 *            上下文
	 * @return TYPE_MOBILE_CMNET:1 TYPE_MOBILE_CMWAP:2 TYPE_WIFI:3
	 *         TYPE_NO:0(未知类型)
	 */
	public static int getNetWorkType(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获得当前网络信息
		ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isAvailable()) {
			int currentNetWork = ni.getType();
			if (currentNetWork == ConnectivityManager.TYPE_MOBILE) {
				if (ni.getExtraInfo().equals("cmwap")) {
					return TYPE_MOBILE_CMWAP;
				} else if (ni.getExtraInfo().equals("uniwap")) {
					return TYPE_MOBILE_CMWAP;
				} else if (ni.getExtraInfo().equals("3gwap")) {
					return TYPE_MOBILE_CMWAP;
				} else {
					return TYPE_MOBILE_CMNET;
				}

			} else if (currentNetWork == ConnectivityManager.TYPE_WIFI) {
				return TYPE_WIFI;
			}
		}
		return TYPE_NO;
	}

	// added by xujinshan at 2011-12-23 end

	/**
	 * 判断是否满足邮箱格式
	 * 
	 * @param email
	 *            邮箱地址string
	 * @return boolean 是否是邮箱格式
	 */
	public static boolean isEmailFormat(String email) {
		// String regex = "\\w{0,}\\@\\w{0,}\\.{1}\\w{0,}"; //正则表达式，定义邮箱格式
		String regex = "[\\w]+@[\\w]+.[\\w]+";
		if (email.matches(regex))
			return true;
		return false;
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(float pxValue, float scale) {
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(float dipValue, float scale) {
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue, float fontScale) {
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue, float fontScale) {
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSDCardMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 获取系统VersionCode版本号 是否显示新手引导
	 * 
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
		} catch (NameNotFoundException e) {
			// System.out.println("------>>>Utils getVersionCode() versionCode no found");
		}
		return versionCode;
	}

	/**
	 * 获取系统version名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 根据字符串返回时间 yyyy.MM.dd HH:mm
	 * 
	 * @param str
	 * @return
	 */
	public static String getTime(Long str) {
		Date date = new Date(str);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 根据字符串返回时间 yyyyMMdd
	 * 
	 * @param str
	 * @return
	 */
	public static String getDay(Long str) {
		Date date = new Date(str);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 判断注册的生日是否大于等于当前年份
	 * 
	 * @param sourceYear
	 *            生日
	 * @return 是否符合要求
	 */
	public static boolean isBirthdayYearValid(int sourceYear) {
		// Date date = new Date(System.currentTimeMillis());
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		if (sourceYear >= curYear) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据字符串返回时间 yyyy-MM-dd HH:mm
	 * 
	 * @param str
	 * @return
	 */
	public static String getTimeSystemFormate(Long str) {
		Date date = new Date(str);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 根据字符串返回时间 yyyy年MM月dd日
	 * 
	 * @param str
	 *            xxxx-xx-xx xx:xx:xx
	 * @return
	 */
	public static String getTimeSystemFormate(String str) {
		if (TextUtils.isEmpty(str) || str.trim().length() == 0) {
			return str;
		} else {
			String ymd;
			if (str.contains(" ")) {
				ymd = str.split(" ")[0];
			} else {
				ymd = str;
			}
			if (ymd.contains("-")) {
				String[] ymds = ymd.split("-");
				return ymds[0] + "年" + ymds[1] + "月" + ymds[2] + "日";
			} else {
				return str;
			}
		}
	}

	/**
	 * 返回当前系统版本
	 * 
	 * @return
	 */
	public static String getSystemVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 系统是否是2.3或者以上
	 * 
	 * @return
	 */
	public static boolean isSupportAPI9() {
		String strVer = getSystemVersion();
		strVer = strVer.substring(0, 3).trim();
		float fv = Float.valueOf(strVer);
		if (fv >= 2.3) {
			return true;
		}

		return false;
	}

	/**
	 * 对象转数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] obj2ByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 数组转对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byteArray2Object(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取有效的文件名
	 * 
	 * @param str
	 * @return
	 */
	public static String getValidFileName(String str) {
		str = str.replace("\\", "");
		str = str.replace("&", "");
		str = str.replace("=", "");
		str = str.replace(".", "");
		str = str.replace("/", "");
		str = str.replace(":", "");
		str = str.replace(";", "");
		str = str.replace("*", "");
		str = str.replace("?", "");
		str = str.replace("\"", "");
		str = str.replace("<", "");
		str = str.replace(">", "");
		str = str.replace("|", "");
		str = str.replace(" ", "_"); // 前面的替换会产生空格,最后将其一并替换掉
		return str;
	}

	public static String getFileNameFromHtml(String source) {
		String prefix = source.substring(source.lastIndexOf(".") + 1);
		String filename = getValidFileName(source);
		return filename + "." + prefix;
	}

	public static void play(Activity context, String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		String type = "video/*";
		Uri uri = Uri.parse(url);
		i.setDataAndType(uri, type);
		context.startActivity(i);
	}

	public static void systemDel(Activity context, String number) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + number));
		context.startActivity(intent);
	}

	// 计算两点距离
	private static final double EARTH_RADIUS = 6378137.0;

	public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static final String READ_SETTINGS_PERMISSION = "com.android.launcher.permission.READ_SETTINGS";
	public static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.action.INSTALL_SHORTCUT";
	private static final String TAG = "Utils";

	/**
	 * 创建桌面快捷方式
	 * 
	 * @param context
	 * @param 点击快捷方式进入的Activity
	 * @param title
	 *            快捷方式显示名
	 * @param iconRes
	 *            快捷方式图标的resource id
	 */
	public static void createShortcut(Context context, Class<?> activity, String title, int iconRes) {
		if (context == null || activity == null || isShortcutExist(context, title)) {
			return;
		}
		Intent addIntent = new Intent(INSTALL_SHORTCUT_PERMISSION);
		Parcelable icon = Intent.ShortcutIconResource.fromContext(context, iconRes);// 获取快捷键的图标
		addIntent.putExtra("duplicate", false);
		Intent myIntent = new Intent(context, activity);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);// 快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);// 快捷方式的动作
		context.sendBroadcast(addIntent);
		return;
	}

	public static boolean isShortcutExist(Context context, String title) {
		String shortcutTitle = getShortcutTitle(context, title);
		Log.d(TAG, "isShortcutExist shortcutTitle =" + shortcutTitle);
		return !TextUtils.isEmpty(shortcutTitle);
	}

	/**
	 * 
	 * @param context
	 * @param titleToCheck
	 *            检查该名称的快捷方式是否存在
	 * @return
	 */
	public static String getShortcutTitle(Context context, String titleToCheck) {
		if (context == null) {
			return null;
		}
		try {
			String authority = getAuthorityFromPermission(context, READ_SETTINGS_PERMISSION);
			Log.d(TAG, "getAuthorityFromPermission=" + authority);
			final String uriStr = "content://" + authority + "/favorites?notify=true";
			final Uri uri = Uri.parse(uriStr);
			// final Cursor c = context.getContentResolver().query(uri, new
			// String[] { "title" }, "title=?",
			// new String[] { titleToCheck }, null);
			final Cursor c = context.getContentResolver().query(uri, null, "title=?", new String[] { titleToCheck },
					null);
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					// for (int i = 0, count = c.getColumnCount(); i < count;
					// i++) {
					// Log.d(TAG, "getColumn name=" + c.getColumnName(i));
					// Log.d(TAG, "getColumn value=" + c.getString(i));
					// }
					String iconPackage = c.getString(c.getColumnIndexOrThrow("iconPackage"));
					String title = c.getString(c.getColumnIndexOrThrow("title"));
					Log.d(TAG, "getShortcutTitle=" + title);
					Log.d(TAG, "getShortcutTitle iconPackage=" + iconPackage);
					/**
					 * 必须是同一个包名的同名快捷方式才认为是同一个快捷方式
					 */
					if (context.getPackageName() != null && context.getPackageName().equals(iconPackage)) {
						return title;
					}
				} while (c.moveToNext());
			} else {
				Log.d(TAG, "c.getCount() = " + c.getCount());
				return null;
			}
		} catch (SQLException e) {
			/**
			 * 有的rom没有桌面快捷方式概念，应用程序直接在桌面，不存在快捷方式管理数据库，则直接返回应用程序名称
			 */
			Log.e(TAG, "getShortcutTitle Exception e=" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			String emsg = e.getMessage();
			Log.e(TAG, "getShortcutTitle Exception e=" + emsg);
			return null;
		}
		return null;
	}

	/**
	 * The launcher is an Application under the Handset Manufacturer
	 * responsibility. The Authority is then not always
	 * "com.android.launcher2.settings". The Handset Manufacturer may rewrite
	 * its own. It can be "com.android.twlauncher" or anything else depending on
	 * the Java package. You need to retrieve the right authority by searching
	 * for a provider that declares the read/write permissions
	 * "com.android.launcher.permission.READ_SETTINGS" or
	 * "com.android.launcher.permission.WRITE_SETTINGS".
	 * 
	 * @param context
	 * @param permission
	 * @return e.g. com.baidu.launcher2.settings
	 */
	public static String getAuthorityFromPermission(Context context, String permission) {
		if (permission == null)
			return null;
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						Log.d(TAG, "provider Permission=" + provider.readPermission);
						if (permission.equals(provider.readPermission))
							return provider.authority;
						if (permission.equals(provider.writePermission))
							return provider.authority;
					}
				}
			}
		}
		return null;
	}

	/** */
	/**
	 * 通过网站域名URL获取该网站的源码
	 * 
	 * @param url
	 * @return String
	 * @throws Exception
	 */
	public static String getURLSource(URL url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream(); // 通过输入流获取html二进制数据
		byte[] data = readInputStream(inStream); // 把二进制数据转化为byte字节数据
		String htmlSource = new String(data);
		return htmlSource;
	}

	/** */
	/**
	 * 把二进制流转化为byte字节数组
	 * 
	 * @param instream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream instream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1204];
		int len = 0;
		while ((len = instream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		instream.close();
		return outStream.toByteArray();
	}

	private static final int MOBILE_NUM_LENGTH = 11;

	public static boolean isLegalMobileNumber(String numStr) {
		if (!TextUtils.isEmpty(numStr) && numStr.length() == MOBILE_NUM_LENGTH) {
			for (int i = 0; i < numStr.length(); i++) {
				if (!isNumeric(numStr.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isWrongMobileNumber(String numStr) {
		return !TextUtils.isEmpty(numStr) && !isLegalMobileNumber(numStr);
	}

	private static boolean isNumeric(char c) {
		return c >= '0' && c <= '9';
	}
}
