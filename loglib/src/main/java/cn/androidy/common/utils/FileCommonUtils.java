package cn.androidy.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCommonUtils {
	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 *            源文件
	 * @param t
	 *            复制到的新文件
	 */
	public static File fileChannelCopy(File s) {
		if (s == null || !s.exists()) {
			return null;
		}
		String fileName = s.getName();
		File t = new File(s.getParent() + "/copyOf" + fileName);
		File dir = t.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (t.exists()) {
			t.delete();
		}
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * 获取可以使用的缓存目录
	 * 
	 * @param context
	 * @param uniqueName
	 *            目录名称
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String dir) {
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? getExternalCacheDir(
				context).getPath()
				: context.getCacheDir().getPath();
		File f = new File(cachePath + File.separator + dir);
		return f;
	}

	/**
	 * 获取程序外部的缓存目录
	 * 
	 * @param context
	 * @return
	 */
	public static File getExternalCacheDir(Context context) {
		return new File(Environment.getExternalStorageDirectory().getPath() + "/");
	}

	/**
	 * Returns the largest power-of-two divisor for use in downscaling a bitmap
	 * that will not result in the scaling past the desired dimensions.
	 *
	 * @param actualWidth
	 *            Actual width of the bitmap
	 * @param actualHeight
	 *            Actual height of the bitmap
	 * @param desiredWidth
	 *            Desired width of the bitmap
	 * @param desiredHeight
	 *            Desired height of the bitmap
	 */
	public static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
		double wr = (double) actualWidth / desiredWidth;
		double hr = (double) actualHeight / desiredHeight;
		double ratio = Math.min(wr, hr);
		float n = 1.0f;
		while ((n * 2) <= ratio) {
			n *= 2;
		}
		return (int) n;
	}

	public static int getExifOrientation(String filepath) {// YOUR MEDIA PATH AS
		// STRING
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	public static Bitmap rotate(Bitmap sourceBitmap, float rotate) {
		if (sourceBitmap == null) {
			return null;
		}
		Matrix m = new Matrix();
		m.postRotate(rotate, sourceBitmap.getWidth() / 2, sourceBitmap.getHeight() / 2);

		Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), m,
				false);
		sourceBitmap.recycle();
		sourceBitmap = null;
		sourceBitmap = bitmap;
		return bitmap;
	}
}
