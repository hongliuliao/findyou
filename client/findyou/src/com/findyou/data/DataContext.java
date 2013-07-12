package com.findyou.data;

import java.sql.SQLException;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.findyou.utils.DatabaseConfigUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class DataContext  implements IDataContext{

	private ConnectionSource connectionSource;
	private SQLiteDatabase db; 

	public DataContext() {
		connectionSource = DatabaseConfigUtil.DATAHELPER.getConnectionSource();
		db = DatabaseConfigUtil.DATAHELPER.getWritableDatabase();
	}

	public <T, ID> void add(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.create(item);
	}

	public <T, ID> void delete(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.delete(item);
	}

	public <T, ID, K extends ID> void deleteById(ID id, Class<T> dataClass,
			Class<K> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.deleteById(id);
	}

	public <T, ID> void update(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.update(item);
	}

	public <T, ID, K extends ID> T queryById(Class<T> dataClass,
			Class<K> idClass, ID id) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryForId(id);
	}

	public <T, ID> List<T> queryForAll(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryForAll();
	}

	public <T, ID> List<String[]> queryBySql(Class<T> dataClass,
			Class<ID> idClass, String sql) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		List<String[]> list = rawResults.getResults();
		return list;
	}

	public <T, ID> List<T> query(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.query(query);
	}

	public <T, ID> long countof(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.countOf();
	}

	public <T, ID> long countof(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.countOf(query);
	}

	public <T, ID> QueryBuilder<T, ID> getQueryBuilder(Class<T> dataClass,
			Class<ID> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryBuilder();
	}

	public void beginTransaction() {
		db.beginTransaction();
	}

	public void commit() {
		db.setTransactionSuccessful();
	}

	public void rollback() {
		db.endTransaction();
	}
	

}
