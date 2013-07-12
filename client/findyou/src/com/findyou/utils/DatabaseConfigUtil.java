package com.findyou.utils;

import android.content.Context;

import com.findyou.data.DataHelper;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * 生成ORM映射文件
 * 
 * @author 福建师范大学软件学院 陈贝、刘大刚
 * @since 2012-6-8
 * 
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	public static DataHelper DATAHELPER;
	public static String DATAFILENAME="myPhone.db";
	
	public static void initDatabase(Context context) {
		DATAHELPER = new DataHelper(context, DATAFILENAME);
	}

}
