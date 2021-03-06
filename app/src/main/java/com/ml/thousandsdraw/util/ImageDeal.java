package com.ml.thousandsdraw.util;

import android.graphics.*;
import android.view.*;

public class ImageDeal
{
	//设置新图片宽高
	public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){   
		// 获得图片的宽高   
		int width = bm.getWidth();   
		int height = bm.getHeight();   
		// 计算缩放比例   
		float scaleWidth = ((float) newWidth) / width;   
		float scaleHeight = ((float) newHeight) / height;   
		// 取得想要缩放的matrix参数   
		Matrix matrix = new Matrix();   
		matrix.postScale(scaleWidth, scaleHeight);   
		// 得到新的图片   www.2cto.com
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);   
		return newbm;   
	}  
	//压缩图片

	public static Bitmap decodeSampledBitmapFromFile(String path,
														 int reqWidth, int reqHeight) { 
// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小 
		final BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path,options);
// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); 
// 使用获取到的inSampleSize值再次解析图片 
		options.inJustDecodeBounds = false; 
		return 	BitmapFactory.decodeFile(path,options);
	}
	
//计算缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options, 
											int reqWidth, int reqHeight) { 
// 源图片的高度和宽度 
		final int height = options.outHeight; 
		final int width = options.outWidth; 
		int inSampleSize = 1; 
		if (height > reqHeight || width > reqWidth) { 
// 计算出实际宽高和目标宽高的比率 
			final int heightRatio = Math.round((float) height / (float) reqHeight); 
			final int widthRatio = Math.round((float) width / (float) reqWidth); 
// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高 
// 一定都会大于等于目标的宽和高。 
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio; 
		} 
		return inSampleSize; 
	}
	
	//把view变为bitmap
	public static Bitmap createViewBitmap(View v) {
		Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888); 
		Canvas canvas = new Canvas(bitmap); 
		v.draw(canvas);
		return bitmap; 
		}
}
