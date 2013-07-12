package com.findyou.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.baidu.mapapi.BMapManager;

public class BMapUtil {
    	
	public static String MAP_KEY = "EB21E59591611451362F228A82E72CA98AEDC437";
	/**
	 * 从view 得到图片
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
	}
	
	public static BMapManager initBMapManager(Context context) {
		BMapManager mBMapMan = new BMapManager(context);
		mBMapMan.init(MAP_KEY, null);
		return mBMapMan;
	}
}
