package com.findyou.data;

import java.sql.SQLException;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.findyou.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class DataContext  implements IDataContext{

	private ConnectionSource connectionSource;//锟斤拷菘锟斤拷锟斤拷锟�
	private SQLiteDatabase db; // 锟斤拷锟斤拷锟斤拷锟�

	public DataContext() {
		connectionSource = MyMapActivity.DATAHELPER.getConnectionSource();
		db = MyMapActivity.DATAHELPER.getWritableDatabase();
	}

	// 锟斤拷锟斤拷
	public <T, ID> void add(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.create(item);
	}

	// 删锟斤拷
	public <T, ID> void delete(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.delete(item);
	}

	// 锟斤拷ID删锟斤拷
	public <T, ID, K extends ID> void deleteById(ID id, Class<T> dataClass,
			Class<K> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.deleteById(id);
	}

	// 锟斤拷锟斤拷
	public <T, ID> void update(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.update(item);
	}

	// 锟斤拷锟絀D锟斤拷询
	public <T, ID, K extends ID> T queryById(Class<T> dataClass,
			Class<K> idClass, ID id) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryForId(id);
	}

	// 锟斤拷询锟斤拷锟叫硷拷录
	public <T, ID> List<T> queryForAll(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryForAll();
	}

	// 锟斤拷锟絊QL锟斤拷锟斤拷询
	public <T, ID> List<String[]> queryBySql(Class<T> dataClass,
			Class<ID> idClass, String sql) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		List<String[]> list = rawResults.getResults();
		return list;
	}

	// 通锟矫诧拷询
	public <T, ID> List<T> query(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.query(query);
	}

	// 锟斤拷取锟斤拷锟叫硷拷录锟斤拷
	public <T, ID> long countof(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.countOf();
	}

	// 锟斤拷取指锟斤拷锟斤拷锟斤拷锟侥硷拷录锟斤拷
	public <T, ID> long countof(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.countOf(query);
	}

	// 锟斤拷取QueryBuilder
	public <T, ID> QueryBuilder<T, ID> getQueryBuilder(Class<T> dataClass,
			Class<ID> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryBuilder();
	}

	// 锟斤拷始锟斤拷锟斤拷
	public void beginTransaction() {
		db.beginTransaction();
	}

	// 锟结交锟斤拷锟斤拷
	public void commit() {
		db.setTransactionSuccessful();
	}

	// 锟截癸拷锟斤拷锟斤拷
	public void rollback() {
		db.endTransaction();
	}
	

}
