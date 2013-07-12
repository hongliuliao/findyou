package com.findyou.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.findyou.model.UserInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DataHelper extends OrmLiteSqliteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	public DataHelper(Context context,String dataFileName) {
		super(context, dataFileName, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		// create database
		this.createTable(db, connectionSource);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int arg2, int arg3) {
		// update database
		this.updateTable(db, connectionSource);

	}

	@Override
	public void onOpen(SQLiteDatabase db) {

		super.onOpen(db);
		Log.d("DataHelper", "database is opened");
	}

	@Override
	public void close() {
		super.close();
		Log.d("DataHelper", "database is closed");

	}

	private void createTable(SQLiteDatabase db,ConnectionSource connectionSource) {

		try {
			Log.d("DataHelper", "create database");
			TableUtils.createTableIfNotExists(connectionSource, UserInfo.class);
		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "创建数据库失败" + e.getCause());
			e.printStackTrace();
		}
	}

	private void updateTable(SQLiteDatabase db, ConnectionSource connectionSource) {

		try {
			TableUtils.dropTable(connectionSource, UserInfo.class, true);

			onCreate(db, connectionSource);
		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "更新数据库失败" + e.getMessage());
			e.printStackTrace();
		}
	}

}