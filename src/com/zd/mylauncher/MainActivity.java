package com.zd.mylauncher;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity implements OnItemClickListener {

	private GridView mGridView;
	private Context mContext;
	private PackageManager mPackageManager;
	private List<ResolveInfo> mAllApps;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupViews();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		System.out.println(width+"<<<"+height);
	}

	public void setupViews() {
		mContext = MainActivity.this;
		mPackageManager = getPackageManager();
		mGridView = (GridView) findViewById(R.id.allapps);
		bindAllApps();

		mGridView.setAdapter(new MyAdapter(mContext, mAllApps));
		mGridView.setNumColumns(4);
		mGridView.setOnItemClickListener(this);
	}

	public void bindAllApps() {
		// 这里是关键哦，我们平时写的应用总有一个activity申明成这两个属性
		// 也就是应用的入口
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 符合上面条件的全部查出来,并且排序
		mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);
		Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(
				mPackageManager));
	}

	// gridview点击事件，点击进入相关应用
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ResolveInfo res = mAllApps.get(position);
		// 该应用的包名和主Activity
		String pkg = res.activityInfo.packageName;
		String cls = res.activityInfo.name;

		ComponentName componet = new ComponentName(pkg, cls);

		Intent i = new Intent();
		i.setComponent(componet);
		startActivity(i);
	}
}