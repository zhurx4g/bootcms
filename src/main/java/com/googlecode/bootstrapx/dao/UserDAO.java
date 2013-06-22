package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.User;

@DAO
public interface UserDAO {
	public static final String TABLE_NAME = "common_user";
	public static final String COLUMNS = "id,email,name,password,creatorId,updaterId,createTime,updateTime,status";

	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " #if(:status>-1){ where status=:status } #if (:orderBy != null) { order by ##(:orderBy) } limit :offset,:size")
	public List<User> getList(@SQLParam("offset") int offset, @SQLParam("size") int size, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);

	@SQL("select count(*) from " + TABLE_NAME + " #if(:status>-1){ where status=:status }")
	public int getCount(@SQLParam("status") int status);

	@SQL("update " + TABLE_NAME + " set status=0,updateTime=unix_timestamp() where id=:userId")
	public int removeById(@SQLParam("userId") int userId);

	@SQL("replace into " + TABLE_NAME + "(" + COLUMNS + ") values(null,:user.email,:user.name,:user.password,:user.creatorId,:user.updaterId,:user.createTime,:user.updateTime,:user.status)")
	public int addForRows(@SQLParam("user") User user);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE_NAME + "(" + COLUMNS + ") values(null,:user.email,:user.name,:user.password,:user.creatorId,:user.updaterId,unix_timestamp(),unix_timestamp(),:user.status)")
	public int add(@SQLParam("user") User user);

	@SQL("update " + TABLE_NAME + " set status=1, password=:user.password, updateTime=unix_timestamp() where id=:user.id")
	public int update(@SQLParam("user") User user);

	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " where id=:userId")
	public User get(@SQLParam("userId") int userId);

	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " where name=:name")
	public User get(@SQLParam("name") String name);
}
