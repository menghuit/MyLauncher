package com.zd.mylauncher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;


public class BitmapManager {
	public static Runnable onLowMemory;

	public static Bitmap copy(Bitmap paramBitmap, Bitmap.Config paramConfig,
			boolean paramBoolean) {
		Bitmap localBitmap;
		try {
			localBitmap = paramBitmap.copy(paramConfig, paramBoolean);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = paramBitmap.copy(paramConfig, paramBoolean);
		}

		return localBitmap;
	}

	public static Bitmap getBitmap(int res,Context mContext)
    {
    	Bitmap bitmap = null;
    	try{
    		bitmap = getBitmap(mContext.getResources(), res, android.graphics.Bitmap.Config.ARGB_8888);
    	}catch(NullPointerException e){
    		bitmap = null;
    	}
    	return bitmap;
    }
	
	public static Bitmap createBitmap(int w, int h, Bitmap.Config paramConfig) {
		Bitmap localBitmap;
		try {
			localBitmap = Bitmap.createBitmap(w, h, paramConfig);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = Bitmap.createBitmap(w, h, paramConfig);
		}
		return localBitmap;
	}

	public static Bitmap createBitmap(Bitmap paramBitmap, int paramInt1,
			int paramInt2, int paramInt3, int paramInt4, Matrix paramMatrix,
			boolean paramBoolean) {
		Bitmap localBitmap;
		try {
			localBitmap = Bitmap.createBitmap(paramBitmap, paramInt1,
					paramInt2, paramInt3, paramInt4, paramMatrix, paramBoolean);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = Bitmap.createBitmap(paramBitmap, paramInt1,
					paramInt2, paramInt3, paramInt4, paramMatrix, paramBoolean);
		}
		return localBitmap;

	}

	public static Bitmap createCoverableBitmap(Bitmap paramBitmap,
			int paramInt1, int paramInt2, boolean paramBoolean) {
		int i = paramInt1;
		int j = paramInt2;
		if (paramBitmap.getWidth() < paramInt1)
			j = (int) (paramInt1 / paramBitmap.getWidth() * paramBitmap
					.getHeight());
		if (j < paramInt2)
			i = (int) (paramInt2 / paramBitmap.getHeight() * paramBitmap
					.getWidth());
		return createScaledBitmap(paramBitmap, i, j, paramBoolean);
	}

	public static Bitmap createScaledBitmap(Bitmap paramBitmap, int paramInt1,
			int paramInt2, boolean paramBoolean) {
		Bitmap localBitmap;
		try {
			localBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1,
					paramInt2, paramBoolean);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1,
					paramInt2, paramBoolean);
		}
		return localBitmap;
	}

	public static Bitmap decodeByteArray(byte[] paramArrayOfByte) {
		Bitmap localBitmap;
		try {
			localBitmap = BitmapFactory.decodeByteArray(paramArrayOfByte, 0,
					paramArrayOfByte.length);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = BitmapFactory.decodeByteArray(paramArrayOfByte, 0,
					paramArrayOfByte.length);
		}
		return localBitmap;
	}

	public static Bitmap decodeFile(String paramString,
			BitmapFactory.Options paramOptions) {
		Bitmap localBitmap;
		try {
			localBitmap = BitmapFactory.decodeFile(paramString, paramOptions);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = BitmapFactory.decodeFile(paramString, paramOptions);
		}
		return localBitmap;
	}

	public static Bitmap decodeStream(InputStream paramInputStream,
			BitmapFactory.Options paramOptions) {
		Bitmap localBitmap;
		try {
			localBitmap = BitmapFactory.decodeStream(paramInputStream, null,
					paramOptions);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = BitmapFactory.decodeStream(paramInputStream, null,
					paramOptions);
		}
		return localBitmap;
	}

	public static void destroy(ImageView paramImageView) {
		Drawable localDrawable = paramImageView.getDrawable();
		paramImageView.setImageDrawable(null);
		if (localDrawable != null)
			localDrawable.setCallback(null);
	}

	public static byte[] encodeToByteArray(Bitmap paramBitmap) {
		byte[] data;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			data = bos.toByteArray();
			bos.close();
			return data;
		} catch (IOException e) {
			return null;
		}
	}

	public static Bitmap fitWidth(Bitmap paramBitmap, int width) {
		if (paramBitmap.getWidth() != width)
			paramBitmap = createScaledBitmap(paramBitmap, width,
					(int) (paramBitmap.getHeight() * (width / paramBitmap
							.getWidth())), true);

		return paramBitmap;
	}

	private static void gc() {
		if ((onLowMemory != null)
				&& (Looper.getMainLooper().getThread() == Thread
						.currentThread()))
			onLowMemory.run();
		System.gc();
		System.runFinalization();
	}

	public static Bitmap getBitmap(Context ctx, int res) {
		return getBitmap(ctx.getResources(), res,
				Bitmap.Config.ARGB_8888);
	}

	public static Bitmap getBitmap(Context paramContext, int paramInt,
			Bitmap.Config paramConfig) {
		return getBitmap(paramContext.getResources(), paramInt, paramConfig);
	}

	public static Bitmap getBitmap(Resources paramResources, int paramInt,
			Bitmap.Config paramConfig) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inPreferredConfig = paramConfig;
		Bitmap localBitmap;
		try {
			localBitmap = BitmapFactory.decodeResource(paramResources,
					paramInt, localOptions);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localBitmap = BitmapFactory.decodeResource(paramResources,
					paramInt, localOptions);
		}
		return localBitmap;
	}

	public static Bitmap getBitmap(Drawable paramDrawable) {
		Bitmap localBitmap;
		if ((paramDrawable instanceof BitmapDrawable))
			localBitmap = ((BitmapDrawable) paramDrawable).getBitmap();
		else {
			localBitmap = createBitmap(paramDrawable.getIntrinsicWidth(),
					paramDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
			paramDrawable.setBounds(0, 0, localBitmap.getWidth(),
					localBitmap.getHeight());
			paramDrawable.draw(new Canvas(localBitmap));
		}
		return localBitmap;
	}

	public static Drawable getDrawable(Context paramContext, int paramInt) {
		return getDrawable(paramContext.getResources(), paramInt);
	}

	public static Drawable getDrawable(Resources paramResources, int paramInt) {
		Drawable localDrawable;
		try {
			localDrawable = paramResources.getDrawable(paramInt);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localDrawable = paramResources.getDrawable(paramInt);
		}
		return localDrawable;
	}

	public static Drawable getWallpaperDrawable(Context paramContext) {
		Drawable localDrawable;
		try {
			localDrawable = WallpaperManager.getInstance(paramContext)
					.getDrawable();
		} catch (OutOfMemoryError localOutOfMemoryError) {
			gc();
			localDrawable = WallpaperManager.getInstance(paramContext)
					.getDrawable();
		}
		return localDrawable;
	}
}

