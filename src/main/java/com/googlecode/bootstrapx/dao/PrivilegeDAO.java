package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.Privilege;

@DAO
public interface PrivilegeDAO {
	public static final String TABLE_NAME = "common_privilege";
	public static final String COLUMNS = "id,parentId,name,link,createTime,updateTime,status";

	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " #if(:status>-1){ where status=:status } #if (:orderBy != null) { order by ##(:orderBy) } limit :offset,:size")
	public List<Privilege> getList(@SQLParam("offset") int offset, @SQLParam("size") int size, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);

	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " where parentId=:parentId #if(:status>-1){ and status=:status } #if (:orderBy != null) { order by ##(:orderBy) }")
	public List<Privilege> getListByParentId(@SQLParam("parentId") int parentId, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);

	@SQL("select count(*) from " + TABLE_NAME + " #if(:status>-1){ where status=:status }")
	public int getCount(@SQLParam("status") int status);

	@SQL("update " + TABLE_NAME + " set status=0,updateTime=unix_timestamp() where id=:id")
	public int removeById(@SQLParam("id") int id);

	@SQL("insert into " + TABLE_NAME + "(" + COLUMNS + ") values(null,:priv.parentId,:priv.name,:priv.link,unix_timestamp(),unix_timestamp(),:priv.status)")
	public int addForRows(@SQLParam("priv") Privilege priv);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE_NAME + "(" + COLUMNS + ") values(null,:priv.parentId,:priv.name,:priv.link,unix_timestamp(),unix_timestamp(),:priv.status)")
	public int add(@SQLParam("priv") Privilege priv);

	@SQL("update " + TABLE_NAME + " set status=1, parentId=:priv.parentId, name=:priv.name, link=:priv.link, updateTime=unix_timestamp() where id=:priv.id")
	public int update(@SQLParam("priv") Privilege priv);

	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " where id=:id")
	public Privilege get(@SQLParam("id") int id);

	@SQL("select  " + PrivilegeDAO.COLUMNS + 
		 " from (select p . * "+
		 " from common_privilege p inner join common_user_privilege up ON p.id = up.privilegeId " +
		 " where up.userId = :userId and up.status = :status) tmp")
	public List<Privilege> getPrivListByUserId(@SQLParam("userId")int userId, @SQLParam("status") int status);
}