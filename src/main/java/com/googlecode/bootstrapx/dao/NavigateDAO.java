package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.Navigate;

@DAO
public interface NavigateDAO {
	public static final String TABLE = "bootstrapx_navigate";
	public static final String FIELDS = " id,parentId,name,sequence,weight,icon,image,description,creatorId,updaterId,createTime,updateTime,link,status";
	public static final String SELECT_FIELDS = " id,parentId,name,sequence,weight,icon,image,description,creatorId,updaterId,createTime,updateTime,link,status,(select count(1) from " + TABLE + " where parentId=tmp.id) count";
	public static final String FIELDS_REPLACE = " (" + FIELDS + ") ";


	@SQL("select " + SELECT_FIELDS + " from " + TABLE + " tmp where status=:status #if(:parentId>=0){ and parentId=:parentId } #if (:orderBy != null) { order by ##(:orderBy)} limit :offset,:size")
	public List<Navigate> select(@SQLParam("parentId") int parentId, @SQLParam("offset") int offset, @SQLParam("size") int size, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);

	@SQL("replace into " + TABLE + FIELDS_REPLACE + " values(null,:navigate.parentId,:navigate.name,:navigate.sequence,:navigate.weight,:navigate.icon,:navigate.image,:navigate.description,:navigate.creatorId,:navigate.updaterId,:navigate.createTime,:navigate.updateTime,:navigate.link,:navigate.status)")
	public int addForRows(@SQLParam("navigate") Navigate navigate);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE + FIELDS + " values(null, :navigate.parentId,:navigate.name,:navigate.sequence,:navigate.weight,:navigate.icon,:navigate.image,:navigate.description,:navigate.createorId,:navigate.updaterId,now(),now(),:navigate.link,:navigate.status)")
	public int add(@SQLParam("navigate") Navigate navigate);
	
	@SQL("update " + TABLE + " set status=0 where id=:id and status=1")
	public int remove(@SQLParam("id") int id);

	@SQL("select " + SELECT_FIELDS +" from " + TABLE + " tmp where status=1 and id=:id")
	public Navigate get(@SQLParam("id") int id);

	@SQL("select count(*) from " + TABLE + " where status=:status #if(:parentId>=0){ and parentId=:parentId }")
	public int getCount(@SQLParam("parentId") int parentId, @SQLParam("status") int status);
}
