package com.findyou.data;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public interface IDataContext {

	// 锟斤拷锟斤拷
	public abstract <T, ID> void add(T item, Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 删锟斤拷
	public abstract <T, ID> void delete(T item, Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 锟斤拷锟斤拷
	public abstract <T, ID> void update(T item, Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 锟斤拷锟絀D锟斤拷询
	public abstract <T, ID, K extends ID> T queryById(Class<T> dataClass,
			Class<K> idClass, ID id) throws SQLException;

	// 锟斤拷询全锟斤拷
	public abstract <T, ID> List<T> queryForAll(Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 锟斤拷锟斤拷锟斤拷锟斤拷锟窖�
	public abstract <T, ID> List<String[]> queryBySql(Class<T> dataClass,
			Class<ID> idClass, String query) throws SQLException;

	// 锟斤拷取全锟斤拷锟斤拷录锟斤拷
	public abstract <T, ID> long countof(Class<T> dataClass, Class<ID> idClass)
			throws SQLException;

	// 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷募锟铰硷拷锟�
	public abstract <T, ID> long countof(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException;

	/**
	 * 通锟矫诧拷询
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param dataClass
	 * @param idClass
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public abstract <T, ID> List<T> query(Class<T> dataClass,
			Class<ID> idClass, PreparedQuery<T> query) throws SQLException;

	/**
	 * 锟斤拷取QueryBuilder
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param dataClass
	 * @param idClass
	 * @return
	 * @throws SQLException
	 */
	public abstract <T, ID> QueryBuilder<T, ID> getQueryBuilder(
			Class<T> dataClass, Class<ID> idClass) throws SQLException;

	/**
	 * 锟斤拷始锟斤拷锟斤拷
	 */
	public abstract void beginTransaction();

	// 锟结交锟斤拷锟斤拷
	public abstract void commit();

	// 锟截癸拷锟斤拷锟斤拷
	public abstract void rollback();


}
