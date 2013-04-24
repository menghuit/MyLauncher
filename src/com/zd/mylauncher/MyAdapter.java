package com.zd.mylauncher;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	// 不明白BaseAdapter的用法 我高手进阶里有
	private Context context;
	private List<ResolveInfo> resInfo;
	private PackageManager mPackageManager;
	private Drawable icon_Pattern, icon_Mask, icon_Border;
	

	// 构造函数
	public MyAdapter(Context content, List<ResolveInfo> res) {
		context = content;
		resInfo = res;
		mPackageManager = content.getPackageManager();
		icon_Pattern = content.getResources().getDrawable(R.drawable.icon_pattern);
		icon_Mask = content.getResources().getDrawable(R.drawable.icon_mask);
		icon_Border = content.getResources().getDrawable(R.drawable.icon_border);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHold mHold = null;
		// 不明白LayoutInflater的我android高手进阶里有
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
			mHold = new MyHold();
			mHold.img = (ImageView) convertView.findViewById(R.id.app_icon);
			mHold.tv = (TextView) convertView.findViewById(R.id.app_title);
			convertView.setTag(mHold);
		}else{
			mHold=(MyHold) convertView.getTag();
		}
		
		ResolveInfo res = resInfo.get(position);
		Rect rect=new Rect(0,0,icon_Pattern.getIntrinsicWidth(),icon_Pattern.getIntrinsicHeight());
		Bitmap bmp=BitmapManager.getBitmap(res.loadIcon(mPackageManager));
		mHold.img.setImageBitmap(getIcon(bmp,rect));
		
//		mHold.img.setImageDrawable(res.loadIcon(mPackageManager));
		mHold.tv.setText(res.loadLabel(mPackageManager).toString());
		return convertView;
	}

	private static class MyHold {
		ImageView img;
		TextView tv;
	}
	private Bitmap getIcon(Bitmap bmp, Rect rect_Bg) {
		int iconWidth = bmp.getWidth();
		int iconHeight = bmp.getHeight();
		int bgWidth=rect_Bg.width();
		int bgHeight=rect_Bg.height();
		
		int offest = 6;
		Rect rect_src =rect_Bg;
		if(bgWidth-iconWidth<offest||bgHeight-iconHeight<offest){
			rect_src = new Rect(offest, offest, iconWidth - offest, iconHeight- offest);
		}
		// 用icon_mask处理icon，如果icon超出，则裁切成icon_mask的形状
		Bitmap bmp_Filleting = icon_Crop(bmp, PorterDuff.Mode.SRC_IN, rect_Bg,rect_Bg, this.icon_Mask);
		// 把裁切好的icon贴上背景
		Bitmap bmp_Bg = icon_Crop(bmp_Filleting, PorterDuff.Mode.SRC_OVER,rect_Bg, rect_src, icon_Pattern);
		bmp_Filleting.recycle();
//		// 添加右下角的border
		Bitmap bmp_Border = icon_Crop(bmp_Bg, PorterDuff.Mode.DST_ATOP, rect_Bg,rect_Bg, icon_Border);
		bmp_Bg.recycle();

		return bmp_Border;
	}

	private Bitmap icon_Crop(Bitmap bmp, Mode modeCrop, Rect rect_dst,Rect rect_src, Drawable dst) {
		int width = rect_dst.width();
		int height = rect_dst.height();
		Bitmap bmp_Base = BitmapManager.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp_Base);
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		paint.setXfermode(new PorterDuffXfermode(modeCrop));
		dst.setBounds(rect_dst);
		dst.draw(canvas);
		canvas.drawBitmap(bmp, rect_dst, rect_src, paint);
		return bmp_Base;
	}
}
