package com.findyou.utils;

import android.content.Context;

import com.findyou.data.DataHelper;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	public static DataHelper DATAHELPER;
	public static String DATAFILENAME="myPhone.db";
	
	public static void initDatabase(Context context) {
		DATAHELPER = new DataHelper(context, DATAFILENAME);
	}

}
